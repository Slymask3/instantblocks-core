package com.slymask3.instantblocks.core.init;

import com.slymask3.instantblocks.core.Core;
import com.slymask3.instantblocks.core.reference.Names;
import com.slymask3.instantblocks.core.registry.CoreItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class Registration {
    public static void registerItems(IRegistryHelper<Item> helper) {
        CoreItems.init();
        helper.register(resource(Names.Items.IB_WAND_WOOD), CoreItems.WAND_WOOD);
        helper.register(resource(Names.Items.IB_WAND_STONE), CoreItems.WAND_STONE);
        helper.register(resource(Names.Items.IB_WAND_IRON), CoreItems.WAND_IRON);
        helper.register(resource(Names.Items.IB_WAND_GOLD), CoreItems.WAND_GOLD);
        helper.register(resource(Names.Items.IB_WAND_DIAMOND), CoreItems.WAND_DIAMOND);
        helper.register(resource(Names.Items.IB_WAND_NETHERITE), CoreItems.WAND_NETHERITE);
    }

    private static ResourceLocation resource(String name) {
        Core.LOG.info("Core.ITEM_GROUP: {}", Core.ITEM_GROUP);
        return new ResourceLocation(Core.FABRIC_MOD_ID,name);
    }
}