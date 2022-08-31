package com.slymask3.instantblocks.core.block.entity;

import com.slymask3.instantblocks.core.inventory.WandChargeMenu;
import com.slymask3.instantblocks.core.registry.CoreTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Iterator;

public class WandChargeBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, StackedContentsCompatible {
	protected NonNullList<ItemStack> items;
	public int chargeProgress;
	public int chargeTotalTime;
	public int chargeAmount;

	public WandChargeBlockEntity(BlockPos pos, BlockState state) {
		super(CoreTiles.WAND_CHARGE, pos, state);
		this.items = NonNullList.withSize(2, ItemStack.EMPTY);
		this.chargeProgress = 1;
		this.chargeTotalTime = 100;
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag() {
		return this.saveWithoutMetadata();
	}

	public void load(CompoundTag tag) {
		super.load(tag);
		this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(tag, this.items);
		this.chargeProgress = tag.getShort("ChargeProgress");
		this.chargeAmount = tag.getShort("ChargeAmount");
	}

	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		tag.putShort("ChargeProgress", (short)this.chargeProgress);
		tag.putShort("ChargeAmount", (short)this.chargeAmount);
		ContainerHelper.saveAllItems(tag, this.items);
	}

	protected void markUpdated() {
		this.setChanged();
		this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
	}

	protected Component getDefaultName() {
		return Component.translatable("ib.gui.charge.title");
	}

	protected AbstractContainerMenu createMenu(int $$0, Inventory $$1) {
		return new WandChargeMenu($$0, $$1, getLevel(), getBlockPos());
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
		return this.items.get($$0);
	}

	public ItemStack removeItem(int $$0, int $$1) {
		return ContainerHelper.removeItem(this.items, $$0, $$1);
	}

	public ItemStack removeItemNoUpdate(int $$0) {
		return ContainerHelper.takeItem(this.items, $$0);
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

	public void setItem(int index, ItemStack itemStack) {
		ItemStack original = this.items.get(index);
		boolean $$3 = !itemStack.isEmpty() && itemStack.sameItem(original) && ItemStack.tagMatches(itemStack, original);
		this.items.set(index, itemStack);
		if(itemStack.getCount() > this.getMaxStackSize()) {
			itemStack.setCount(this.getMaxStackSize());
		}
		if(index == 0 && !$$3) {
			this.chargeProgress = 1;
			this.setChanged();
		}
	}

	public static void serverTick(Level world, BlockPos pos, BlockState state, WandChargeBlockEntity entity) {
		boolean isChanged = false;

		ItemStack fuel = entity.items.get(0);
		ItemStack wand = entity.items.get(1);

//		Core.LOG.info("fuel: {}", fuel);
//		Core.LOG.info("wand: {}", wand);
//		Core.LOG.info("chargeProgress: {}", entity.chargeProgress);

		if(!wand.equals(ItemStack.EMPTY) && entity.chargeProgress >= 0) {
			++entity.chargeProgress;
			if(entity.chargeProgress == entity.chargeTotalTime) {
				entity.chargeProgress = 1;
				isChanged = true;
			}
//			isChanged = true;
		}

		if(isChanged) {
			entity.markUpdated();
		}
	}
}