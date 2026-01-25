package com.robertx22.mns_cobblemon.abilities.impl;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.robertx22.mns_cobblemon.abilities.PokemonAbility;
import net.minecraft.world.entity.player.Player;

import java.util.Random;

/**
 * Pickup: Chance to find extra items when killing mobs
 * In Pokemon games, this gives chance to pick up items after battle
 * 
 * Note: This is a placeholder that marks the player for the loot system.
 * The actual loot bonus should be implemented in a LootModifier.
 */
public class PickupAbility implements PokemonAbility {

    private static final Random RANDOM = new Random();

    @Override
    public String getCobblemonAbilityId() {
        return "pickup";
    }

    @Override
    public String getDisplayName() {
        return "Pickup";
    }

    @Override
    public String getDescription() {
        return "Your Pokemon may pick up extra items from defeated enemies.";
    }

    @Override
    public void onTick(net.minecraft.world.entity.LivingEntity entity, Pokemon pokemon) {
        if (!(entity instanceof Player player)) {
            return;
        }
        // Set a persistent data tag on the player indicating they have pickup active
        // This will be checked by a LootModifier to add bonus drops
        player.getPersistentData().putBoolean("mns_cobblemon_pickup", true);
        player.getPersistentData().putLong("mns_cobblemon_pickup_time", player.level().getGameTime());
    }

    /**
     * Check if a player has pickup ability active
     */
    public static boolean hasPickup(Player player) {
        if (!player.getPersistentData().contains("mns_cobblemon_pickup")) {
            return false;
        }
        // Check if it was set within the last 40 ticks (2 seconds)
        long setTime = player.getPersistentData().getLong("mns_cobblemon_pickup_time");
        return player.level().getGameTime() - setTime < 40;
    }

    /**
     * Get the bonus loot chance (0.0 to 1.0)
     */
    public static float getBonusLootChance(Player player, Pokemon pokemon) {
        if (!hasPickup(player)) {
            return 0;
        }
        // 10% base chance + 0.5% per Pokemon level
        return 0.10f + (pokemon.getLevel() * 0.005f);
    }
}
