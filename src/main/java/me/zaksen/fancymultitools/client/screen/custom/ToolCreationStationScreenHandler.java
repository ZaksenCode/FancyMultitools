package me.zaksen.fancymultitools.client.screen.custom;

import me.zaksen.fancymultitools.block.custom.enity.ToolCreationStationEntity;
import me.zaksen.fancymultitools.screen.ModScreens;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class ToolCreationStationScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public final ToolCreationStationEntity blockEntity;

    public ToolCreationStationScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()));
    }

    public ToolCreationStationScreenHandler(int syncId, PlayerInventory inventory, BlockEntity blockEntity) {
        super(ModScreens.TOOL_CREATION_STATION_HANDLER, syncId);
        checkSize((Inventory) blockEntity, 2);
        this.inventory = (Inventory) blockEntity;
        this.blockEntity = (ToolCreationStationEntity) blockEntity;
        inventory.onOpen(inventory.player);

        this.addSlot(new Slot(this.inventory, 0, 44, 28));
        this.addSlot(new Slot(this.inventory, 1, 62, 28));
        this.addSlot(new Slot(this.inventory, 2, 80, 28));
        this.addSlot(new Slot(this.inventory, 3, 98, 28));
        this.addSlot(new Slot(this.inventory, 4, 116, 28));
        this.addSlot(new Slot(this.inventory, 5, 44, 46));
        this.addSlot(new Slot(this.inventory, 6, 62, 46));
        this.addSlot(new Slot(this.inventory, 7, 80, 46));
        this.addSlot(new Slot(this.inventory, 8, 98, 46));
        this.addSlot(new Slot(this.inventory, 9, 116, 46));

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
    }


    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
