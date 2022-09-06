package com.slymask3.instantblocks.core.util;

import com.google.gson.JsonObject;
import com.slymask3.instantblocks.core.Core;
import com.slymask3.instantblocks.core.builder.Fuel;
import com.slymask3.instantblocks.core.item.InstantWandItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class WandHelper {
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

        Core.FUEL.add(Items.CALCITE,1);
        Core.FUEL.add(Items.DRIPSTONE_BLOCK,1);
        Core.FUEL.add(Items.GRAVEL,1);
        Core.FUEL.add(Items.NETHER_QUARTZ_ORE,16); //todo
        Core.FUEL.add(Items.ANCIENT_DEBRIS,256); //todo
        Core.FUEL.add(Items.AMETHYST_SHARD,8); //todo
        Core.FUEL.add(Items.SPONGE,32); //todo
        Core.FUEL.add(Items.WET_SPONGE,32); //todo

        Core.FUEL.add(ItemTags.LOGS,(item) -> Core.FUEL.add(item,Core.FUEL.get(Items.OAK_PLANKS)*4));
        Core.FUEL.add(ItemTags.COALS,(item) -> Core.FUEL.add(item,Core.FUEL.get(Items.COAL_ORE)));

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
        Core.FUEL.add(Items.LAPIS_LAZULI,Core.FUEL.get(Items.LAPIS_ORE)*3); //todo
        Core.FUEL.add(Items.DIAMOND,Core.FUEL.get(Items.DIAMOND_ORE));
        Core.FUEL.add(Items.COAL_BLOCK,Core.FUEL.get(Items.COAL)*9);
        Core.FUEL.add(Items.RAW_COPPER_BLOCK,Core.FUEL.get(Items.RAW_COPPER)*9);
        Core.FUEL.add(Items.COPPER_BLOCK,Core.FUEL.get(Items.COPPER_INGOT)*9);
        Core.FUEL.add(Items.RAW_IRON_BLOCK,Core.FUEL.get(Items.RAW_IRON)*9);
        Core.FUEL.add(Items.IRON_BLOCK,Core.FUEL.get(Items.IRON_INGOT)*9);
        Core.FUEL.add(Items.GOLD_BLOCK,Core.FUEL.get(Items.GOLD_INGOT)*9);
        Core.FUEL.add(Items.LAPIS_BLOCK,Core.FUEL.get(Items.LAPIS_LAZULI)*9);
        Core.FUEL.add(Items.DIAMOND_BLOCK,Core.FUEL.get(Items.DIAMOND)*9);
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

        overwrite();

        Core.LOG.info("fuel size: {}",Core.FUEL.size());
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

    public static boolean isWand(ItemStack itemStack) {
        if(itemStack == null) return false;
        return itemStack.getItem() instanceof InstantWandItem;
    }

    public static boolean isWandFuel(ItemStack itemStack) {
        if(itemStack == null) return false;
        return Core.FUEL.contains(itemStack.getItem());
    }

    public static double getWandCharge(ItemStack wand) {
        if(wand.getItem() instanceof InstantWandItem) {
            CompoundTag tag = wand.getOrCreateTag();
            return tag.getDouble("Charge");
        }
        return 0;
    }

    public static boolean isWandFullyCharged(ItemStack wand) {
        if(wand.getItem() instanceof InstantWandItem wandItem) {
            double charge = getWandCharge(wand);
            return charge >= wandItem.getMaxCharge();
        }
        return false;
    }

    public static void addWandCharge(ItemStack wand, double value) {
        if(wand.getItem() instanceof InstantWandItem wandItem && !wandItem.isCreative()) {
            CompoundTag tag = wand.getOrCreateTag();
            double charge = tag.getDouble("Charge");
            tag.putDouble("Charge",Math.min(charge+value,wandItem.getMaxCharge()));
        }
    }

    public static void removeWandCharge(ItemStack wand, double value) {
        if(wand.getItem() instanceof InstantWandItem wandItem && !wandItem.isCreative()) {
            CompoundTag tag = wand.getOrCreateTag();
            double charge = tag.getDouble("Charge");
            tag.putDouble("Charge",Math.max(charge-value,0));
        }
    }

    public static boolean hasEnoughWandCharge(ItemStack wand, double value) {
        if(wand.getItem() instanceof InstantWandItem wandItem) {
            if(wandItem.isCreative()) return true;
            CompoundTag tag = wand.getOrCreateTag();
            double charge = tag.getDouble("Charge");
            Core.LOG.info("hasEnoughWandCharge() - value: {}, charge: {}", value, charge);
            return value <= charge;
        }
        return false;
    }
}