package com.robertx22.mine_and_slash.mixin_ducks;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public interface AbstractContainerScreenAccessor {
    Slot getClickedSlot();

    ItemStack getDraggingItem();

    boolean isSplittingStack();
}
