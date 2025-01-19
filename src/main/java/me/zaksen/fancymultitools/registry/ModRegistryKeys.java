package me.zaksen.fancymultitools.registry;

import me.zaksen.fancymultitools.FancyMultitools;
import me.zaksen.fancymultitools.api.material.BasicMaterial;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ModRegistryKeys {
    public static final RegistryKey<Registry<BasicMaterial>> MATERIAL_KEY = of("materials");

    private static <T> RegistryKey<Registry<T>> of(String id) {
        return RegistryKey.ofRegistry(new Identifier(FancyMultitools.MOD_ID, id));
    }
}
