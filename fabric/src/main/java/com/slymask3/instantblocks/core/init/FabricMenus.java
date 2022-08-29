package com.slymask3.instantblocks.core.init;

import com.slymask3.instantblocks.core.inventory.WandChargeMenu;
import com.slymask3.instantblocks.core.registry.CoreMenus;
import net.minecraft.world.inventory.MenuType;

public class FabricMenus implements IMenuHelper {
    public void init() {
        CoreMenus.WAND_CHARGE = new MenuType<>(WandChargeMenu::new);
    }
}