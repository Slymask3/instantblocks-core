package com.slymask3.instantblocks.core.inventory;

import com.slymask3.instantblocks.core.util.Helper;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class WandSlot extends Slot {
    public WandSlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    public boolean mayPlace(ItemStack itemStack) {
        return Helper.isWand(itemStack);
    }

    public int getMaxStackSize(ItemStack $$0) {
        return 1;
    }
}