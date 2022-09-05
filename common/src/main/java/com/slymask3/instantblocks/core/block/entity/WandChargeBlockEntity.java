package com.slymask3.instantblocks.core.block.entity;

import com.slymask3.instantblocks.core.Core;
import com.slymask3.instantblocks.core.inventory.WandChargeContainer;
import com.slymask3.instantblocks.core.item.InstantWandItem;
import com.slymask3.instantblocks.core.registry.CoreTiles;
import com.slymask3.instantblocks.core.util.WandHelper;
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
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class WandChargeBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {
	protected NonNullList<ItemStack> items;
	public int chargeProgress;
	public int chargeTotalTime;
	public double chargeAmount;

	public WandChargeBlockEntity(BlockPos pos, BlockState state) {
		super(CoreTiles.WAND_CHARGE, pos, state);
		this.items = NonNullList.withSize(2, ItemStack.EMPTY);
		this.chargeProgress = 0;
		this.chargeTotalTime = 0;
		this.chargeAmount = 0;
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
		this.chargeAmount = tag.getDouble("ChargeAmount");
		this.chargeTotalTime = tag.getShort("ChargeTotalTime");
	}

	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		tag.putShort("ChargeProgress", (short)this.chargeProgress);
		tag.putDouble("ChargeAmount", this.chargeAmount);
		tag.putShort("ChargeTotalTime", (short)this.chargeTotalTime);
		ContainerHelper.saveAllItems(tag, this.items);
	}

	protected void markUpdated() {
		this.setChanged();
		this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
	}

	protected Component getDefaultName() {
		return Component.translatable("ib.gui.charge.title");
	}

	protected AbstractContainerMenu createMenu(int windowId, Inventory inventory) {
		return new WandChargeContainer(windowId, inventory, getLevel(), getBlockPos());
	}

	public int[] getSlotsForFace(Direction direction) {
		return new int[]{0,1};
	}

	public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, Direction direction) {
		return this.canPlaceItem(index, itemStack);
	}

	public boolean canTakeItemThroughFace(int index, ItemStack itemStack, Direction direction) {
		return WandHelper.isWandFullyCharged(itemStack);
	}

	public int getContainerSize() {
		return this.items.size();
	}

	public boolean isEmpty() {
		for(ItemStack itemStack : this.items) {
			if (!itemStack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public ItemStack getItem(int index) {
		return this.items.get(index);
	}

	public ItemStack removeItem(int index, int amount) {
		return ContainerHelper.removeItem(this.items, index, amount);
	}

	public ItemStack removeItemNoUpdate(int index) {
		return ContainerHelper.takeItem(this.items, index);
	}

	public boolean stillValid(Player player) {
		if(this.level.getBlockEntity(this.worldPosition) != this) {
			return false;
		} else {
			return player.distanceToSqr((double)this.worldPosition.getX() + 0.5, (double)this.worldPosition.getY() + 0.5, (double)this.worldPosition.getZ() + 0.5) <= 64.0;
		}
	}

	public void clearContent() {
		this.items.clear();
	}

	public void setItem(int index, ItemStack itemStack) {
		this.items.set(index, itemStack);
	}

	public boolean canPlaceItem(int index, ItemStack itemStack) {
		return index == 0 && WandHelper.isWandFuel(itemStack) || index == 1 && WandHelper.isWand(itemStack);
	}

	public static void serverTick(Level world, BlockPos pos, BlockState state, WandChargeBlockEntity entity) {
		boolean isChanged = false;

		ItemStack fuel = entity.items.get(0);
		ItemStack wand = entity.items.get(1);

		if(!wand.equals(ItemStack.EMPTY) && wand.getItem() instanceof InstantWandItem wandItem && WandHelper.getWandCharge(wand) < wandItem.getMaxCharge()) {
			entity.chargeTotalTime = wandItem.getChargeSpeed();

			//take fuel
			if(!fuel.equals(ItemStack.EMPTY) && entity.chargeProgress == 0) {
				entity.chargeAmount = Core.FUEL.get(fuel.getItem());
				entity.chargeProgress = 1;
				if(fuel.getCount() == 1) {
					entity.items.set(0,ItemStack.EMPTY);
				} else {
					fuel.shrink(1);
				}
			}

			//tick
			if(entity.chargeProgress > 0) {
				++entity.chargeProgress;
				if(entity.chargeProgress >= entity.chargeTotalTime) {
					entity.chargeProgress = 0;
					WandHelper.addWandCharge(wand,entity.chargeAmount);
					isChanged = true;
				}
			}
		}

		if(isChanged) {
			entity.markUpdated();
		}
	}
}