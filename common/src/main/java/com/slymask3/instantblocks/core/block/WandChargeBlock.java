package com.slymask3.instantblocks.core.block;

import com.slymask3.instantblocks.core.Core;
import com.slymask3.instantblocks.core.block.entity.WandChargeBlockEntity;
import com.slymask3.instantblocks.core.registry.CoreTiles;
import com.slymask3.instantblocks.core.util.Helper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

public class WandChargeBlock extends BaseEntityBlock {
    public WandChargeBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL)
                .strength(1.5F)
                .sound(SoundType.METAL)
        );
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WandChargeBlockEntity(pos,state);
    }

    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if(Helper.isServer(world)) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof MenuProvider) {
                Core.NETWORK.openScreen(player,(MenuProvider)blockEntity,pos);
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.SUCCESS;
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return world.isClientSide ? null : createTickerHelper(type, CoreTiles.WAND_CHARGE, WandChargeBlockEntity::serverTick);
    }
}