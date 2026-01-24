package com.robertx22.mns_cobblemon.capability;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.items.ItemStackHandler;

public class KobblemonData implements INBTSerializable<CompoundTag> {

    private final ItemStackHandler inventory;

    // Valid for 4 slots:
    // 0: Held Item
    // 1: Battle Item
    // 2: Training Item
    // 3: Mega Stone
    public static final int SIZE = 4;

    public KobblemonData() {
        this.inventory = new ItemStackHandler(SIZE) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                // Future: Trigger stat update/sync
            }
        };
    }

    public KobblemonData(Entity entity) {
        this();
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.put("Inventory", inventory.serializeNBT(provider));
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        if (nbt.contains("Inventory")) {
            inventory.deserializeNBT(provider, nbt.getCompound("Inventory"));
        }
    }
}
