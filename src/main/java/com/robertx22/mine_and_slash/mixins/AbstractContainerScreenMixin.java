package com.robertx22.mine_and_slash.mixins;

import com.robertx22.mine_and_slash.mixin_ducks.AbstractContainerScreenAccessor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractContainerScreen.class)
public class AbstractContainerScreenMixin implements AbstractContainerScreenAccessor {

    @Shadow
    private Slot clickedSlot;

    @Shadow
    private ItemStack draggingItem;

    @Shadow
    private boolean isSplittingStack;

    @Override
    public Slot getClickedSlot() {
        return this.clickedSlot;
    }

    @Override
    public ItemStack getDraggingItem() {
        return this.draggingItem;
    }

    @Override
    public boolean isSplittingStack() {
        return this.isSplittingStack;
    }
}
