package com.slymask3.instantblocks.core.item;

import com.slymask3.instantblocks.core.Common;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;

public class InstantWandItem extends TieredItem {
	public InstantWandItem(Tier tier) {
		super(tier,new Item.Properties().tab(Common.ITEM_GROUP));
	}
}