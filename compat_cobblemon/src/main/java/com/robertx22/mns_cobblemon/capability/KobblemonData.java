package com.robertx22.mns_cobblemon.capability;

import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mns_cobblemon.core.KobblemonGearSlots;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.items.ItemStackHandler;

public class KobblemonData implements INBTSerializable<CompoundTag> {

    private final ItemStackHandler inventory;
    private Entity entity;

    // Valid for 4 slots:
    // 0: Held Item -> POKEMON_HELD
    // 1: Battle Item -> POKEMON_BATTLE
    // 2: Training Item -> POKEMON_TRAINING
    // 3: Mega Stone -> POKEMON_MEGA
    public static final int SIZE = 4;

    public KobblemonData() {
        this.inventory = new ItemStackHandler(SIZE) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                syncToEntityData(slot);
            }
        };
    }

    public KobblemonData(Entity entity) {
        this();
        this.entity = entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    private void syncToEntityData(int slot) {
        if (entity == null || !(entity instanceof LivingEntity living)) {
            return;
        }
        EntityData data = Load.Unit(living);
        if (data == null) {
            return;
        }

        String slotId = null;
        switch (slot) {
            case 0:
                slotId = KobblemonGearSlots.POKEMON_HELD;
                break;
            case 1:
                slotId = KobblemonGearSlots.POKEMON_BATTLE;
                break;
            case 2:
                slotId = KobblemonGearSlots.POKEMON_TRAINING;
                break;
            case 3:
                slotId = KobblemonGearSlots.POKEMON_MEGA;
                break;
        }

        if (slotId != null) {
            // Update EntityData gears (assuming EntityGears has setItem or similar, or
            // access
            // map directly)
            // EntityGears stores items in a map.
            // data.getCurrentGears().setGear(slotId, inventory.getStackInSlot(slot));
            // Checking EntityGears API. It usually has setStack or similar.
            // Assuming setGear(String slot, ItemStack stack) exists.
            data.getCurrentGears().setGear(slotId, inventory.getStackInSlot(slot));
            data.setEquipsChanged();
            data.recalcStats_DONT_CALL(); // Trigger recalc? Or dirty sync.
            // setEquipsChanged should be enough for next tick recalc.
        }
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

    public void saveToPokemon(com.cobblemon.mod.common.pokemon.Pokemon pokemon) {
        CompoundTag data = pokemon.getPersistentData();
        data.put("mns_kobblemon_data", serializeNBT(null));
    }

    public void loadFromPokemon(com.cobblemon.mod.common.pokemon.Pokemon pokemon) {
        CompoundTag data = pokemon.getPersistentData();
        if (data.contains("mns_kobblemon_data")) {
            deserializeNBT(null, data.getCompound("mns_kobblemon_data"));
        }
    }
}
