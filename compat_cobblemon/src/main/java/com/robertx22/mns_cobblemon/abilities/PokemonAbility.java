package com.robertx22.mns_cobblemon.abilities;

import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.world.entity.player.Player;

/**
 * Base interface for Pokemon abilities that provide passive effects to the
 * player.
 * Abilities are triggered when a Pokemon with the ability is in the player's
 * party
 * or actively equipped.
 */
public interface PokemonAbility {

    /**
     * Get the Cobblemon ability ID this maps to
     */
    String getCobblemonAbilityId();

    /**
     * Get the display name for this ability
     */
    String getDisplayName();

    /**
     * Get the description of what this ability does for the player
     */
    String getDescription();

    /**
     * Called every tick while the player has a Pokemon with this ability
     * 
     * @param player  The player receiving the ability effect
     * @param pokemon The Pokemon that has this ability
     */
    void onTick(Player player, Pokemon pokemon);

    /**
     * Whether this ability should affect the player
     * Can be used to add conditions, like only work in certain dimensions
     */
    default boolean shouldApply(Player player, Pokemon pokemon) {
        return true;
    }

    /**
     * The priority of this ability (higher = applied first)
     */
    default int getPriority() {
        return 0;
    }
}
