package com.slymask3.instantblocks.core.registry;

import com.slymask3.instantblocks.core.item.InstantWandItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class CoreItems {
    public static Item WAND_STARTER;
    public static Item WAND_ADVANCED;
    public static Item WAND_ELITE;
    public static Item WAND_CREATIVE;

    public static void init() {
        WAND_STARTER = new InstantWandItem(2000,32);
        WAND_ADVANCED = new InstantWandItem(5000,16);
        WAND_ELITE = new InstantWandItem(10000,8);
        WAND_CREATIVE = new InstantWandItem(0,0, Rarity.EPIC);
    }
}