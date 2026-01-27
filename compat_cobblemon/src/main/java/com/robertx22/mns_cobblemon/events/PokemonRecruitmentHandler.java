package com.robertx22.mns_cobblemon.events;

import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.robertx22.mine_and_slash.aoe_data.database.spells.schools.SummonSpells;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.network.chat.Component;

public class PokemonRecruitmentHandler {

    // Persistent NBT tag key for minion status
    public static final String IS_MINION_TAG = "mns_is_minion";

    public static void register() {
        // Trying generic subscription if type is issue, but need concrete type for
        // lambda argument
        // Assuming package
        // com.cobblemon.mod.common.api.events.pokemon.PokemonSentPostEvent exists,
        // but maybe implicit import failed.
        // Let's rely on finding the class.
        // If it is NOT in that package, I will try to use the inferred type from the
        // event object
        // but Java requires explicit type in method arg unless I use var? No, var
        // doesn't work in method args.

        CobblemonEvents.POKEMON_SENT_POST.subscribe(com.cobblemon.mod.common.api.Priority.NORMAL,
                PokemonRecruitmentHandler::onPokemonSent);
    }

    // Using Post event to access the entity
    private static void onPokemonSent(com.cobblemon.mod.common.api.events.pokemon.PokemonSentEvent.Post event) {
        PokemonEntity pokemonEntity = event.getPokemonEntity();
        Pokemon pokemon = event.getPokemon();

        // Check if this Pokemon is marked as a minion in its persistent data
        if (pokemon.getPersistentData().contains(IS_MINION_TAG)
                && pokemon.getPersistentData().getBoolean(IS_MINION_TAG)) {
            applyMinionStatus(pokemonEntity);
        }
    }

    public static void applyMinionStatus(PokemonEntity pokemonEntity) {
        var data = Load.Unit(pokemonEntity);
        if (data == null)
            return;

        Pokemon pokemon = pokemonEntity.getPokemon();

        var spell = ExileDB.Spells().get(SummonSpells.SUMMON_POKEMON);
        if (spell != null) {
            // Infinite duration (-1), count towards limit = false (for now), useOwnStats =
            // true
            data.summonedPetData.setup(spell, -1, 20, false);
            data.summonedPetData.useOwnStats = true;

            // Do NOT scale to owner level from M&S side.
            // Level is roughly synced to the Pokemon's actual level.
            int mnsLevel = com.robertx22.mns_cobblemon.events.CobblemonSpawning
                    .mapPokemonLevelToMnS(pokemon.getLevel());
            data.setLevel(mnsLevel);

            data.mobStatsAreSet();
            data.setAllDirtyOnLoginEtc();
        }
    }
}
