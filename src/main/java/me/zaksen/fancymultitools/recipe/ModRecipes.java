package me.zaksen.fancymultitools.recipe;

import me.zaksen.fancymultitools.FancyMultitools;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {

    public static void register() {
        Registry.register(
                Registries.RECIPE_SERIALIZER,
                Identifier.of(FancyMultitools.MOD_ID, MultitoolRecipe.Serializer.ID),
                MultitoolRecipe.Serializer.INSTANCE
        );

        Registry.register(
                Registries.RECIPE_TYPE,
                Identifier.of(FancyMultitools.MOD_ID, MultitoolRecipe.Type.ID),
                MultitoolRecipe.Type.INSTANCE
        );
    }
}
