package com.robertx22.mns_cobblemon.abilities;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.mns_cobblemon.abilities.impl.*;

/**
 * Registers all Pokemon ability implementations
 */
public class KobblemonAbilities implements ExileRegistryInit {

    @Override
    public void registerAll() {
        // Utility abilities
        AbilityRegistry.register(new IlluminateAbility());
        AbilityRegistry.register(new FlameBodyAbility());
        AbilityRegistry.register(new LevitateAbility());
        AbilityRegistry.register(new PickupAbility());

        // Combat-related abilities
        AbilityRegistry.register(new SpeedBoostAbility());
        AbilityRegistry.register(new IntimidateAbility());
    }
}
