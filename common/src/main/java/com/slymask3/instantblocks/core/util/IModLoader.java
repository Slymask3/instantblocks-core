package com.slymask3.instantblocks.core.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface IModLoader {
    Item getItem(String string);
    String getKey(Block block);
    String getKey(Item item);
}