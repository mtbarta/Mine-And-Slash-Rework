package com.robertx22.mns_cobblemon.abilities.impl;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.robertx22.mns_cobblemon.abilities.PokemonAbility;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

/**
 * Intimidate: Reduces damage from nearby enemies
 * In Pokemon games, this lowers the opponent's Attack stat
 */
public class IntimidateAbility implements PokemonAbility {

    @Override
    public String getCobblemonAbilityId() {
        return "intimidate";
    }

    @Override
    public String getDisplayName() {
        return "Intimidate";
    }

    @Override
    public String getDescription() {
        return "Your Pokemon's intimidating presence grants you resistance to damage.";
    }

    @Override
    public void onTick(Player player, Pokemon pokemon) {
        // Apply resistance effect
        if (!player.hasEffect(MobEffects.DAMAGE_RESISTANCE) ||
                player.getEffect(MobEffects.DAMAGE_RESISTANCE).getDuration() < 60) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 0, true, false));
        }
    }
}
