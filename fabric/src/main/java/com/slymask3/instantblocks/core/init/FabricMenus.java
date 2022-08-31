package com.slymask3.instantblocks.core.init;

import com.slymask3.instantblocks.core.inventory.WandChargeMenu;
import com.slymask3.instantblocks.core.registry.CoreMenus;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class FabricMenus implements IMenuHelper {
    public void init() {
        CoreMenus.WAND_CHARGE = new ExtendedScreenHandlerType<>((windowId, inv, data) -> {
            Level world = inv.player.getCommandSenderWorld();
            BlockPos pos = data.readBlockPos();
            return new WandChargeMenu(windowId, inv, world, pos);
        });
    }
}