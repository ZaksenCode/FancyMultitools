package me.zaksen.fancymultitools.material;

import me.zaksen.fancymultitools.FancyMultitools;
import me.zaksen.fancymultitools.api.material.BasicMaterial;
import me.zaksen.fancymultitools.api.material.Material;
import me.zaksen.fancymultitools.registry.ModRegistries;
import net.minecraft.item.Items;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModMaterials {

    public static final BasicMaterial AIR = registerMaterial(
            "air",
            new Material(Items.AIR, 0)
    );

    public static BasicMaterial registerMaterial(String id, BasicMaterial material) {
        return Registry.register(
                ModRegistries.MATERIAL,
                Identifier.of(FancyMultitools.MOD_ID, id),
                material
        );
    }

    public static void register() {

    }
}
