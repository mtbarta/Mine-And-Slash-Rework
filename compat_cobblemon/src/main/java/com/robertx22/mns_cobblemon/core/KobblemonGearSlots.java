package com.robertx22.mns_cobblemon.core;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.mine_and_slash.database.data.gear_slots.GearSlot;
import com.robertx22.mine_and_slash.database.data.gear_types.bases.SlotFamily;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;

public class KobblemonGearSlots implements ExileRegistryInit {

    // Slot IDs for easy reference
    public static final String POKEMON_HELD = "pokemon_held";
    public static final String POKEMON_BATTLE = "pokemon_battle";
    public static final String POKEMON_TRAINING = "pokemon_training";
    public static final String POKEMON_MEGA = "pokemon_mega";

    @Override
    public void registerAll() {
        // Held Item slot - standard battle items like Choice Band, Leftovers
        new GearSlot(POKEMON_HELD, "Held Item", SlotFamily.Jewelry,
                new GearSlot.WeaponData(0, 0, 0), 14, 1000)
                .addToSerializables(MMORPG.SERIAZABLE_REGISTRATION_INFO);

        // Battle Item slot - combat-focused items like Scope Lens, Quick Claw
        new GearSlot(POKEMON_BATTLE, "Battle Item", SlotFamily.Jewelry,
                new GearSlot.WeaponData(0, 0, 0), 15, 800)
                .addToSerializables(MMORPG.SERIAZABLE_REGISTRATION_INFO);

        // Training Item slot - EV boosting items like Power Weight, Lucky Egg
        new GearSlot(POKEMON_TRAINING, "Training Item", SlotFamily.Jewelry,
                new GearSlot.WeaponData(0, 0, 0), 16, 600)
                .addToSerializables(MMORPG.SERIAZABLE_REGISTRATION_INFO);

        // Mega/Evolution slot - transformation items like Mega Stones
        new GearSlot(POKEMON_MEGA, "Mega Stone", SlotFamily.Jewelry,
                new GearSlot.WeaponData(0, 0, 0), 17, 400)
                .addToSerializables(MMORPG.SERIAZABLE_REGISTRATION_INFO);
    }
}
