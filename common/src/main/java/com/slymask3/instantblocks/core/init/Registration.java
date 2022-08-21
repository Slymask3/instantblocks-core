package com.slymask3.instantblocks.core.init;

import com.slymask3.instantblocks.core.registry.CoreItems;
import com.slymask3.instantblocks.core.reference.Names;
import net.minecraft.world.item.Item;

public class Registration {
    public static void registerItems(IRegistryHelper<Item> helper) {
        CoreItems.init();
        helper.register(Names.Items.IB_WAND_WOOD, CoreItems.WAND_WOOD);
        helper.register(Names.Items.IB_WAND_STONE, CoreItems.WAND_STONE);
        helper.register(Names.Items.IB_WAND_IRON, CoreItems.WAND_IRON);
        helper.register(Names.Items.IB_WAND_GOLD, CoreItems.WAND_GOLD);
        helper.register(Names.Items.IB_WAND_DIAMOND, CoreItems.WAND_DIAMOND);
        helper.register(Names.Items.IB_WAND_NETHERITE, CoreItems.WAND_NETHERITE);
    }
}