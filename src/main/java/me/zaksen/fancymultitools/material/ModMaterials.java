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
                    2.0f
            )
    );

    public static final BasicMaterial STONE = registerMaterial(
            "stone",
            new Material(

                    Text.translatable("material.fancy_multitools.stone"),
                    Ingredient.fromTag(ModTags.STONE_MATERIAL),
                    100,
                    4.0f
            )
    );

    public static final BasicMaterial GOLD = registerMaterial(
            "gold",
            new Material(

                    Text.translatable("material.fancy_multitools.gold"),
                    Ingredient.fromTag(ModTags.GOLD_MATERIAL),
                    80,
                    12.0f
            )
    );

    public static final BasicMaterial IRON = registerMaterial(
            "iron",
            new Material(

                    Text.translatable("material.fancy_multitools.iron"),
                    Ingredient.fromTag(ModTags.IRON_MATERIAL),
                    256,
                    6.0f
            )
    );

    public static final BasicMaterial DIAMOND = registerMaterial(
            "diamond",
            new Material(

                    Text.translatable("material.fancy_multitools.diamond"),
                    Ingredient.fromTag(ModTags.DIAMOND_MATERIAL),
                    512,
                    8.0f
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
