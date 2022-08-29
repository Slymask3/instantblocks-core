package com.slymask3.instantblocks.core;

import com.slymask3.instantblocks.core.gui.screens.WandChargeScreen;
import com.slymask3.instantblocks.core.network.FabricPacketHandler;
import com.slymask3.instantblocks.core.registry.CoreMenus;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class InstantBlocksCoreClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FabricPacketHandler.Client.init();
        MenuScreens.register(CoreMenus.WAND_CHARGE, WandChargeScreen::new);
    }
}