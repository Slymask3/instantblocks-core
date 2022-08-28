package com.slymask3.instantblocks.core.init;

import com.slymask3.instantblocks.core.Core;
import com.slymask3.instantblocks.core.gui.screens.WandChargeScreen;
import com.slymask3.instantblocks.core.inventory.WandChargeMenu;
import com.slymask3.instantblocks.core.registry.CoreMenus;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.common.extensions.IForgeMenuType;

public class ForgeMenus implements IMenuHelper {
    public void init() {
        Core.LOG.error("ForgeMenus.init()");
        CoreMenus.WAND_CHARGE = IForgeMenuType.create((windowId, inv, data) -> new WandChargeMenu(windowId));
    }

    public void link() {
        Core.LOG.error("ForgeMenus.link()");
        MenuScreens.register(CoreMenus.WAND_CHARGE, WandChargeScreen::new);
    }
}