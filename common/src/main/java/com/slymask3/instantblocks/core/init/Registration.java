package com.slymask3.instantblocks.core.init;

import com.slymask3.instantblocks.core.core.ModItems;
import com.slymask3.instantblocks.core.reference.Names;
import net.minecraft.world.item.Item;

public class Registration {
    public static void registerItems(IRegistryHelper<Item> helper) {
        ModItems.init();
        helper.register(Names.Items.IB_WAND_WOOD, ModItems.WAND_WOOD);
        helper.register(Names.Items.IB_WAND_STONE, ModItems.WAND_STONE);
        helper.register(Names.Items.IB_WAND_IRON, ModItems.WAND_IRON);
        helper.register(Names.Items.IB_WAND_GOLD, ModItems.WAND_GOLD);
        helper.register(Names.Items.IB_WAND_DIAMOND, ModItems.WAND_DIAMOND);
        helper.register(Names.Items.IB_WAND_NETHERITE, ModItems.WAND_NETHERITE);
    }
}