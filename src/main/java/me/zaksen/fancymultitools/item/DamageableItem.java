package me.zaksen.fancymultitools.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public interface DamageableItem {

    void setDamageValue(ItemStack stack, int value);
    int getDamageValue(ItemStack stack);
    int getMaxDamageValue(ItemStack stack);

    void hurt(ItemStack stack, int amount, LivingEntity entity, Hand hand);
}
