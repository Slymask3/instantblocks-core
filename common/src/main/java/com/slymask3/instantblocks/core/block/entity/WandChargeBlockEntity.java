package com.slymask3.instantblocks.core.block.entity;

import com.slymask3.instantblocks.core.inventory.WandChargeMenu;
import com.slymask3.instantblocks.core.registry.CoreTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Iterator;

public class WandChargeBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, StackedContentsCompatible {
	protected NonNullList<ItemStack> items;

	public WandChargeBlockEntity(BlockPos pos, BlockState state) {
		super(CoreTiles.WAND_CHARGE, pos, state);
		this.items = NonNullList.withSize(2, ItemStack.EMPTY);
	}

	public void load(CompoundTag tag) {
		super.load(tag);
		this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(tag, this.items);
//		this.litTime = tag.getShort("BurnTime");
//		this.cookingProgress = tag.getShort("CookTime");
//		this.cookingTotalTime = tag.getShort("CookTimeTotal");
//		this.litDuration = this.getBurnDuration((ItemStack)this.items.get(1));
	}

	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
//		tag.putShort("BurnTime", (short)this.litTime);
//		tag.putShort("CookTime", (short)this.cookingProgress);
//		tag.putShort("CookTimeTotal", (short)this.cookingTotalTime);
		ContainerHelper.saveAllItems(tag, this.items);
	}

	protected Component getDefaultName() {
		return Component.translatable("container.furnace");
	}

	protected AbstractContainerMenu createMenu(int $$0, Inventory $$1) {
		return new WandChargeMenu($$0, $$1, this);
	}

	@Override
	public int[] getSlotsForFace(Direction direction) {
		return new int[0];
	}

	public boolean canPlaceItemThroughFace(int $$0, ItemStack $$1, @Nullable Direction $$2) {
		return this.canPlaceItem($$0, $$1);
	}

	public boolean canTakeItemThroughFace(int $$0, ItemStack $$1, Direction $$2) {
		if ($$2 == Direction.DOWN && $$0 == 1) {
			return $$1.is(Items.WATER_BUCKET) || $$1.is(Items.BUCKET);
		} else {
			return true;
		}
	}

	public int getContainerSize() {
		return this.items.size();
	}

	public boolean isEmpty() {
		return false;
	}

	public ItemStack getItem(int $$0) {
		return (ItemStack)this.items.get($$0);
	}

	public ItemStack removeItem(int $$0, int $$1) {
		return ContainerHelper.removeItem(this.items, $$0, $$1);
	}

	public ItemStack removeItemNoUpdate(int $$0) {
		return ContainerHelper.takeItem(this.items, $$0);
	}

	public void setItem(int $$0, ItemStack $$1) {
		ItemStack $$2 = (ItemStack)this.items.get($$0);
		boolean $$3 = !$$1.isEmpty() && $$1.sameItem($$2) && ItemStack.tagMatches($$1, $$2);
		this.items.set($$0, $$1);
		if ($$1.getCount() > this.getMaxStackSize()) {
			$$1.setCount(this.getMaxStackSize());
		}
	}

	public boolean stillValid(Player $$0) {
		if (this.level.getBlockEntity(this.worldPosition) != this) {
			return false;
		} else {
			return $$0.distanceToSqr((double)this.worldPosition.getX() + 0.5, (double)this.worldPosition.getY() + 0.5, (double)this.worldPosition.getZ() + 0.5) <= 64.0;
		}
	}

	public void clearContent() {
		this.items.clear();
	}

	public void fillStackedContents(StackedContents $$0) {
		Iterator var2 = this.items.iterator();

		while(var2.hasNext()) {
			ItemStack $$1 = (ItemStack)var2.next();
			$$0.accountStack($$1);
		}

	}
}