package com.robertx22.mns_cobblemon.abilities.impl;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.robertx22.mns_cobblemon.abilities.PokemonAbility;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

/**
 * Speed Boost: Grants the player increased movement speed
 * In Pokemon games, this raises Speed at the end of each turn
 */
public class SpeedBoostAbility implements PokemonAbility {

    @Override
    public String getCobblemonAbilityId() {
        return "speedboost";
    }

    @Override
    public String getDisplayName() {
        return "Speed Boost";
    }

    @Override
    public String getDescription() {
        return "Your Pokemon's speed boost ability increases your movement speed.";
    }

    @Override
    public void onTick(net.minecraft.world.entity.LivingEntity entity, Pokemon pokemon) {
        // Apply speed effect - level scales with Pokemon level
        int amplifier = Math.min(pokemon.getLevel() / 25, 2); // 0, 1, or 2
        if (!entity.hasEffect(MobEffects.MOVEMENT_SPEED) ||
                entity.getEffect(MobEffects.MOVEMENT_SPEED).getDuration() < 60) {
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, amplifier, true, false));
        }
    }
}
