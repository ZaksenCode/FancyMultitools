package me.zaksen.fancymultitools.api.material;

import net.minecraft.recipe.Ingredient;
import net.minecraft.text.Text;

public interface BasicMaterial {

    Text getDisplayName();
    Ingredient getMaterialIngredient();
    int getDurability();
    float getSpeedModifier();
}
