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

    public Material(Text displayName, Ingredient ingredient, int durability) {
        this.registryEntry = ModRegistries.MATERIAL.createEntry(this);
        this.displayName = displayName;
        this.materialIngredient = ingredient;
        this.durability = durability;
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
}
