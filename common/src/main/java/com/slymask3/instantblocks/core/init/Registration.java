package com.slymask3.instantblocks.core.init;

import com.slymask3.instantblocks.core.Core;
import com.slymask3.instantblocks.core.registry.CoreBlocks;
import com.slymask3.instantblocks.core.registry.CoreItems;
import com.slymask3.instantblocks.core.registry.CoreMenus;
import com.slymask3.instantblocks.core.registry.CoreTiles;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class Registration {
    public static final class Names {
        public static final String WAND_CHARGE = "wand_charge";
        public static final String WAND_WOOD = "wand_wood";
        public static final String WAND_STONE = "wand_stone";
        public static final String WAND_IRON = "wand_iron";
        public static final String WAND_GOLD = "wand_gold";
        public static final String WAND_DIAMOND = "wand_diamond";
        public static final String WAND_NETHERITE = "wand_netherite";
    }

    public static void registerBlocks(IRegistryHelper<Block> helper) {
        CoreBlocks.init();
        helper.register(resource(Names.WAND_CHARGE), CoreBlocks.WAND_CHARGE);
    }

    public static void registerItems(IRegistryHelper<Item> helper) {
        CoreItems.init();
        helper.register(resource(Names.WAND_WOOD), CoreItems.WAND_WOOD);
        helper.register(resource(Names.WAND_STONE), CoreItems.WAND_STONE);
        helper.register(resource(Names.WAND_IRON), CoreItems.WAND_IRON);
        helper.register(resource(Names.WAND_GOLD), CoreItems.WAND_GOLD);
        helper.register(resource(Names.WAND_DIAMOND), CoreItems.WAND_DIAMOND);
        helper.register(resource(Names.WAND_NETHERITE), CoreItems.WAND_NETHERITE);
        helper.register(resource(Names.WAND_CHARGE), blockItem(CoreBlocks.WAND_CHARGE));
    }

    public static void registerTiles(IRegistryHelper<BlockEntityType<?>> helper) {
        Core.TILES.init();
        helper.register(resource(Names.WAND_CHARGE), CoreTiles.WAND_CHARGE);
    }

    public static void registerMenus(IRegistryHelper<MenuType<?>> helper) {
        Core.MENUS.init();
        helper.register(resource(Names.WAND_CHARGE), CoreMenus.WAND_CHARGE);
        Core.MENUS.link();
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