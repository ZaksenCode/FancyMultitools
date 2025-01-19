package me.zaksen.fancymultitools.block;

import me.zaksen.fancymultitools.FancyMultitools;
import me.zaksen.fancymultitools.block.custom.enity.ToolCreationStationEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {

    public static final BlockEntityType<ToolCreationStationEntity> TOOL_CREATION_STATION_ENTITY = registerEntity(
            "tool_creation_station",
            BlockEntityType.Builder.create(ToolCreationStationEntity::new, ModBlocks.TOOL_CREATION_STATION).build(null)
    );

    private static <T extends BlockEntityType<?>> T registerEntity(String id, T blockEntityType) {
        return Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(FancyMultitools.MOD_ID, id),
                blockEntityType
        );
    }

    public static void register() {

    }
}
