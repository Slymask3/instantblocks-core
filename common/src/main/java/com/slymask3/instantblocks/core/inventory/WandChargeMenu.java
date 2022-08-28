package com.slymask3.instantblocks.core.inventory;

import com.slymask3.instantblocks.core.registry.CoreMenus;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class WandChargeMenu extends AbstractContainerMenu {
    public WandChargeMenu(int windowId) {
        super(CoreMenus.WAND_CHARGE, windowId);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }
}