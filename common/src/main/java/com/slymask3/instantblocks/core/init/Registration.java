package com.slymask3.instantblocks.core.init;

import com.slymask3.instantblocks.core.Core;
import com.slymask3.instantblocks.core.registry.CoreBlocks;
import com.slymask3.instantblocks.core.registry.CoreItems;
import com.slymask3.instantblocks.core.registry.CoreMenus;
import com.slymask3.instantblocks.core.registry.CoreTiles;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class Registration {
    public static final class Names {
        public static final String WAND_CHARGE = "wand_charge";
        public static final String WAND_STARTER = "wand_starter";
        public static final String WAND_ADVANCED = "wand_advanced";
        public static final String WAND_ELITE = "wand_elite";
        public static final String WAND_CREATIVE = "wand_creative";
    }

    public static void registerBlocks(IRegistryHelper<Block> helper) {
        CoreBlocks.init();
        helper.register(resource(Names.WAND_CHARGE), CoreBlocks.WAND_CHARGE);
    }

    public static void registerItems(IRegistryHelper<Item> helper) {
        CoreItems.init();
        helper.register(resource(Names.WAND_CHARGE), blockItem(CoreBlocks.WAND_CHARGE));
        helper.register(resource(Names.WAND_STARTER), CoreItems.WAND_STARTER);
        helper.register(resource(Names.WAND_ADVANCED), CoreItems.WAND_ADVANCED);
        helper.register(resource(Names.WAND_ELITE), CoreItems.WAND_ELITE);
        helper.register(resource(Names.WAND_CREATIVE), CoreItems.WAND_CREATIVE);
    }

    public static void registerTiles(IRegistryHelper<BlockEntityType<?>> helper) {
        Core.TILES.init();
        helper.register(resource(Names.WAND_CHARGE), CoreTiles.WAND_CHARGE);
    }

    public static void registerMenus(IRegistryHelper<MenuType<?>> helper) {
        Core.MENUS.init();
        helper.register(resource(Names.WAND_CHARGE), CoreMenus.WAND_CHARGE);
    }

    private static ResourceLocation resource(String name) {
        Core.LOG.info("Core.ITEM_GROUP: {}", Core.ITEM_GROUP);
        return new ResourceLocation(Core.FABRIC_MOD_ID,name);
    }

    private static BlockItem blockItem(Block block) {
        Core.LOG.info("Core.ITEM_GROUP: {}", Core.ITEM_GROUP);
        return new BlockItem(block,new Item.Properties().tab(Core.ITEM_GROUP));
    }
}