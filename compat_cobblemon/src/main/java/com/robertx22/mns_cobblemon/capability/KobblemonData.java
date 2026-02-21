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
    // 4: Weapon -> POKEMON_WEAPON
    public static final int SIZE = 5;

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
            case 4:
                slotId = KobblemonGearSlots.POKEMON_WEAPON;
                break;
        }

        if (slotId != null) {
            // Update EntityData gears
            data.getCurrentGears().setGear(slotId, inventory.getStackInSlot(slot));
            data.setEquipsChanged();
            data.recalcStats_DONT_CALL();
        }

        // Robust Persistence: Save to Pokemon NBT immediately on change
        if (entity instanceof com.cobblemon.mod.common.entity.pokemon.PokemonEntity pokemonEntity) {
            saveToPokemon(pokemonEntity.getPokemon());
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

        // Ensure inventory is the correct size, resizing if necessary (e.g. loading old
        // data)
        if (inventory.getSlots() < SIZE) {
            inventory.setSize(SIZE);
        }
    }

    public void saveToPokemon(com.cobblemon.mod.common.pokemon.Pokemon pokemon) {
        CompoundTag data = pokemon.getPersistentData();
        if (entity != null) {
            data.put("mns_kobblemon_data", serializeNBT(entity.level().registryAccess()));
        }
    }

    public void loadFromPokemon(com.cobblemon.mod.common.pokemon.Pokemon pokemon) {
        CompoundTag data = pokemon.getPersistentData();
        if (data.contains("mns_kobblemon_data") && entity != null) {
            deserializeNBT(entity.level().registryAccess(), data.getCompound("mns_kobblemon_data"));
        }
    }
}
