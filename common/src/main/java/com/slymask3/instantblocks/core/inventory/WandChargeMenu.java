package com.slymask3.instantblocks.core.inventory;

import com.slymask3.instantblocks.core.block.entity.WandChargeBlockEntity;
import com.slymask3.instantblocks.core.registry.CoreMenus;
import com.slymask3.instantblocks.core.util.Helper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WandChargeMenu extends AbstractContainerMenu {
    //private final ContainerData data;
    protected final Level level;
    private final WandChargeBlockEntity blockEntity;

    public WandChargeMenu(int windowId, Inventory inventory, Level world, BlockPos pos) {
        super(CoreMenus.WAND_CHARGE, windowId);
        //checkContainerSize(container, 2);
        //checkContainerDataCount(containerData, 2);
        //this.data = containerData;
        this.blockEntity = (WandChargeBlockEntity)world.getBlockEntity(pos);
        this.level = inventory.player.level;

        this.addSlot(new Slot(blockEntity, 0, 53, 20) {
            public boolean mayPlace(ItemStack itemStack) {
                return Helper.isWandFuel(itemStack);
            }
        });

        this.addSlot(new Slot(blockEntity, 1, 107, 20) {
            public boolean mayPlace(ItemStack itemStack) {
                return Helper.isWand(itemStack);
            }
            public int getMaxStackSize(ItemStack itemStack) {
                return 1;
            }
        });

        for(int y = 0; y < 3; ++y) {
            for(int x = 0; x < 9; ++x) {
                this.addSlot(new Slot(inventory, x + y * 9 + 9, 8 + x * 18, y * 18 + 51));
            }
        }

        for(int x = 0; x < 9; ++x) {
            this.addSlot(new Slot(inventory, x, 8 + x * 18, 109));
        }

        addDataSlot(new DataSlot() {
            public int get() {
                return blockEntity.chargeProgress & 0xffff;
            }
            public void set(int value) {
                int add = blockEntity.chargeProgress & 0xffff0000;
                blockEntity.chargeProgress = add + (value & 0xffff);
            }
        });
    }

    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if(slot != null && slot.hasItem()) {
            ItemStack original = slot.getItem();
            itemStack = original.copy();
            if(index == 0 || index == 1) {
                if(!this.moveItemStackTo(original, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if(Helper.isWandFuel(original)) {
                if(!this.moveItemStackTo(original, 0, 1, true)) {
                    return ItemStack.EMPTY;
                }
            } else if(Helper.isWand(original)) {
                if(!this.moveItemStackTo(original, 1, 2, true)) {
                    return ItemStack.EMPTY;
                }
            }
            if(original.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if(original.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, original);
        }
        return itemStack;
    }

    public boolean stillValid(Player player) {
        return this.blockEntity.stillValid(player);
    }

    public int getProgress() {
        int cur = this.blockEntity.chargeProgress;
        int max = 100;
        return cur != 0 ? cur * 24 / max : 0;
    }
}