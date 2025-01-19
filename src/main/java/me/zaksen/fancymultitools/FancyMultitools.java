package me.zaksen.fancymultitools;

import me.zaksen.fancymultitools.block.ModBlockEntities;
import me.zaksen.fancymultitools.block.ModBlocks;
import me.zaksen.fancymultitools.item.ModItems;
import me.zaksen.fancymultitools.material.ModMaterials;
import me.zaksen.fancymultitools.recipe.ModRecipes;
import me.zaksen.fancymultitools.item.tool.ModTools;
import me.zaksen.fancymultitools.screen.ModScreens;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FancyMultitools implements ModInitializer {

    public static final String MOD_ID = "fancy_multitools";
    public static final Logger LOGGER = LoggerFactory.getLogger(FancyMultitools.class);

    @Override
    public void onInitialize() {
        // Items
        ModItems.register();
        ModTools.register();
        // Blocks
        ModBlocks.register();
        ModBlockEntities.register();
        // Other
        ModMaterials.register();
        ModRecipes.register();
        ModScreens.register();
    }
}
