package com.slymask3.instantblocks.core;

import com.google.gson.JsonObject;
import com.slymask3.instantblocks.core.config.IConfig;
import com.slymask3.instantblocks.core.init.IBasicHelper;
import com.slymask3.instantblocks.core.network.IPacketHandler;
import com.slymask3.instantblocks.core.util.Helper;
import com.slymask3.instantblocks.core.util.IModLoader;
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
    public static IBasicHelper TILES;
    public static IBasicHelper MENUS;
    public static IConfig CONFIG;
    public static IModLoader LOADER;
    public static Map<Item,Double> FUEL;

    public static void init() {
        Helper.createDirectory(CONFIG_DIR);
        CONFIG = new IConfig(){};
        FUEL = new HashMap<>();
        setupFuel();
    }

    private static void setupFuel() {
        FUEL.put(Items.BONE,7.0);
        FUEL.put(Items.DIAMOND,123.0);
        FUEL.put(Items.IRON_INGOT,100.0);
        FUEL.put(Items.GLASS,1.0);
        FUEL.put(Items.GLASS_PANE,0.375);
    }

    public static void overwriteFuel() {
        JsonObject jsonDefault = new JsonObject();
        jsonDefault.addProperty("minecraft:stone",1.000);
        JsonObject json = Helper.getJsonFromFile(CONFIG_DIR + "/fuel.json", JsonObject.class, jsonDefault);
        if(json != null) {
            for(String key : json.keySet()) {
                Item item = LOADER.getItem(key);
                if(!item.equals(Items.AIR)) {
                    if(FUEL.containsKey(item)) {
                        FUEL.replace(item,json.get(key).getAsDouble());
                    } else {
                        FUEL.put(item,json.get(key).getAsDouble());
                    }
                }
            }
        }
    }
}