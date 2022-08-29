package com.slymask3.instantblocks.core.init;

import com.slymask3.instantblocks.core.inventory.WandChargeMenu;
import com.slymask3.instantblocks.core.registry.CoreMenus;
import net.minecraftforge.common.extensions.IForgeMenuType;

public class ForgeMenus implements IMenuHelper {
    public void init() {
        CoreMenus.WAND_CHARGE = IForgeMenuType.create((windowId, inv, data) -> new WandChargeMenu(windowId, inv));
    }
}