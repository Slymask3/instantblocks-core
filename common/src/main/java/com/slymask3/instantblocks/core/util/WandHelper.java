package com.slymask3.instantblocks.core.util;

import com.google.gson.JsonObject;
import com.slymask3.instantblocks.core.Core;
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
        Core.LOADER.getItemsByTag(BlockTags.BASE_STONE_OVERWORLD,(item) -> Core.FUEL.put(item,1.0));
        Core.LOADER.getItemsByTag(BlockTags.BASE_STONE_NETHER,(item) -> Core.FUEL.put(item,1.0));
        Core.LOADER.getItemsByTag(BlockTags.NYLIUM,(item) -> Core.FUEL.put(item,1.0));
        Core.LOADER.getItemsByTag(BlockTags.SAND,(item) -> Core.FUEL.put(item,1.0));
        Core.LOADER.getItemsByTag(BlockTags.COAL_ORES,(item) -> Core.FUEL.put(item,16.0)); //todo
        Core.LOADER.getItemsByTag(BlockTags.IRON_ORES,(item) -> Core.FUEL.put(item,32.0)); //todo
        Core.LOADER.getItemsByTag(BlockTags.COPPER_ORES,(item) -> Core.FUEL.put(item,32.0)); //todo
        Core.LOADER.getItemsByTag(BlockTags.GOLD_ORES,(item) -> Core.FUEL.put(item,64.0)); //todo
        Core.LOADER.getItemsByTag(BlockTags.REDSTONE_ORES,(item) -> Core.FUEL.put(item,32.0)); //todo
        Core.LOADER.getItemsByTag(BlockTags.EMERALD_ORES,(item) -> Core.FUEL.put(item,128.0)); //todo
        Core.LOADER.getItemsByTag(BlockTags.LAPIS_ORES,(item) -> Core.FUEL.put(item,32.0)); //todo
        Core.LOADER.getItemsByTag(BlockTags.DIAMOND_ORES,(item) -> Core.FUEL.put(item,128.0)); //todo
        Core.LOADER.getItemsByTag(BlockTags.WOOL,(item) -> Core.FUEL.put(item,8.0)); //todo

        Core.LOADER.getItemsByTag(ItemTags.DIRT,(item) -> Core.FUEL.put(item,1.0));
        Core.LOADER.getItemsByTag(ItemTags.PLANKS,(item) -> Core.FUEL.put(item,1.0));
        Core.LOADER.getItemsByTag(ItemTags.SAPLINGS,(item) -> Core.FUEL.put(item,4.0)); //todo

        Core.FUEL.put(Items.CALCITE,1.0);
        Core.FUEL.put(Items.DRIPSTONE_BLOCK,1.0);
        Core.FUEL.put(Items.GRAVEL,1.0);
        Core.FUEL.put(Items.NETHER_QUARTZ_ORE,16.0); //todo
        Core.FUEL.put(Items.ANCIENT_DEBRIS,256.0); //todo

        Core.FUEL.put(Items.POLISHED_GRANITE,Core.FUEL.get(Items.GRANITE));
        Core.FUEL.put(Items.POLISHED_DIORITE,Core.FUEL.get(Items.DIORITE));
        Core.FUEL.put(Items.POLISHED_ANDESITE,Core.FUEL.get(Items.ANDESITE));
        Core.FUEL.put(Items.COBBLED_DEEPSLATE,Core.FUEL.get(Items.DEEPSLATE));
        Core.FUEL.put(Items.POLISHED_DEEPSLATE,Core.FUEL.get(Items.DEEPSLATE));
        Core.FUEL.put(Items.POINTED_DRIPSTONE,Core.FUEL.get(Items.DRIPSTONE_BLOCK)/4);
        Core.FUEL.put(Items.COBBLESTONE,Core.FUEL.get(Items.STONE));

        Core.LOADER.getItemsByTag(ItemTags.LOGS,(item) -> Core.FUEL.put(item,Core.FUEL.get(Items.OAK_PLANKS)*4));

        overwrite();

        Core.LOG.info("fuel size: {}",Core.FUEL.size());
    }

    private static void overwrite() {
        JsonObject json = Helper.getJsonFromFile(Core.CONFIG_DIR + "/fuel.json", JsonObject.class, getDefaultFuels());
        if(json != null) {
            for(String key : json.keySet()) {
                Item item = Core.LOADER.getItem(key);
                if(!item.equals(Items.AIR)) {
                    if(Core.FUEL.containsKey(item)) {
                        Core.FUEL.replace(item,json.get(key).getAsDouble());
                    } else {
                        Core.FUEL.put(item,json.get(key).getAsDouble());
                    }
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
        return Core.FUEL.containsKey(itemStack.getItem());
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