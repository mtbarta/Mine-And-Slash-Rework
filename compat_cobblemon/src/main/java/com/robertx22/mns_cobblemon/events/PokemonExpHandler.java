package com.robertx22.mns_cobblemon.events;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.stat_calculation.MobStatUtils;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber
public class PokemonExpHandler {

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getSource() == null || event.getSource().getEntity() == null)
            return;

        if (event.getSource().getEntity() instanceof PokemonEntity pokemonEntity) {
            Pokemon pokemon = pokemonEntity.getPokemon();
            LivingEntity target = event.getEntity();

            // Calculate XP based on target level and rarity
            int targetLevel = 1;
            String rarity = "common";

            var data = Load.Unit(target);
            if (data != null) {
                targetLevel = data.getLevel();
                rarity = data.getRarity();
            }

            // Simple XP formula: Level * Multiplier
            // Rarity Bonus: Common=1x, Rare=2x, etc. (Simplified)
            double rarityMult = 1.0;
            switch (rarity) {
                case "uncommon":
                    rarityMult = 1.5;
                    break;
                case "rare":
                    rarityMult = 2.5;
                    break;
                case "unique":
                    rarityMult = 4.0;
                    break;
                case "epic":
                    rarityMult = 6.0;
                    break;
                case "mythic":
                    rarityMult = 10.0;
                    break;
                default:
                    rarityMult = 1.0;
            }

            // Base XP
            int xpGain = (int) (targetLevel * 2 * rarityMult);

            // Ensure minimum 1 XP
            if (xpGain < 1)
                xpGain = 1;

            // Award XP to Pokemon
            pokemon.addExperience(
                    new com.cobblemon.mod.common.api.pokemon.experience.SidemodExperienceSource("mine_and_slash"),
                    xpGain);

            com.robertx22.mns_cobblemon.core.stats.PokemonStatSync.syncLevel(pokemon);
        }
    }
}
