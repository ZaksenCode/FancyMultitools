package me.zaksen.fancymultitools.client.screen.custom.slot;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

import java.util.function.Consumer;

public class HandledSlot extends Slot {

    private final Consumer<Slot> consumer;

    public HandledSlot(Inventory inventory, int index, int x, int y, Consumer<Slot> consumer) {
        super(inventory, index, x, y);
        this.consumer = consumer;
    }

    @Override
    protected void onTake(int amount) {
        consumer.accept(this);
    }

    @Override
    public void onQuickTransfer(ItemStack newItem, ItemStack original) {
        consumer.accept(this);
    }

    @Override
    public void setStack(ItemStack stack) {
        super.setStack(stack);
        consumer.accept(this);
    }
}
