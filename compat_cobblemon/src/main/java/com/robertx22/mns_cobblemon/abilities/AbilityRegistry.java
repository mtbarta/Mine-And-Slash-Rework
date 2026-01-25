package com.robertx22.mns_cobblemon.abilities;

import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Registry for all Pokemon abilities that provide player effects.
 * Maps Cobblemon ability IDs to our ability implementations.
 */
public class AbilityRegistry {

    private static final Map<String, PokemonAbility> ABILITIES = new HashMap<>();
    private static final List<PokemonAbility> SORTED_ABILITIES = new ArrayList<>();
    private static boolean needsSort = true;

    /**
     * Register a new ability
     */
    public static void register(PokemonAbility ability) {
        ABILITIES.put(ability.getCobblemonAbilityId().toLowerCase(), ability);
        needsSort = true;
    }

    /**
     * Get ability by Cobblemon ability ID
     */
    public static PokemonAbility getAbility(String cobblemonAbilityId) {
        return ABILITIES.get(cobblemonAbilityId.toLowerCase());
    }

    /**
     * Check if we have an implementation for this ability
     */
    public static boolean hasAbility(String cobblemonAbilityId) {
        return ABILITIES.containsKey(cobblemonAbilityId.toLowerCase());
    }

    /**
     * Get all registered abilities, sorted by priority
     */
    public static List<PokemonAbility> getAllAbilities() {
        if (needsSort) {
            SORTED_ABILITIES.clear();
            SORTED_ABILITIES.addAll(ABILITIES.values());
            SORTED_ABILITIES.sort((a, b) -> Integer.compare(b.getPriority(), a.getPriority()));
            needsSort = false;
        }
        return SORTED_ABILITIES;
    }

    /**
     * Apply ability effects for a Pokemon to a player
     */
    public static void applyAbilityEffects(net.minecraft.world.entity.LivingEntity entity, Pokemon pokemon) {
        if (pokemon == null || pokemon.getAbility() == null) {
            return;
        }

        String abilityName = pokemon.getAbility().getName();
        PokemonAbility ability = getAbility(abilityName);

        if (ability != null && ability.shouldApply(entity, pokemon)) {
            ability.onTick(entity, pokemon);
        }
    }
}
