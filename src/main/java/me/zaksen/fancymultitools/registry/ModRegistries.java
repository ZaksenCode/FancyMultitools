package me.zaksen.fancymultitools.registry;

import me.zaksen.fancymultitools.api.material.BasicMaterial;
import me.zaksen.fancymultitools.material.ModMaterials;
import net.minecraft.registry.*;

public class ModRegistries {
    public static final DefaultedRegistry<BasicMaterial> MATERIAL = Registries.createIntrusive(
            ModRegistryKeys.MATERIAL,
            "fancy_multitools:air",
            registry -> ModMaterials.AIR
    );
}
