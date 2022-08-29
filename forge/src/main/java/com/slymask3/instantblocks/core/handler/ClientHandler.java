package com.slymask3.instantblocks.core.handler;

import com.slymask3.instantblocks.core.Core;
import com.slymask3.instantblocks.core.config.ClothConfig;
import com.slymask3.instantblocks.core.gui.screens.WandChargeScreen;
import com.slymask3.instantblocks.core.platform.Services;
import com.slymask3.instantblocks.core.registry.CoreMenus;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Core.FORGE_MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientHandler {
    @SubscribeEvent
    public static void registerConfigScreen(FMLClientSetupEvent event) {
        if(Services.PLATFORM.isModLoaded("cloth_config")) {
            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> AutoConfig.getConfigScreen(ClothConfig.class, parent).get()));
        }
        event.enqueueWork(() -> MenuScreens.register(CoreMenus.WAND_CHARGE, WandChargeScreen::new));
    }
}