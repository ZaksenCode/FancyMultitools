package me.zaksen.fancymultitools.item.tool;

import me.zaksen.fancymultitools.FancyMultitools;
import me.zaksen.fancymultitools.item.tool.custom.Multitool;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModTools {
    public static final Item MULTITOOL = Registry.register(
            Registries.ITEM,
            Identifier.of(FancyMultitools.MOD_ID, "multitool"),
            new Multitool(new FabricItemSettings().maxCount(1))
    );

    public static void register() {

    }
}
