package me.zaksen.fancymultitools.block.custom.enity;

import me.zaksen.fancymultitools.block.ModBlockEntities;
import me.zaksen.fancymultitools.client.screen.custom.ToolCreationStationScreenHandler;
import me.zaksen.fancymultitools.recipe.MultitoolRecipe;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ToolCreationStationEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {

    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(6, ItemStack.EMPTY);

    public final static int[] INPUT_SLOTS = {0, 1, 2, 3, 4};
    public final static int OUTPUT_SLOT = 5;

    public ToolCreationStationEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TOOL_CREATION_STATION_ENTITY, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("screen.fancy_multitools.tool_creation_station");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ToolCreationStationScreenHandler(syncId, playerInventory, this);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
    }

    public void tryPreviewItem() {
        MultitoolRecipe recipe = getCurrentRecipe().orElse(null);

        if(recipe == null) {
            this.setStack(OUTPUT_SLOT, ItemStack.EMPTY);
            return;
        }

        SimpleInventory inv = new SimpleInventory(this.size() - 1);

        for(int i = 0; i < this.size() - 1; ++i) {
            inv.setStack(i, this.getStack(i));
        }

        this.setStack(OUTPUT_SLOT, (recipe.craft(inv, null)));
    }

    public void craftItem() {
        for(int i : INPUT_SLOTS) {
            this.removeStack(i, 1);
        }
    }

    private boolean hasRecipe() {
        Optional<MultitoolRecipe> recipe = getCurrentRecipe();
        return recipe.isPresent() && canInsertOutput();
    }

    private boolean canInsertOutput() {
        return this.getStack(OUTPUT_SLOT).isEmpty();
    }

    private Optional<MultitoolRecipe> getCurrentRecipe() {
        SimpleInventory inv = new SimpleInventory(this.size() - 1);

        for(int i = 0; i < this.size() - 1; ++i) {
            inv.setStack(i, this.getStack(i));
        }

        return getWorld().getRecipeManager().getFirstMatch(MultitoolRecipe.Type.INSTANCE, inv, getWorld());
    }
}
