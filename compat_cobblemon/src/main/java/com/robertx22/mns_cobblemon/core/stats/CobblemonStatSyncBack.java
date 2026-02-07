package com.robertx22.mns_cobblemon.core.stats;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mns_cobblemon.core.stats.PokemonSpeed;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

/**
 * Syncs Mine and Slash calculated stats back to Cobblemon's Pokemon object.
 * 
 * This class applies M&S stats as vanilla attribute modifiers for Pokemon:
 * - MAX_HEALTH: For combat with Minecraft mobs
 * - MOVEMENT_SPEED: For walking/running speed
 * - rideStamina: For mounting stamina
 */
@EventBusSubscriber
public class CobblemonStatSyncBack {

    // Unique IDs for our modifiers
    private static final ResourceLocation POKEMON_MNS_HEALTH_ID = ResourceLocation.fromNamespaceAndPath("mns_cobblemon",
            "pokemon_mns_health");
    private static final ResourceLocation POKEMON_MNS_SPEED_ID = ResourceLocation.fromNamespaceAndPath("mns_cobblemon",
            "pokemon_mns_speed");

    // Track last synced values to avoid constant updates
    private static final java.util.Map<java.util.UUID, SyncedStats> lastSyncedStats = new java.util.concurrent.ConcurrentHashMap<>();

    private record SyncedStats(int health, float speed) {
    }

    /**
     * Sync M&S stats to Cobblemon on entity tick.
     */
    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof PokemonEntity pokemonEntity)) {
            return;
        }

        if (pokemonEntity.level().isClientSide()) {
            return;
        }

        // Only sync every 20 ticks (1 second) to reduce overhead
        if (pokemonEntity.tickCount % 20 != 0) {
            return;
        }

        syncStatsToCobblemon(pokemonEntity);
    }

    /**
     * Main sync method - call this after stats are calculated.
     */
    public static void syncStatsToCobblemon(PokemonEntity pokemonEntity) {
        if (pokemonEntity == null || pokemonEntity.level().isClientSide()) {
            return;
        }

        try {
            EntityData data = Load.Unit(pokemonEntity);
            if (data == null || data.getUnit() == null) {
                return;
            }

            // Get M&S calculated stats
            int mnsMaxHealth = (int) data.getUnit().healthData().getValue();

            // Get Pokemon speed stat for movement
            float mnsSpeed = data.getUnit().getCalculatedStat(PokemonSpeed.getInstance()).getValue();

            if (mnsMaxHealth <= 0) {
                return; // Stats not calculated yet
            }

            // Check if we need to update
            java.util.UUID pokemonUuid = pokemonEntity.getUUID();
            SyncedStats lastSynced = lastSyncedStats.get(pokemonUuid);

            boolean needsUpdate = lastSynced == null
                    || lastSynced.health != mnsMaxHealth
                    || Math.abs(lastSynced.speed - mnsSpeed) > 0.01f;

            if (!needsUpdate) {
                return; // Already synced these values
            }

            // Apply M&S health directly to vanilla MAX_HEALTH attribute
            applyHealthAttribute(pokemonEntity, mnsMaxHealth);

            // Apply M&S speed to vanilla MOVEMENT_SPEED attribute
            applySpeedAttribute(pokemonEntity, mnsSpeed);

            // Sync stamina based on speed stat
            syncStamina(pokemonEntity, mnsSpeed);

            // Sync to Cobblemon's internal tracking
            syncToCobblemonHealth(pokemonEntity, data);

            // Mark as synced
            lastSyncedStats.put(pokemonUuid, new SyncedStats(mnsMaxHealth, mnsSpeed));

        } catch (Exception e) {
            // Silently handle to prevent crashes
        }
    }

    /**
     * Apply M&S health as a vanilla attribute modifier.
     * This ensures the entity's getMaxHealth() returns the M&S value.
     */
    private static void applyHealthAttribute(PokemonEntity pokemonEntity, int mnsHealth) {
        AttributeInstance healthAttribute = pokemonEntity.getAttribute(Attributes.MAX_HEALTH);
        if (healthAttribute == null) {
            return;
        }

        float currentMax = pokemonEntity.getMaxHealth();

        // Remove old modifier if present
        if (healthAttribute.hasModifier(POKEMON_MNS_HEALTH_ID)) {
            healthAttribute.removeModifier(POKEMON_MNS_HEALTH_ID);
        }

        // Calculate how much health to add
        float baseHealth = (float) healthAttribute.getBaseValue();
        float healthToAdd = mnsHealth - baseHealth;

        if (healthToAdd > 0) {
            AttributeModifier modifier = new AttributeModifier(
                    POKEMON_MNS_HEALTH_ID,
                    healthToAdd,
                    AttributeModifier.Operation.ADD_VALUE);
            healthAttribute.addPermanentModifier(modifier);
        }

        // Heal if max health increased
        float newMax = pokemonEntity.getMaxHealth();
        if (newMax > currentMax) {
            pokemonEntity.heal(newMax - currentMax);
        }
    }

    /**
     * Apply M&S speed as a vanilla MOVEMENT_SPEED modifier.
     * Base Pokemon speed is 0.3. We scale based on the MnS speed stat.
     */
    private static void applySpeedAttribute(PokemonEntity pokemonEntity, float mnsSpeed) {
        AttributeInstance speedAttribute = pokemonEntity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttribute == null) {
            return;
        }

        // Remove old modifier if present
        if (speedAttribute.hasModifier(POKEMON_MNS_SPEED_ID)) {
            speedAttribute.removeModifier(POKEMON_MNS_SPEED_ID);
        }

        // Calculate speed bonus as percentage
        // Base assumption: 100 speed = normal speed, higher = faster
        // Convert to a percentage modifier (e.g., 150 speed = +50% speed)
        float speedMultiplier = (mnsSpeed / 100.0f) - 1.0f; // 100 = 0%, 150 = 50%, 50 = -50%
        speedMultiplier = Math.max(-0.5f, Math.min(speedMultiplier, 2.0f)); // Clamp between -50% and +200%

        if (Math.abs(speedMultiplier) > 0.01f) {
            AttributeModifier modifier = new AttributeModifier(
                    POKEMON_MNS_SPEED_ID,
                    speedMultiplier,
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
            speedAttribute.addPermanentModifier(modifier);
        }
    }

    /**
     * Sync stamina based on MnS speed stat.
     * Higher speed = better stamina recovery (we set initial stamina higher).
     */
    private static void syncStamina(PokemonEntity pokemonEntity, float mnsSpeed) {
        try {
            Pokemon pokemon = pokemonEntity.getPokemon();
            if (pokemon == null) {
                return;
            }

            // Only modify stamina if Pokemon is not being ridden
            if (!pokemonEntity.getPassengers().isEmpty()) {
                return;
            }

            // Higher speed = better stamina. Scale from 0.5 to 1.0 based on speed
            // 50 speed = 0.5 stamina, 150+ speed = 1.0 stamina
            float staminaValue = Math.min(1.0f, Math.max(0.5f, mnsSpeed / 150.0f));
            pokemon.setRideStamina(staminaValue);

        } catch (Exception e) {
            // Handle gracefully
        }
    }

    /**
     * Sync health to Cobblemon's Pokemon object for GUI display.
     */
    private static void syncToCobblemonHealth(PokemonEntity pokemonEntity, EntityData data) {
        try {
            Pokemon pokemon = pokemonEntity.getPokemon();
            if (pokemon == null) {
                return;
            }

            // Get current health as a percentage of the actual max
            float healthPercent = pokemonEntity.getHealth() / pokemonEntity.getMaxHealth();

            // Apply to Cobblemon's internal health tracking
            int cobblemonMaxHp = pokemon.getMaxHealth();
            int newCurrentHp = Math.max(1, (int) (cobblemonMaxHp * healthPercent));
            pokemon.setCurrentHealth(newCurrentHp);

        } catch (Exception e) {
            // Cobblemon API might differ - handle gracefully
        }
    }

    /**
     * Force a full sync - call after gear changes or level ups.
     */
    public static void forceSync(PokemonEntity pokemonEntity) {
        if (pokemonEntity != null && !pokemonEntity.level().isClientSide()) {
            java.util.UUID pokemonUuid = pokemonEntity.getUUID();
            lastSyncedStats.remove(pokemonUuid);
            syncStatsToCobblemon(pokemonEntity);
        }
    }
}
