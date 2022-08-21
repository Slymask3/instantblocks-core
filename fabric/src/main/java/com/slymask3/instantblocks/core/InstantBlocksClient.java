package com.slymask3.instantblocks.core;

import com.slymask3.instantblocks.core.network.FabricPacketHandler;
import net.fabricmc.api.ClientModInitializer;

public class InstantBlocksClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FabricPacketHandler.Client.init();
    }
}