package me.zaksen.fancymultitools.block;

import me.zaksen.fancymultitools.FancyMultitools;
import me.zaksen.fancymultitools.block.custom.ToolCreationStation;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block TOOL_CREATION_STATION = registerBlock(
            "tool_creation_station",
            new ToolCreationStation(
                    AbstractBlock.Settings.copy(Blocks.CRAFTING_TABLE)
                            .hardness(2.0f)
            ),
            true
    );

    private static Block registerBlock(String id, Block block, boolean withItem) {
        Block registered = Registry.register(
                Registries.BLOCK,
                Identifier.of(FancyMultitools.MOD_ID, id),
                block
        );

        if(withItem) {
            Registry.register(
                    Registries.ITEM,
                    Identifier.of(FancyMultitools.MOD_ID, id),
                    new BlockItem(block, new FabricItemSettings())
            );
        }

        return registered;
    }

    public static void register() {

    }
}
