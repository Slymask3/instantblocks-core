package com.slymask3.instantblocks.core.block;

import com.slymask3.instantblocks.core.Core;
import com.slymask3.instantblocks.core.block.entity.WandChargeBlockEntity;
import com.slymask3.instantblocks.core.registry.CoreTiles;
import com.slymask3.instantblocks.core.util.Helper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
            if(blockEntity instanceof MenuProvider menuProvider) {
                Core.NETWORK.openScreen(player,menuProvider,pos);
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.SUCCESS;
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return Helper.isClient(world) ? null : createTickerHelper(type, CoreTiles.WAND_CHARGE, WandChargeBlockEntity::serverTick);
    }

    public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity entity, ItemStack itemStack) {
        if(itemStack.hasCustomHoverName()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof WandChargeBlockEntity) {
                ((WandChargeBlockEntity)blockEntity).setCustomName(itemStack.getHoverName());
            }
        }
    }

    public void onRemove(BlockState oldState, Level world, BlockPos pos, BlockState newState, boolean $$4) {
        if(!oldState.is(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof WandChargeBlockEntity) {
                if(world instanceof ServerLevel) {
                    Containers.dropContents(world, pos, (WandChargeBlockEntity)blockEntity);
                }
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(oldState, world, pos, newState, $$4);
        }
    }
}