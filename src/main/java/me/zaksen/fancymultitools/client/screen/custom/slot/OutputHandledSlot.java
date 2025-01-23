package me.zaksen.fancymultitools.client.screen.custom.slot;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

import java.util.function.Consumer;

public class OutputHandledSlot extends HandledSlot {

    public OutputHandledSlot(Inventory inventory, int index, int x, int y, Consumer<Slot> consumer) {
        super(inventory, index, x, y, consumer);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return false;
    }
}
