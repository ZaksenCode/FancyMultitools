package me.zaksen.fancymultitools.client;

import me.zaksen.fancymultitools.client.screen.custom.ToolCreationStationScreen;
import me.zaksen.fancymultitools.screen.ModScreens;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class FancyMultitoolsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Screens
        HandledScreens.register(ModScreens.TOOL_CREATION_STATION_HANDLER, ToolCreationStationScreen::new);
    }
}
