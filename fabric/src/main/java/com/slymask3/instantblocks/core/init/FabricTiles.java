package com.slymask3.instantblocks.core.init;

import com.slymask3.instantblocks.core.block.entity.WandChargeBlockEntity;
import com.slymask3.instantblocks.core.registry.CoreBlocks;
import com.slymask3.instantblocks.core.registry.CoreTiles;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;

public class FabricTiles implements IBasicHelper {
    public void init() {
        CoreTiles.WAND_CHARGE = FabricBlockEntityTypeBuilder.create(WandChargeBlockEntity::new, CoreBlocks.WAND_CHARGE).build(null);
    }
}