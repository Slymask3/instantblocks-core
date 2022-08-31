package com.slymask3.instantblocks.core.registry;

import com.slymask3.instantblocks.core.item.InstantWandItem;
import net.minecraft.world.item.Item;

public class CoreItems {
    public static Item WAND_WOOD;
    public static Item WAND_STONE;
    public static Item WAND_IRON;
    public static Item WAND_GOLD;
    public static Item WAND_DIAMOND;
    public static Item WAND_NETHERITE;

    public static void init() {
        WAND_WOOD = new InstantWandItem(100);
        WAND_STONE = new InstantWandItem(200);
        WAND_IRON = new InstantWandItem(300);
        WAND_GOLD = new InstantWandItem(500);
        WAND_DIAMOND = new InstantWandItem(1000);
        WAND_NETHERITE = new InstantWandItem(2000);
    }
}