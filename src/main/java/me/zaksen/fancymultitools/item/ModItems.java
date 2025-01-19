package me.zaksen.fancymultitools.item;

import me.zaksen.fancymultitools.FancyMultitools;
import me.zaksen.fancymultitools.item.custom.MultitoolPartItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item MULTITOOL_HEAD_PART = Registry.register(
            Registries.ITEM,
            Identifier.of(FancyMultitools.MOD_ID, "multitool_head_part"),
            new MultitoolPartItem(new FabricItemSettings().maxCount(1))
    );

    public static void register() {

    }
}
