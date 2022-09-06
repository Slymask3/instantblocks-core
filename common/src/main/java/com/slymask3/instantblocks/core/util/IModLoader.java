package com.slymask3.instantblocks.core.util;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface IModLoader {
    Item getItem(String string);
    String getKey(Block block);
    String getKey(Item item);
    TagKey<Item> getItemTagKey(String string);
    TagKey<Block> getBlockTagKey(String string);
    void getItemsByTag(TagKey<?> tag, ItemCallable itemCallable);

    interface ItemCallable {
        void call(Item item);
    }
}