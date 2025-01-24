package me.zaksen.fancymultitools.item.tool.custom;

import me.zaksen.fancymultitools.api.material.BasicMaterial;
import me.zaksen.fancymultitools.registry.ModRegistries;
import me.zaksen.fancymultitools.tag.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Multitool extends Item {
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
    public boolean isDamageable() {
        return true;
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
        tooltip.add(Text.translatable("tooltip.fancy_multitools.durability", (materials.getTotalDurability() - stack.getNbt().getInt("Damage")) + "/" + materials.getTotalDurability()));

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
