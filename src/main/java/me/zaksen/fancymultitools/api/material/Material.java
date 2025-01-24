package me.zaksen.fancymultitools.api.material;

import me.zaksen.fancymultitools.registry.ModRegistries;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;

public class Material implements BasicMaterial {
    public final RegistryEntry.Reference<BasicMaterial> registryEntry;
    private final Text displayName;
    private final Ingredient materialIngredient;
    private final int durability;
    private final float speedModifier;

    public Material(Text displayName, Ingredient ingredient, int durability, float speedModifier) {
        this.registryEntry = ModRegistries.MATERIAL.createEntry(this);
        this.displayName = displayName;
        this.materialIngredient = ingredient;
        this.durability = durability;
        this.speedModifier = speedModifier;
    }

    @Override
    public Text getDisplayName() {
        return displayName;
    }

    @Override
    public Ingredient getMaterialIngredient() {
        return materialIngredient;
    }

    @Override
    public int getDurability() {
        return durability;
    }

    @Override
    public float getSpeedModifier() {
        return speedModifier;
    }
}
