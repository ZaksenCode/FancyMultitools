package me.zaksen.fancymultitools.item.tool.custom;

import me.zaksen.fancymultitools.api.material.BasicMaterial;
import me.zaksen.fancymultitools.item.DamageableItem;
import me.zaksen.fancymultitools.registry.ModRegistries;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Multitool extends Item implements DamageableItem {
    private static final NumberFormat formatter = new DecimalFormat("#0.00");

    public Multitool(Settings settings) {
        super(settings);
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, @Nullable BlockState state) {
        var materials = Materials.fromNbt(stack.getOrCreateNbt());

        var totalModifier = 0.0f;

        for(var material : materials.materials) {
            totalModifier += material.getSpeedModifier();
        }

        return totalModifier / materials.materials.size();
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        hurt(stack, 1, miner, miner.getActiveHand());
        return true;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        hurt(stack, 1, attacker, attacker.getActiveHand());
        return true;
    }

    @Override
    public boolean isSuitableFor(ItemStack stack, BlockState state) {
        var materials = Materials.fromNbt(stack.getOrCreateNbt());
        return materials.isSuitableFor(state);
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return Math.round((((float) getDamageValue(stack) / getMaxDamageValue(stack) * 13f)));
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        float f = Math.max(0.0F, getDamageValue(stack) / (float)getMaxDamageValue(stack));
        return MathHelper.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if(!world.isClient) {
            var materials = Materials.fromNbt(stack.getOrCreateNbt());

        }

        return TypedActionResult.pass(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        var materials = Materials.fromNbt(stack.getOrCreateNbt());

        tooltip.add(Text.translatable("tooltip.fancy_multitools.void"));
        tooltip.add(Text.translatable(
                "tooltip.fancy_multitools.durability",
                (getDamageValue(stack) + "/" + getMaxDamageValue(stack))
        ));

        if(Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.fancy_multitools.mining_speed", formatter.format(getMiningSpeedMultiplier(stack, null))));

            tooltip.add(Text.translatable("tooltip.fancy_multitools.void"));
            tooltip.add(Text.translatable("tooltip.fancy_multitools.materials"));
            materials.materials.forEach(material -> tooltip.add(
                    MutableText.of(Text.of("§7- ").getContent()).append(material.getDisplayName()))
            );
        } else {
            tooltip.add(Text.translatable("tooltip.fancy_multitools.shift_info"));
        }

        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public void setDamageValue(ItemStack stack, int value) {
        var nbt = stack.getOrCreateNbt();
        nbt.putInt("Damage", value);
        stack.setNbt(nbt);
    }

    @Override
    public int getDamageValue(ItemStack stack) {
        var nbt = stack.getOrCreateNbt();
        return nbt.contains("Damage") ? nbt.getInt("Damage") : 0;
    }

    @Override
    public int getMaxDamageValue(ItemStack stack) {
        var materials = Materials.fromNbt(stack.getOrCreateNbt());
        return materials.getTotalDurability();
    }

    @Override
    public void hurt(ItemStack stack, int amount, LivingEntity entity, Hand hand) {
        if(entity.getWorld().isClient) {
            return;
        }

        setDamageValue(stack, getDamageValue(stack) - amount);

        if(entity instanceof ServerPlayerEntity player) {
            Criteria.ITEM_DURABILITY_CHANGED.trigger(player, stack, (getMaxDamageValue(stack) - getDamageValue(stack)) + amount);
        }

        if(getDamageValue(stack) < 1) {
            entity.sendToolBreakStatus(hand);
            stack.decrement(1);
        }
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    public record Materials(List<BasicMaterial> materials) {
        public NbtCompound toNbt(NbtCompound nbt) {
            NbtList list = new NbtList();
            stream().forEach(material -> list.add(NbtString.of(ModRegistries.MATERIAL.getId(material).toString())));
            nbt.put("materials", list);
            return nbt;
        }

        public Stream<BasicMaterial> stream() {
            return materials.stream();
        }

        public int getTotalDurability() {
            int result = 0;

            for(BasicMaterial material : materials) {
                result += material.getDurability();
            }

            return result;
        }

        // TODO - Add materials tier logic
        public boolean isSuitableFor(BlockState state) {
            return true;
        }

        public static Materials fromNbt(@Nullable NbtCompound nbt) {
            Materials result = new Materials(new ArrayList<>());

            if(nbt == null || !nbt.contains("materials", NbtElement.LIST_TYPE)) {
                return result;
            }

            NbtList nbtList = nbt.getList("materials", NbtElement.STRING_TYPE);

            for(int i = 0; i < nbtList.size(); ++i) {
                result.materials.add(getMaterial(nbtList, i));
            }

            return result;
        }

        public static BasicMaterial getMaterial(NbtList list, int index) {
            NbtElement element = list.get(index);
            return ModRegistries.MATERIAL.get(Identifier.splitOn(element.asString(), ':'));
        }
    }
}
