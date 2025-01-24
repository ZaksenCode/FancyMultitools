package me.zaksen.fancymultitools.tag;

import me.zaksen.fancymultitools.FancyMultitools;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static final TagKey<Block> MULTITOOL_MINEABLE = TagKey.of(RegistryKeys.BLOCK, Identifier.of(
            FancyMultitools.MOD_ID, "multitool"
    ));

    public static final TagKey<Item> STONE_MATERIAL = TagKey.of(RegistryKeys.ITEM, Identifier.of(
            FancyMultitools.MOD_ID, "stone_material"
    ));
}
