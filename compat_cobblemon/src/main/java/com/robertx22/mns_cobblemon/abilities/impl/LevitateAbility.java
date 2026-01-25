package com.robertx22.mns_cobblemon.abilities.impl;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.robertx22.mns_cobblemon.abilities.PokemonAbility;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

/**
 * Levitate: Provides slow falling to the player (simulates levitation/no fall
 * damage)
 * In Pokemon games, this grants immunity to Ground-type moves
 */
public class LevitateAbility implements PokemonAbility {

    @Override
    public String getCobblemonAbilityId() {
        return "levitate";
    }

    @Override
    public String getDisplayName() {
        return "Levitate";
    }

    @Override
    public String getDescription() {
        return "Your Pokemon's levitation powers grant you slow falling.";
    }

    @Override
    public void onTick(net.minecraft.world.entity.LivingEntity entity, Pokemon pokemon) {
        // Apply slow falling effect
        if (!entity.hasEffect(MobEffects.SLOW_FALLING) ||
                entity.getEffect(MobEffects.SLOW_FALLING).getDuration() < 60) {
            entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 400, 0, true, false));
        }
    }
}
