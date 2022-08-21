package com.slymask3.instantblocks.core.item;

import com.slymask3.instantblocks.core.Core;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;

public class InstantWandItem extends TieredItem {
	public InstantWandItem(Tier tier) {
		super(tier,new Item.Properties().tab(Core.ITEM_GROUP));
	}
}