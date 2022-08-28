package com.slymask3.instantblocks.core.registry;

import com.slymask3.instantblocks.core.block.WandChargeBlock;
import net.minecraft.world.level.block.Block;

public class CoreBlocks {
    public static Block WAND_CHARGE;

    public static void init() {
        WAND_CHARGE = new WandChargeBlock();
    }
}