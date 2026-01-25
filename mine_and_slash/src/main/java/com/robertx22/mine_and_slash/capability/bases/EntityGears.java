package com.robertx22.mine_and_slash.capability.bases;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;

public class EntityGears {

    private HashMap<String, ItemStack> map = new HashMap<>();

    public ItemStack get(EquipmentSlot slot) {
        return get(slot.getName());
    }

    public ItemStack get(String slot) {
        if (map.isEmpty()) {
            // Initialization if needed, though lazy init is fine usually
        }
        return map.getOrDefault(slot, ItemStack.EMPTY);
    }

    public ItemStack put(EquipmentSlot slot, ItemStack stack) {
        return put(slot.getName(), stack);
    }

    public ItemStack put(String slot, ItemStack stack) {
        return map.put(slot, stack);
    }

    public void setGear(String slot, ItemStack stack) {
        map.put(slot, stack);
    }

}
