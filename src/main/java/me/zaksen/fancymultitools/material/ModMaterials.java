package me.zaksen.fancymultitools.material;

import me.zaksen.fancymultitools.FancyMultitools;
import me.zaksen.fancymultitools.api.material.BasicMaterial;
import me.zaksen.fancymultitools.api.material.Material;
import me.zaksen.fancymultitools.registry.ModRegistries;
import me.zaksen.fancymultitools.tag.ModTags;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModMaterials {

    public static final BasicMaterial AIR = registerMaterial(
            "air",
            new Material(
                    Text.translatable("material.fancy_multitools.air"),
                    Ingredient.ofItems(Items.AIR),
                    0,
                    0.0f
            )
    );

    public static final BasicMaterial WOOD = registerMaterial(
            "wood",
            new Material(
                    Text.translatable("material.fancy_multitools.wood"),
                    Ingredient.fromTag(ItemTags.PLANKS),
                    50,
                    0.4f
            )
    );

    public static final BasicMaterial STONE = registerMaterial(
            "stone",
            new Material(

                    Text.translatable("material.fancy_multitools.stone"),
                    Ingredient.fromTag(ModTags.STONE_MATERIAL),
                    100,
                    0.6f
            )
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
