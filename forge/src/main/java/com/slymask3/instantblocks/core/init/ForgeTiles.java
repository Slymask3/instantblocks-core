package com.slymask3.instantblocks.core.init;

import com.slymask3.instantblocks.core.block.entity.WandChargeBlockEntity;
import com.slymask3.instantblocks.core.registry.CoreBlocks;
import com.slymask3.instantblocks.core.registry.CoreTiles;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ForgeTiles implements ITileHelper {
    public void init() {
        CoreTiles.WAND_CHARGE = BlockEntityType.Builder.of(WandChargeBlockEntity::new, CoreBlocks.WAND_CHARGE).build(null);
    }
}