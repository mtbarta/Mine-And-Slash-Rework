package com.robertx22.mns_cobblemon.abilities.impl;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.robertx22.mns_cobblemon.abilities.PokemonAbility;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

/**
 * Illuminate: Provides night vision to the player
 * In Pokemon games, this increases the chance of encountering wild Pokemon
 */
public class IlluminateAbility implements PokemonAbility {

    @Override
    public String getCobblemonAbilityId() {
        return "illuminate";
    }

    @Override
    public String getDisplayName() {
        return "Illuminate";
    }

    @Override
    public String getDescription() {
        return "Your Pokemon illuminates the area, granting you night vision.";
    }

    @Override
    public void onTick(Player player, Pokemon pokemon) {
        // Apply night vision effect - refresh every second (20 ticks buffer)
        if (!player.hasEffect(MobEffects.NIGHT_VISION) ||
                player.getEffect(MobEffects.NIGHT_VISION).getDuration() < 60) {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 0, true, false));
        }
    }
}
