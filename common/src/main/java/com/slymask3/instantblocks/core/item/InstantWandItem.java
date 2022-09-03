package com.slymask3.instantblocks.core.item;

import com.slymask3.instantblocks.core.Core;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class InstantWandItem extends Item {
	private final double maxCharge;
	private final int chargeSpeed;

	public InstantWandItem(double maxCharge, int chargeSpeed) {
		this(maxCharge, chargeSpeed, Rarity.COMMON);
	}

	public InstantWandItem(double maxCharge, int chargeSpeed, Rarity rarity) {
		super(new Item.Properties().tab(Core.ITEM_GROUP).stacksTo(1).rarity(rarity));
		this.maxCharge = maxCharge;
		this.chargeSpeed = chargeSpeed;
	}

	public double getMaxCharge() {
		return this.maxCharge;
	}

	public int getChargeSpeed() {
		return this.chargeSpeed;
	}

	public static double getCharge(ItemStack itemStack) {
		CompoundTag tag = itemStack.getTag();
		if(tag != null) {
			return tag.getDouble("Charge");
		}
		return 0;
	}

	public boolean isBarVisible(ItemStack itemStack) {
		return this.maxCharge > 0;
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

	public boolean isCreative() {
		return this.maxCharge == 0;
	}

	public void appendHoverText(ItemStack itemStack, Level world, List<Component> list, TooltipFlag flag) {
		if(this.maxCharge > 0) {
			list.add(Component.translatable("ib.tooltip.wand", String.valueOf(getCharge(itemStack)), String.valueOf(this.getMaxCharge())));
		}
	}
}