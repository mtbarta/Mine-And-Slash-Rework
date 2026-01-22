package com.robertx22.mns_cobblemon.events;

import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.Cobblemon;
import com.robertx22.mns_cobblemon.abilities.AbilityRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * Event handler that applies Pokemon ability effects to players
 */
@EventBusSubscriber
public class AbilityEventHandler {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity().level().isClientSide) {
            return;
        }

        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        // Only process every 20 ticks (1 second) for performance
        if (player.level().getGameTime() % 20 != 0) {
            return;
        }

        try {
            // Get the player's Pokemon party
            PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty(player);
            if (party == null) {
                return;
            }

            // Apply ability effects from each Pokemon in the party
            // Using iterator to avoid Kotlin collection interop issues
            for (Pokemon pokemon : party) {
                if (pokemon != null && !pokemon.isFainted()) {
                    AbilityRegistry.applyAbilityEffects(player, pokemon);
                }
            }
        } catch (Exception e) {
            // Silently ignore errors to prevent crashes during development
        }
    }
}
