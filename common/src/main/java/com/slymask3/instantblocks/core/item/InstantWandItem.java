package com.slymask3.instantblocks.core.item;

import com.slymask3.instantblocks.core.Core;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class InstantWandItem extends Item {
	private final int maxCharge;

	public InstantWandItem(int maxCharge) {
		super(new Item.Properties().tab(Core.ITEM_GROUP).stacksTo(1));
		this.maxCharge = maxCharge;
	}

	public int getMaxCharge() {
		return this.maxCharge;
	}

	public static int getCharge(ItemStack itemStack) {
		CompoundTag tag = itemStack.getTag();
		if(tag != null) {
			return tag.getInt("Charge");
		}
		return 0;
	}

	public boolean isBarVisible(ItemStack itemStack) {
		return true;
	}

	public int getBarWidth(ItemStack itemStack) {
		return Math.round((float)getCharge(itemStack) * 13.0F / (float)this.getMaxCharge());
	}

	public int getBarColor(ItemStack itemStack) {
		float f = Math.max(0.0F, (float)getCharge(itemStack) / (float)this.getMaxCharge());
		return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
	}

	public boolean isEnchantable(ItemStack itemStack) {
		return false;
	}

//	public ItemStack getDefaultInstance() {
//		ItemStack itemStack = new ItemStack(this);
//		itemStack.setDamageValue(itemStack.getMaxDamage());
//		return itemStack;
//	}

	public void appendHoverText(ItemStack itemStack, Level world, List<Component> list, TooltipFlag flag) {
		list.add(Component.translatable("ib.tooltip.wand", String.valueOf(getCharge(itemStack)), String.valueOf(this.getMaxCharge())));
	}
}