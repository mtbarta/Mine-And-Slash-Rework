package com.robertx22.mns_cobblemon.events;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.robertx22.mns_cobblemon.MnSCobblemonCompat;
import com.robertx22.mns_cobblemon.capability.KobblemonData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;

public class PokemonDataHandler {

    // Helper for manual registration
    public static void register(net.neoforged.bus.api.IEventBus bus) {
        bus.register(PokemonDataHandler.class);
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide)
            return;

        if (event.getEntity() instanceof PokemonEntity pokemonEntity) {
            Pokemon pokemon = pokemonEntity.getPokemon();
            KobblemonData data = pokemonEntity.getData(MnSCobblemonCompat.KOBBLEMON_DATA);
            if (data != null) {
                // Critical: Set the entity reference so the data object can access the
                // level/registry
                data.setEntity(pokemonEntity);
                data.loadFromPokemon(pokemon);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityLeave(EntityLeaveLevelEvent event) {
        if (event.getLevel().isClientSide)
            return;

        if (event.getEntity() instanceof PokemonEntity pokemonEntity) {
            Pokemon pokemon = pokemonEntity.getPokemon();
            KobblemonData data = pokemonEntity.getData(MnSCobblemonCompat.KOBBLEMON_DATA);
            if (data != null) {
                data.setEntity(pokemonEntity); // Ensure entity is set
                data.saveToPokemon(pokemon);
            }
        }
    }
}
