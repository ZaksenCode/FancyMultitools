package me.zaksen.fancymultitools.block.custom;

import me.zaksen.fancymultitools.block.custom.enity.ToolCreationStationEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ToolCreationStation extends BlockWithEntity {
    public ToolCreationStation(Settings settings) {
        super(settings);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ToolCreationStationEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient) {
            var screenFactory = (ToolCreationStationEntity) world.getBlockEntity(pos);

            if(screenFactory != null) {
                player.openHandledScreen(screenFactory);
            }
        }

        return ActionResult.SUCCESS;
    }
}
