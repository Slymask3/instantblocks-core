package com.slymask3.instantblocks.core.inventory;

import com.slymask3.instantblocks.core.registry.CoreMenus;
import com.slymask3.instantblocks.core.util.Helper;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WandChargeMenu extends AbstractContainerMenu {
    private final Container container;
    private final ContainerData data;
    protected final Level level;

    public WandChargeMenu(int windowId, Inventory inventory) {
        this(windowId, inventory, new SimpleContainer(2), new SimpleContainerData(2));
    }

    public WandChargeMenu(int windowId, Inventory inventory, Container container) {
        this(windowId, inventory, container, new SimpleContainerData(2));
    }

    public WandChargeMenu(int windowId, Inventory inventory, Container container, ContainerData containerData) {
        super(CoreMenus.WAND_CHARGE, windowId);
        checkContainerSize(container, 2);
        checkContainerDataCount(containerData, 2);
        this.container = container;
        this.data = containerData;
        this.level = inventory.player.level;

        this.addSlot(new WandFuelSlot(container, 0, 53, 20));
        this.addSlot(new WandSlot(container, 1, 107, 20));

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, i * 18 + 51));
            }
        }

        for(int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 109));
        }

        this.addDataSlots(containerData);
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
        return this.container.stillValid(player);
    }
}