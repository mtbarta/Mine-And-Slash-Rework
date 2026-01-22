package com.robertx22.mns_cobblemon.core.stats;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;

/**
 * Registers all Pokemon-specific stats with the MnS stat system
 */
public class KobblemonStats implements ExileRegistryInit {

    @Override
    public void registerAll() {
        // Register all Pokemon stats with the serialization system
        PokemonHealth.getInstance().addToSerializables(MMORPG.SERIAZABLE_REGISTRATION_INFO);
        PokemonAttack.getInstance().addToSerializables(MMORPG.SERIAZABLE_REGISTRATION_INFO);
        PokemonSpAtk.getInstance().addToSerializables(MMORPG.SERIAZABLE_REGISTRATION_INFO);
        PokemonDefense.getInstance().addToSerializables(MMORPG.SERIAZABLE_REGISTRATION_INFO);
        PokemonSpDef.getInstance().addToSerializables(MMORPG.SERIAZABLE_REGISTRATION_INFO);
        PokemonSpeed.getInstance().addToSerializables(MMORPG.SERIAZABLE_REGISTRATION_INFO);
    }
}
