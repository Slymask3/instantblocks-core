package com.slymask3.instantblocks.core.builder;

import com.slymask3.instantblocks.core.Core;
import com.slymask3.instantblocks.core.util.IModLoader;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
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
        Core.LOADER.getItemsByTag(tag,itemCallable);
    }

    public void add(String tag, IModLoader.ItemCallable itemCallable) {
        Core.LOADER.getItemsByTag(Core.LOADER.getItemTagKey(tag),itemCallable);
        Core.LOADER.getItemsByTag(Core.LOADER.getBlockTagKey(tag),itemCallable);
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
}