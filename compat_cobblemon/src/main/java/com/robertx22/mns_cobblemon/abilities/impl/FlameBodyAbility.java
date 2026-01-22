package com.robertx22.mns_cobblemon.abilities.impl;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.robertx22.mns_cobblemon.abilities.PokemonAbility;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

/**
 * Flame Body: Provides fire resistance to the player
 * In Pokemon games, this halves egg hatching time and can burn attackers
 */
public class FlameBodyAbility implements PokemonAbility {

    @Override
    public String getCobblemonAbilityId() {
        return "flamebody";
    }

    @Override
    public String getDisplayName() {
        return "Flame Body";
    }

    @Override
    public String getDescription() {
        return "Your Pokemon's fiery body grants you fire resistance.";
    }

    @Override
    public void onTick(Player player, Pokemon pokemon) {
        // Apply fire resistance effect
        if (!player.hasEffect(MobEffects.FIRE_RESISTANCE) ||
                player.getEffect(MobEffects.FIRE_RESISTANCE).getDuration() < 60) {
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 400, 0, true, false));
        }
    }
}
