package com.robertx22.mns_cobblemon.core.stats;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.api.pokemon.stats.Stats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.SpellChangeStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.Armor;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.DodgeRating;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.ElementalResist;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.health.Health;
import com.robertx22.mine_and_slash.event_hooks.my_events.GatherEntityStatsEvent;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.MiscStatCtx;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Synchronizes Cobblemon Pokemon stats with Mine and Slash attributes.
 * Treats the Pokemon's biological body as base gear.
 */
@EventBusSubscriber
public class PokemonStatSync {

    @SubscribeEvent
    public static void onGatherStats(GatherEntityStatsEvent event) {
        if (event.getEntity() instanceof PokemonEntity pokemonEntity) {
            Pokemon pokemon = pokemonEntity.getPokemon();
            List<ExactStatData> baseStats = getStatsForPokemon(pokemon);
            event.addStatContext(new MiscStatCtx(baseStats));
        }
    }

    public static List<ExactStatData> getStatsForPokemon(Pokemon pokemon) {
        List<ExactStatData> baseStats = new ArrayList<>();
        int level = pokemon.getLevel();
        float fLevel = (float) level;

        // "Power Ratio" approach: We calculate how many points per level the Pokemon
        // has,
        // then map that to a Level-1 base value for Mine and Slash to scale up.
        // This ensures stats are relevant at all levels and avoids arbitrary divisors.
        // Average Ratio is ~2.0 per stat.

        // HP -> Vitality/Health
        float hpRatio = pokemon.getStat(Stats.HP) / fLevel;
        baseStats.add(ExactStatData.levelScaled(hpRatio * 1.5f, Health.getInstance(), ModType.FLAT, level));

        // Physical Attack -> Strength
        float physAtkRatio = pokemon.getStat(Stats.ATTACK) / fLevel;
        baseStats.add(ExactStatData.levelScaled(physAtkRatio * 2.0f, DatapackStats.STR, ModType.FLAT, level));

        // Physical Defense -> Armor
        float physDefRatio = pokemon.getStat(Stats.DEFENCE) / fLevel;
        baseStats.add(ExactStatData.levelScaled(physDefRatio * 5.0f, Armor.getInstance(), ModType.FLAT, level));

        // Special Attack -> Intelligence
        float spAtkRatio = pokemon.getStat(Stats.SPECIAL_ATTACK) / fLevel;
        baseStats.add(ExactStatData.levelScaled(spAtkRatio * 2.0f, DatapackStats.INT, ModType.FLAT, level));

        // Special Defense -> Elemental Resistances
        float spDefRatio = pokemon.getStat(Stats.SPECIAL_DEFENCE) / fLevel;
        float eleResBase = spDefRatio * 0.5f;
        for (Elements ele : Elements.getAllSingle()) {
            if (ele != Elements.Physical) {
                baseStats.add(ExactStatData.levelScaled(eleResBase, new ElementalResist(ele), ModType.FLAT, level));
            }
        }

        // Speed -> Dodge / Cooldown Reduction
        float speedRatio = pokemon.getStat(Stats.SPEED) / fLevel;
        baseStats.add(ExactStatData.levelScaled(speedRatio * 1.0f, DodgeRating.getInstance(), ModType.FLAT, level));

        // CDR is a percent, keep it relatively flat
        baseStats.add(ExactStatData.noScaling(speedRatio * 2.5f, ModType.PERCENT,
                SpellChangeStats.COOLDOWN_REDUCTION.getId()));

        // Apply custom Poke-stats for specialized modifiers
        baseStats.add(ExactStatData.levelScaled(physAtkRatio * 10f, PokemonAttack.getInstance(), ModType.FLAT, level));
        baseStats.add(ExactStatData.levelScaled(physDefRatio * 10f, PokemonDefense.getInstance(), ModType.FLAT, level));
        baseStats.add(ExactStatData.levelScaled(spAtkRatio * 10f, PokemonSpAtk.getInstance(), ModType.FLAT, level));
        baseStats.add(ExactStatData.levelScaled(spDefRatio * 10f, PokemonSpDef.getInstance(), ModType.FLAT, level));
        baseStats.add(ExactStatData.levelScaled(speedRatio * 30f, PokemonSpeed.getInstance(), ModType.FLAT, level));
        baseStats.add(ExactStatData.levelScaled(hpRatio * 10f, PokemonHealth.getInstance(), ModType.FLAT, level));

        return baseStats;
    }

    /**
     * Sync Mine and Slash Level -> Cobblemon Level
     */
    @SubscribeEvent
    public static void onLevelUp(com.robertx22.mine_and_slash.event_hooks.my_events.EntityLevelUpEvent event) {
        if (event.getEntity() instanceof PokemonEntity pokemonEntity) {
            Pokemon pokemon = pokemonEntity.getPokemon();
            if (pokemon != null) {
                // If the new M&S level is different from the Pokemon level, update it.
                // This ensures the "Lv. X" tag above the pokemon matches the M&S level.
                int newLevel = event.getNewLevel();
                if (pokemon.getLevel() != newLevel) {
                    pokemon.setLevel(newLevel);
                }
            }
        }
    }

    public static void syncLevel(Pokemon pokemon) {
        if (pokemon != null && pokemon.getEntity() != null) {
            com.robertx22.mine_and_slash.capability.entity.EntityData data = com.robertx22.mine_and_slash.uncommon.datasaving.Load
                    .Unit(pokemon.getEntity());
            if (data != null && data.getLevel() != pokemon.getLevel()) {
                data.setLevel(pokemon.getLevel());
            }
        }
    }
}
