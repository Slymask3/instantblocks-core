package com.slymask3.instantblocks.core;

import com.slymask3.instantblocks.core.config.IConfig;
import com.slymask3.instantblocks.core.init.IMenuHelper;
import com.slymask3.instantblocks.core.init.ITileHelper;
import com.slymask3.instantblocks.core.network.IPacketHandler;
import com.slymask3.instantblocks.core.util.Helper;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Core {
    public static final String MOD_BASE = "instantblocks";
    public static final String MOD_ADDON = "core";
    public static final String FABRIC_MOD_ID = MOD_BASE + "-" + MOD_ADDON;
    public static final String FORGE_MOD_ID = MOD_BASE + "_" + MOD_ADDON;
    public static final String CONFIG_DIR = "config/" + MOD_BASE;
    public static final Logger LOG = LoggerFactory.getLogger(FABRIC_MOD_ID);
    public static CreativeModeTab ITEM_GROUP;
    public static IPacketHandler NETWORK;
    public static ITileHelper TILES;
    public static IMenuHelper MENUS;
    public static IConfig CONFIG = new IConfig(){};
    public static Map<Item,Integer> FUEL;

    public static void init() {
        Helper.createDirectory(CONFIG_DIR);
        FUEL = new HashMap<>();
        FUEL.put(Items.BONE,5);
    }
}