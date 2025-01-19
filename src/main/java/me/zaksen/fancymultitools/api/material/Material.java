package me.zaksen.fancymultitools.api.material;

import me.zaksen.fancymultitools.registry.ModRegistries;
import net.minecraft.item.Item;
import net.minecraft.registry.entry.RegistryEntry;

public class Material implements BasicMaterial {
    private final RegistryEntry.Reference<BasicMaterial> registryEntry;
    private final Item materialItem;
    private final int durability;

    public Material(Item item, int durability) {
        this.registryEntry = ModRegistries.MATERIAL.createEntry(this);
        this.materialItem = item;
        this.durability = durability;
    }

    @Override
    public Item getMaterialItem() {
        return materialItem;
    }

    @Override
    public int getDurability() {
        return durability;
    }
}
