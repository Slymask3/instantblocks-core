package com.slymask3.instantblocks.core.builder;

import com.google.gson.JsonObject;
import com.slymask3.instantblocks.core.Core;
import com.slymask3.instantblocks.core.util.Helper;
import com.slymask3.instantblocks.core.util.IModLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fuel {
    public Map<Item,Double> map;

    public Fuel() {
        this.map = new HashMap<>();
    }

    public void add(Block block, int value) {
        this.add(block.asItem(),(double)value);
    }

    public void add(Item item, int value) {
        this.add(item,(double)value);
    }

    public void add(Block block, double value) {
        this.add(block.asItem(),value);
    }

    public void add(Item item, double value) {
        if(this.contains(item)) {
            this.map.replace(item,value);
        } else {
            this.map.put(item,value);
        }
    }

    public void add(TagKey<?> tag, IModLoader.ItemCallable itemCallable) {
        Core.LOADER.forEachItem(tag,itemCallable);
    }

    public void add(String tag, IModLoader.ItemCallable itemCallable) {
        Core.LOADER.forEachItem(Core.LOADER.getItemTagKey(tag),itemCallable);
        Core.LOADER.forEachItem(Core.LOADER.getBlockTagKey(tag),itemCallable);
    }

    public Map<Item,Double> get() {
        return this.map;
    }

    public double get(Block block) {
        return this.get(block.asItem());
    }

    public double get(Item item) {
        return this.map.getOrDefault(item, null);
    }

    public boolean contains(Block block) {
        return this.map.containsKey(block.asItem());
    }

    public boolean contains(Item item) {
        return this.map.containsKey(item);
    }

    public int size() {
        return this.map.size();
    }

    public static void setup() {
        Core.FUEL = new Fuel();

        Core.FUEL.add(BlockTags.BASE_STONE_OVERWORLD,(item) -> Core.FUEL.add(item,1));
        Core.FUEL.add(BlockTags.BASE_STONE_OVERWORLD,(item) -> Core.FUEL.add(item,1));
        Core.FUEL.add(BlockTags.BASE_STONE_NETHER,(item) -> Core.FUEL.add(item,1));
        Core.FUEL.add(BlockTags.NYLIUM,(item) -> Core.FUEL.add(item,1));
        Core.FUEL.add(BlockTags.SAND,(item) -> Core.FUEL.add(item,1));
        Core.FUEL.add(BlockTags.COAL_ORES,(item) -> Core.FUEL.add(item,16)); //todo
        Core.FUEL.add(BlockTags.IRON_ORES,(item) -> Core.FUEL.add(item,32)); //todo
        Core.FUEL.add(BlockTags.COPPER_ORES,(item) -> Core.FUEL.add(item,32)); //todo
        Core.FUEL.add(BlockTags.GOLD_ORES,(item) -> Core.FUEL.add(item,64)); //todo
        Core.FUEL.add(BlockTags.REDSTONE_ORES,(item) -> Core.FUEL.add(item,32)); //todo
        Core.FUEL.add(BlockTags.EMERALD_ORES,(item) -> Core.FUEL.add(item,128)); //todo
        Core.FUEL.add(BlockTags.LAPIS_ORES,(item) -> Core.FUEL.add(item,32)); //todo
        Core.FUEL.add(BlockTags.DIAMOND_ORES,(item) -> Core.FUEL.add(item,128)); //todo
        Core.FUEL.add(BlockTags.WOOL,(item) -> Core.FUEL.add(item,8)); //todo
        Core.FUEL.add(BlockTags.LEAVES,(item) -> Core.FUEL.add(item,1)); //todo

        Core.FUEL.add(ItemTags.DIRT,(item) -> Core.FUEL.add(item,1));
        Core.FUEL.add(ItemTags.PLANKS,(item) -> Core.FUEL.add(item,1));
        Core.FUEL.add(ItemTags.SAPLINGS,(item) -> Core.FUEL.add(item,4)); //todo
        Core.FUEL.add(ItemTags.SIGNS,(item) -> Core.FUEL.add(item,2)); //todo 2.166
        Core.FUEL.add(ItemTags.WOODEN_FENCES,(item) -> Core.FUEL.add(item,1.5)); //todo 1.666
        Core.FUEL.add(ItemTags.WOOL_CARPETS,(item) -> Core.FUEL.add(item,5.25)); //todo 5.333

        Core.FUEL.add(Items.CALCITE,1);
        Core.FUEL.add(Items.DRIPSTONE_BLOCK,1);
        Core.FUEL.add(Items.GRAVEL,1);
        Core.FUEL.add(Items.NETHER_QUARTZ_ORE,16); //todo
        Core.FUEL.add(Items.ANCIENT_DEBRIS,256); //todo
        Core.FUEL.add(Items.AMETHYST_SHARD,8); //todo
        Core.FUEL.add(Items.SPONGE,32); //todo
        Core.FUEL.add(Items.WET_SPONGE,32); //todo
        Core.FUEL.add(Items.SUGAR_CANE,4); //todo
        Core.FUEL.add(Items.CLAY_BALL,8); //todo
        Core.FUEL.add(Items.LEATHER,12); //todo
        Core.FUEL.add(Items.OBSIDIAN,16); //todo
        Core.FUEL.add(Items.LADDER,1); //todo 1.166
        Core.FUEL.add(Items.SOUL_SAND,1); //todo
        Core.FUEL.add(Items.SOUL_SOIL,1); //todo
        Core.FUEL.add(Items.ICE,2); //todo
        Core.FUEL.add(Items.WHEAT,4); //todo


        Core.FUEL.add(Items.STICK,Core.FUEL.get(Items.OAK_PLANKS)/2);

        Core.FUEL.add(BlockTags.FENCE_GATES,(item) -> Core.FUEL.add(item,Core.FUEL.get(Items.OAK_PLANKS)*2+Core.FUEL.get(Items.STICK)*4));

        Core.FUEL.add(ItemTags.WOODEN_SLABS,(item) -> Core.FUEL.add(item,Core.FUEL.get(Items.OAK_PLANKS)/2));
        Core.FUEL.add(ItemTags.LOGS,(item) -> Core.FUEL.add(item,Core.FUEL.get(Items.OAK_PLANKS)*4));
        Core.FUEL.add(ItemTags.COALS,(item) -> Core.FUEL.add(item,Core.FUEL.get(Items.COAL_ORE)));
        Core.FUEL.add(ItemTags.STONE_BRICKS,(item) -> Core.FUEL.add(item,Core.FUEL.get(Items.STONE)));
        Core.FUEL.add(ItemTags.BANNERS,(item) -> Core.FUEL.add(item,Core.FUEL.get(Items.WHITE_WOOL)*6+Core.FUEL.get(Items.STICK)));
        Core.FUEL.add(ItemTags.WOODEN_STAIRS,(item) -> Core.FUEL.add(item,Core.FUEL.get(Items.OAK_PLANKS)*6/4));
        Core.FUEL.add(ItemTags.TERRACOTTA,(item) -> Core.FUEL.add(item,Core.FUEL.get(Items.CLAY_BALL)*4));

        Core.FUEL.add(Items.POLISHED_GRANITE,Core.FUEL.get(Items.GRANITE));
        Core.FUEL.add(Items.POLISHED_DIORITE,Core.FUEL.get(Items.DIORITE));
        Core.FUEL.add(Items.POLISHED_ANDESITE,Core.FUEL.get(Items.ANDESITE));
        Core.FUEL.add(Items.COBBLED_DEEPSLATE,Core.FUEL.get(Items.DEEPSLATE));
        Core.FUEL.add(Items.POLISHED_DEEPSLATE,Core.FUEL.get(Items.DEEPSLATE));
        Core.FUEL.add(Items.POINTED_DRIPSTONE,Core.FUEL.get(Items.DRIPSTONE_BLOCK)/4);
        Core.FUEL.add(Items.COBBLESTONE,Core.FUEL.get(Items.STONE));
        Core.FUEL.add(Items.RAW_COPPER,Core.FUEL.get(Items.COPPER_ORE));
        Core.FUEL.add(Items.COPPER_INGOT,Core.FUEL.get(Items.RAW_COPPER));
        Core.FUEL.add(Items.RAW_IRON,Core.FUEL.get(Items.IRON_ORE));
        Core.FUEL.add(Items.IRON_INGOT,Core.FUEL.get(Items.RAW_IRON));
        Core.FUEL.add(Items.RAW_GOLD,Core.FUEL.get(Items.GOLD_ORE));
        Core.FUEL.add(Items.GOLD_INGOT,Core.FUEL.get(Items.RAW_GOLD));
        Core.FUEL.add(Items.LAPIS_LAZULI,Core.FUEL.get(Items.LAPIS_ORE)*9); //todo drops 4-9
        Core.FUEL.add(Items.REDSTONE,Core.FUEL.get(Items.REDSTONE_ORE)*5); //todo drops 4-5
        Core.FUEL.add(Items.DIAMOND,Core.FUEL.get(Items.DIAMOND_ORE));
        Core.FUEL.add(Items.EMERALD,Core.FUEL.get(Items.EMERALD_ORE));
        Core.FUEL.add(Items.COAL_BLOCK,Core.FUEL.get(Items.COAL)*9);
        Core.FUEL.add(Items.RAW_COPPER_BLOCK,Core.FUEL.get(Items.RAW_COPPER)*9);
        Core.FUEL.add(Items.COPPER_BLOCK,Core.FUEL.get(Items.COPPER_INGOT)*9);
        Core.FUEL.add(Items.RAW_IRON_BLOCK,Core.FUEL.get(Items.RAW_IRON)*9);
        Core.FUEL.add(Items.IRON_BLOCK,Core.FUEL.get(Items.IRON_INGOT)*9);
        Core.FUEL.add(Items.GOLD_BLOCK,Core.FUEL.get(Items.GOLD_INGOT)*9);
        Core.FUEL.add(Items.RAW_GOLD_BLOCK,Core.FUEL.get(Items.GOLD_INGOT)*9);
        Core.FUEL.add(Items.LAPIS_BLOCK,Core.FUEL.get(Items.LAPIS_LAZULI)*9);
        Core.FUEL.add(Items.REDSTONE_BLOCK,Core.FUEL.get(Items.REDSTONE)*9);
        Core.FUEL.add(Items.DIAMOND_BLOCK,Core.FUEL.get(Items.DIAMOND)*9);
        Core.FUEL.add(Items.EMERALD_BLOCK,Core.FUEL.get(Items.EMERALD)*9);
        Core.FUEL.add(Items.AMETHYST_BLOCK,Core.FUEL.get(Items.AMETHYST_SHARD)*4);
        Core.FUEL.add(Items.NETHERITE_SCRAP,Core.FUEL.get(Items.ANCIENT_DEBRIS));
        Core.FUEL.add(Items.NETHERITE_INGOT,Core.FUEL.get(Items.ANCIENT_DEBRIS)*4+Core.FUEL.get(Items.GOLD_INGOT)*4);
        Core.FUEL.add(Items.NETHERITE_BLOCK,Core.FUEL.get(Items.NETHERITE_INGOT)*9);
        Core.FUEL.add(Items.EXPOSED_COPPER,Core.FUEL.get(Items.COPPER_BLOCK));
        Core.FUEL.add(Items.WEATHERED_COPPER,Core.FUEL.get(Items.COPPER_BLOCK));
        Core.FUEL.add(Items.OXIDIZED_COPPER,Core.FUEL.get(Items.COPPER_BLOCK));
        Core.FUEL.add(Items.CUT_COPPER,Core.FUEL.get(Items.COPPER_BLOCK));
        Core.FUEL.add(Items.EXPOSED_CUT_COPPER,Core.FUEL.get(Items.COPPER_BLOCK)/4);
        Core.FUEL.add(Items.WEATHERED_CUT_COPPER,Core.FUEL.get(Items.COPPER_BLOCK)/4);
        Core.FUEL.add(Items.OXIDIZED_CUT_COPPER,Core.FUEL.get(Items.COPPER_BLOCK)/4);
        Core.FUEL.add(Items.CUT_COPPER_STAIRS,Core.FUEL.get(Items.COPPER_BLOCK)/4);
        Core.FUEL.add(Items.EXPOSED_CUT_COPPER_STAIRS,Core.FUEL.get(Items.COPPER_BLOCK)/4);
        Core.FUEL.add(Items.WEATHERED_CUT_COPPER_STAIRS,Core.FUEL.get(Items.COPPER_BLOCK)/4);
        Core.FUEL.add(Items.OXIDIZED_CUT_COPPER_STAIRS,Core.FUEL.get(Items.COPPER_BLOCK)/4);
        Core.FUEL.add(Items.CUT_COPPER_SLAB,Core.FUEL.get(Items.COPPER_BLOCK)/8);
        Core.FUEL.add(Items.EXPOSED_CUT_COPPER_SLAB,Core.FUEL.get(Items.COPPER_BLOCK)/8);
        Core.FUEL.add(Items.WEATHERED_CUT_COPPER_SLAB,Core.FUEL.get(Items.COPPER_BLOCK)/8);
        Core.FUEL.add(Items.OXIDIZED_CUT_COPPER_SLAB,Core.FUEL.get(Items.COPPER_BLOCK)/8);
        Core.FUEL.add(Items.WAXED_COPPER_BLOCK,Core.FUEL.get(Items.COPPER_BLOCK));
        Core.FUEL.add(Items.WAXED_EXPOSED_COPPER,Core.FUEL.get(Items.COPPER_BLOCK));
        Core.FUEL.add(Items.WAXED_WEATHERED_COPPER,Core.FUEL.get(Items.COPPER_BLOCK));
        Core.FUEL.add(Items.WAXED_OXIDIZED_COPPER,Core.FUEL.get(Items.COPPER_BLOCK));
        Core.FUEL.add(Items.WAXED_CUT_COPPER,Core.FUEL.get(Items.COPPER_BLOCK)/4);
        Core.FUEL.add(Items.WAXED_EXPOSED_CUT_COPPER,Core.FUEL.get(Items.COPPER_BLOCK)/4);
        Core.FUEL.add(Items.WAXED_WEATHERED_CUT_COPPER,Core.FUEL.get(Items.COPPER_BLOCK)/4);
        Core.FUEL.add(Items.WAXED_OXIDIZED_CUT_COPPER,Core.FUEL.get(Items.COPPER_BLOCK)/4);
        Core.FUEL.add(Items.WAXED_CUT_COPPER_SLAB,Core.FUEL.get(Items.COPPER_BLOCK)/8);
        Core.FUEL.add(Items.WAXED_EXPOSED_CUT_COPPER_SLAB,Core.FUEL.get(Items.COPPER_BLOCK)/8);
        Core.FUEL.add(Items.WAXED_WEATHERED_CUT_COPPER_SLAB,Core.FUEL.get(Items.COPPER_BLOCK)/8);
        Core.FUEL.add(Items.WAXED_OXIDIZED_CUT_COPPER_SLAB,Core.FUEL.get(Items.COPPER_BLOCK)/8);
        Core.FUEL.add(Items.GLASS,Core.FUEL.get(Items.SAND));
        Core.FUEL.add(Items.TINTED_GLASS,Core.FUEL.get(Items.GLASS)+Core.FUEL.get(Items.AMETHYST_SHARD)*4);
        Core.FUEL.add(Items.SANDSTONE,Core.FUEL.get(Items.SAND)*4);
        Core.FUEL.add(Items.CHISELED_SANDSTONE,Core.FUEL.get(Items.SAND)*4);
        Core.FUEL.add(Items.CUT_SANDSTONE,Core.FUEL.get(Items.SAND)*4);
        Core.FUEL.add(Items.RED_SANDSTONE,Core.FUEL.get(Items.RED_SAND)*4);
        Core.FUEL.add(Items.CHISELED_RED_SANDSTONE,Core.FUEL.get(Items.RED_SAND)*4);
        Core.FUEL.add(Items.CUT_RED_SANDSTONE,Core.FUEL.get(Items.RED_SAND)*4);
        Core.FUEL.add(Items.BAMBOO,Core.FUEL.get(Items.STICK)/2);
        Core.FUEL.add(Items.SUGAR,Core.FUEL.get(Items.SUGAR_CANE));
        Core.FUEL.add(Items.PAPER,Core.FUEL.get(Items.SUGAR_CANE));
        Core.FUEL.add(Items.SMOOTH_STONE,Core.FUEL.get(Items.STONE));
        Core.FUEL.add(Items.STONE_SLAB,Core.FUEL.get(Items.STONE)/2);
        Core.FUEL.add(Items.SMOOTH_STONE_SLAB,Core.FUEL.get(Items.SMOOTH_STONE)/2);
        Core.FUEL.add(Items.COBBLESTONE_SLAB,Core.FUEL.get(Items.COBBLESTONE)/2);
        Core.FUEL.add(Items.SANDSTONE_SLAB,Core.FUEL.get(Items.SANDSTONE)/2);
        Core.FUEL.add(Blocks.CUT_SANDSTONE_SLAB,Core.FUEL.get(Items.SANDSTONE)/2);
        Core.FUEL.add(Items.STONE_BRICK_SLAB,Core.FUEL.get(Items.STONE_BRICKS)/2);
        Core.FUEL.add(Items.CLAY,Core.FUEL.get(Items.CLAY_BALL)*4);
        Core.FUEL.add(Items.BRICK,Core.FUEL.get(Items.CLAY_BALL));
        Core.FUEL.add(Items.BRICKS,Core.FUEL.get(Items.BRICK)*4);
        Core.FUEL.add(Items.SMOOTH_SANDSTONE,Core.FUEL.get(Items.SANDSTONE));
        Core.FUEL.add(Items.SMOOTH_RED_SANDSTONE,Core.FUEL.get(Items.SANDSTONE));
        Core.FUEL.add(Items.RABBIT_HIDE,Core.FUEL.get(Items.LEATHER)/4);
        Core.FUEL.add(Items.TORCH,(Core.FUEL.get(Items.COAL)+Core.FUEL.get(Items.STICK))/4);
        Core.FUEL.add(Items.SOUL_TORCH,(Core.FUEL.get(Items.COAL)+Core.FUEL.get(Items.STICK)+Core.FUEL.get(Items.SOUL_SAND))/4);
        Core.FUEL.add(Items.REDSTONE_TORCH,Core.FUEL.get(Items.REDSTONE)+Core.FUEL.get(Items.STICK));
        Core.FUEL.add(Items.CHEST,Core.FUEL.get(Items.OAK_PLANKS)*8);
        Core.FUEL.add(Items.CRAFTING_TABLE,Core.FUEL.get(Items.OAK_PLANKS)*4);
        Core.FUEL.add(Items.FURNACE,Core.FUEL.get(Items.COBBLESTONE)*8);
        Core.FUEL.add(Items.CRYING_OBSIDIAN,Core.FUEL.get(Items.OBSIDIAN));
        Core.FUEL.add(Items.BOOK,Core.FUEL.get(Items.PAPER)*3+Core.FUEL.get(Items.LEATHER));
        Core.FUEL.add(Items.BOOKSHELF,Core.FUEL.get(Items.BOOK)*3+Core.FUEL.get(Items.OAK_PLANKS)*6);
        Core.FUEL.add(Items.ENCHANTING_TABLE,Core.FUEL.get(Items.OBSIDIAN)*4+Core.FUEL.get(Items.DIAMOND)*2+Core.FUEL.get(Items.BOOK));
        Core.FUEL.add(Items.ARMOR_STAND,Core.FUEL.get(Items.STICK)*6+Core.FUEL.get(Items.SMOOTH_STONE_SLAB));
        Core.FUEL.add(Items.CAMPFIRE,Core.FUEL.get(Items.STICK)*3+Core.FUEL.get(Items.OAK_LOG)*3+Core.FUEL.get(Items.COAL));
        Core.FUEL.add(Items.SOUL_CAMPFIRE,Core.FUEL.get(Items.STICK)*3+Core.FUEL.get(Items.OAK_LOG)*3+Core.FUEL.get(Items.SOUL_SAND));
        Core.FUEL.add(Items.IRON_NUGGET,Core.FUEL.get(Items.IRON_INGOT)/9);
        Core.FUEL.add(Items.GOLD_NUGGET,Core.FUEL.get(Items.GOLD_INGOT)/9);
        Core.FUEL.add(Items.PACKED_ICE,Core.FUEL.get(Items.ICE)*9);
        Core.FUEL.add(Items.BLUE_ICE,Core.FUEL.get(Items.PACKED_ICE)*9);
        Core.FUEL.add(Items.JUKEBOX,Core.FUEL.get(Items.OAK_PLANKS)*8+Core.FUEL.get(Items.DIAMOND));
        Core.FUEL.add(Items.GLASS_PANE,Core.FUEL.get(Items.GLASS)*6/16);
        Core.FUEL.add(Items.IRON_BARS,Core.FUEL.get(Items.IRON_INGOT)*6/16);
        Core.FUEL.add(Items.CHAIN,Core.FUEL.get(Items.IRON_NUGGET)*2+Core.FUEL.get(Items.IRON_INGOT));
        Core.FUEL.add(Items.LANTERN,Core.FUEL.get(Items.IRON_NUGGET)*8+Core.FUEL.get(Items.TORCH));
        Core.FUEL.add(Items.SOUL_LANTERN,Core.FUEL.get(Items.IRON_NUGGET)*8+Core.FUEL.get(Items.SOUL_TORCH));
        Core.FUEL.add(Items.PACKED_MUD,Core.FUEL.get(Items.MUD)+Core.FUEL.get(Items.WHEAT));
        Core.FUEL.add(Items.HAY_BLOCK,Core.FUEL.get(Items.WHEAT)*9);
        Core.FUEL.add(Items.BREAD,Core.FUEL.get(Items.WHEAT)*3);
        Core.FUEL.add(Items.ANVIL,Core.FUEL.get(Items.IRON_INGOT)*4+Core.FUEL.get(Items.IRON_BLOCK)*3);

        overwrite();

        Core.LOG.info("Registered {} items as fuel",Core.FUEL.size());
    }

    private static void overwrite() {
        JsonObject json = Helper.getJsonFromFile(Core.CONFIG_DIR + "/fuel.json", JsonObject.class, getDefaultFuels());
        if(json != null) {
            for(String key : json.keySet()) {
                Item item = Core.LOADER.getItem(key);
                if(!item.equals(Items.AIR)) {
                    Core.FUEL.add(item,json.get(key).getAsDouble());
                }
            }
        }
    }

    private static JsonObject getDefaultFuels() {
        JsonObject jsonDefault = new JsonObject();
        jsonDefault.addProperty(Core.LOADER.getKey(Blocks.STONE),1.000);
        return jsonDefault;
    }

    public static void addTooltip(ItemStack itemStack, List<Component> lines) {
        Item item = itemStack.getItem();
        if(Core.FUEL.contains(item)) {
            lines.add(Component.translatable("ib.tooltip.fuel",Core.FUEL.get(item)));
        }
    }
}