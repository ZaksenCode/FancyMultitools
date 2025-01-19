package me.zaksen.fancymultitools.screen;

import me.zaksen.fancymultitools.FancyMultitools;
import me.zaksen.fancymultitools.client.screen.custom.ToolCreationStationScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreens {

    public static final ScreenHandlerType<ToolCreationStationScreenHandler> TOOL_CREATION_STATION_HANDLER = Registry.register(
            Registries.SCREEN_HANDLER,
            Identifier.of(FancyMultitools.MOD_ID, "tool_creation_station"),
            new ExtendedScreenHandlerType<>(ToolCreationStationScreenHandler::new)
    );

    public static void register() {

    }
}
