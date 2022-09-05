package com.slymask3.instantblocks.core.init;

import com.slymask3.instantblocks.core.inventory.WandChargeContainer;
import com.slymask3.instantblocks.core.registry.CoreContainers;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class FabricMenus implements IBasicHelper {
    public void init() {
        CoreContainers.WAND_CHARGE_STATION = new ExtendedScreenHandlerType<>((windowId, inv, data) -> {
            Level world = inv.player.getCommandSenderWorld();
            BlockPos pos = data.readBlockPos();
            return new WandChargeContainer(windowId, inv, world, pos);
        });
    }
}