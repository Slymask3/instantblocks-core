package com.slymask3.instantblocks.core.init;

import com.slymask3.instantblocks.core.inventory.WandChargeMenu;
import com.slymask3.instantblocks.core.registry.CoreMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeMenuType;

public class ForgeMenus implements IBasicHelper {
    public void init() {
        CoreMenus.WAND_CHARGE = IForgeMenuType.create((windowId, inv, data) -> {
            Level world = inv.player.getCommandSenderWorld();
            BlockPos pos = data.readBlockPos();
            return new WandChargeMenu(windowId, inv, world, pos);
        });
    }
}