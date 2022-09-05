package com.slymask3.instantblocks.core.util;

import com.google.gson.JsonObject;
import com.slymask3.instantblocks.core.Core;
import com.slymask3.instantblocks.core.item.InstantWandItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class WandHelper {
    public static void setup() {
        Core.FUEL.put(Items.STONE,1.0);
        Core.FUEL.put(Items.GRANITE,1.0);
        Core.FUEL.put(Items.DIORITE,1.0);
        Core.FUEL.put(Items.ANDESITE,1.0);
        Core.FUEL.put(Items.DEEPSLATE,1.0);
        Core.FUEL.put(Items.CALCITE,1.0);
        Core.FUEL.put(Items.TUFF,1.0);
        Core.FUEL.put(Items.DRIPSTONE_BLOCK,1.0);
        Core.FUEL.put(Items.DIRT,1.0);
        Core.FUEL.put(Items.NETHERRACK,1.0);
        Core.FUEL.put(Items.OAK_PLANKS,1.0);
        Core.FUEL.put(Items.SPRUCE_PLANKS,1.0);
        Core.FUEL.put(Items.BIRCH_PLANKS,1.0);
        Core.FUEL.put(Items.JUNGLE_PLANKS,1.0);
        Core.FUEL.put(Items.ACACIA_PLANKS,1.0);
        Core.FUEL.put(Items.DARK_OAK_PLANKS,1.0);
        Core.FUEL.put(Items.MANGROVE_PLANKS,1.0);
        Core.FUEL.put(Items.CRIMSON_PLANKS,1.0);
        Core.FUEL.put(Items.WARPED_PLANKS,1.0);
        
        Core.FUEL.put(Items.POLISHED_GRANITE,Core.FUEL.get(Items.GRANITE));
        Core.FUEL.put(Items.POLISHED_DIORITE,Core.FUEL.get(Items.DIORITE));
        Core.FUEL.put(Items.POLISHED_ANDESITE,Core.FUEL.get(Items.ANDESITE));
        Core.FUEL.put(Items.COBBLED_DEEPSLATE,Core.FUEL.get(Items.DEEPSLATE));
        Core.FUEL.put(Items.POLISHED_DEEPSLATE,Core.FUEL.get(Items.DEEPSLATE));
        Core.FUEL.put(Items.POINTED_DRIPSTONE,Core.FUEL.get(Items.DRIPSTONE_BLOCK)/4);
        Core.FUEL.put(Items.GRASS_BLOCK,Core.FUEL.get(Items.DIRT));
        Core.FUEL.put(Items.COARSE_DIRT,(Core.FUEL.get(Items.DIRT)+Core.FUEL.get(Items.GRAVEL))/2);
        Core.FUEL.put(Items.PODZOL,Core.FUEL.get(Items.DIRT));
        Core.FUEL.put(Items.ROOTED_DIRT,Core.FUEL.get(Items.DIRT));
        Core.FUEL.put(Items.MUD,Core.FUEL.get(Items.DIRT));
        Core.FUEL.put(Items.CRIMSON_NYLIUM,Core.FUEL.get(Items.NETHERRACK));
        Core.FUEL.put(Items.WARPED_NYLIUM,Core.FUEL.get(Items.NETHERRACK));
        Core.FUEL.put(Items.COBBLESTONE,Core.FUEL.get(Items.STONE));

        overwrite();
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