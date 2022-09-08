package com.slymask3.instantblocks.core;

import com.slymask3.instantblocks.core.builder.Fuel;
import com.slymask3.instantblocks.core.config.IConfig;
import com.slymask3.instantblocks.core.init.IBasicHelper;
import com.slymask3.instantblocks.core.network.IPacketHandler;
import com.slymask3.instantblocks.core.util.Helper;
import com.slymask3.instantblocks.core.util.IModLoader;
import net.minecraft.world.item.CreativeModeTab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Core {
    public static final String MOD_BASE = "instantblocks";
    public static final String MOD_ADDON = "core";
    public static final String FABRIC_MOD_ID = MOD_BASE + "-" + MOD_ADDON;
    public static final String FORGE_MOD_ID = MOD_BASE + "_" + MOD_ADDON;
    public static final String CONFIG_DIR = "config/" + MOD_BASE;
    public static final Logger LOG = LoggerFactory.getLogger(FABRIC_MOD_ID);

    public static CreativeModeTab ITEM_GROUP;
    public static IPacketHandler NETWORK;
    public static IBasicHelper TILES;
    public static IBasicHelper CONTAINERS;
    public static IConfig CONFIG;
    public static IModLoader LOADER;
    public static Fuel FUEL;

    public static void init() {
        Helper.createDirectory(CONFIG_DIR);
        CONFIG = new IConfig(){};
        FUEL = new Fuel();
    }

    public static class Strings {
        private static final String ERROR = "ib.message.error.";
        public static final String ERROR_WAND = ERROR + "wand";
        public static final String ERROR_WAND_CHARGE = ERROR + "wand_charge";
        public static final String ERROR_DISABLED = ERROR + "disabled";
    }
}