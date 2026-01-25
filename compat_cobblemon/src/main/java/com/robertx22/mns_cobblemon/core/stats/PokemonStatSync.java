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
            List<ExactStatData> baseStats = new ArrayList<>();

            // HP IVs (0-31) -> Vitality (mapping to "vitality" GUID)
            // Scale so 31 feels "tanky"
            int hpIv = pokemon.getIvs().getOrDefault(Stats.HP);
            baseStats.add(ExactStatData.noScaling(hpIv * 2.0f, ModType.FLAT, "vitality"));
            // Fallback for HP if vitality not recognized
            baseStats.add(ExactStatData.noScaling(hpIv * 1.5f, ModType.PERCENT, Health.getInstance().GUID()));

            // Physical Attack -> Strength
            int physAtk = pokemon.getStat(Stats.ATTACK);
            baseStats.add(ExactStatData.noScaling(physAtk / 5.0f, ModType.FLAT, DatapackStats.STR.GUID()));

            // Physical Defense -> Armor
            int physDef = pokemon.getStat(Stats.DEFENCE);
            baseStats.add(ExactStatData.noScaling(physDef / 2.0f, ModType.FLAT, Armor.getInstance().GUID()));

            // Special Attack -> Intelligence
            int spAtk = pokemon.getStat(Stats.SPECIAL_ATTACK);
            baseStats.add(ExactStatData.noScaling(spAtk / 5.0f, ModType.FLAT, DatapackStats.INT.GUID()));

            // Special Defense -> Elemental Resistances
            int spDef = pokemon.getStat(Stats.SPECIAL_DEFENCE);
            float eleRes = spDef / 4.0f;
            for (Elements ele : Elements.getAllSingle()) {
                if (ele != Elements.Physical) {
                    baseStats.add(ExactStatData.noScaling(eleRes, ModType.FLAT, new ElementalResist(ele).GUID()));
                }
            }

            // Speed -> Dodge / Cooldown Reduction
            int speed = pokemon.getStat(Stats.SPEED);
            baseStats.add(ExactStatData.noScaling(speed / 4.0f, ModType.FLAT, DodgeRating.getInstance().GUID()));
            baseStats.add(ExactStatData.noScaling(speed / 8.0f, ModType.PERCENT,
                    SpellChangeStats.COOLDOWN_REDUCTION.getId()));

            // Apply custom Poke-stats if they exist and are relevant
            baseStats.add(ExactStatData.noScaling(physAtk, ModType.FLAT, PokemonAttack.getInstance().GUID()));
            baseStats.add(ExactStatData.noScaling(physDef, ModType.FLAT, PokemonDefense.getInstance().GUID()));
            baseStats.add(ExactStatData.noScaling(spAtk, ModType.FLAT, PokemonSpAtk.getInstance().GUID()));
            baseStats.add(ExactStatData.noScaling(spDef, ModType.FLAT, PokemonSpDef.getInstance().GUID()));
            baseStats.add(ExactStatData.noScaling(speed, ModType.FLAT, PokemonSpeed.getInstance().GUID()));
            baseStats.add(ExactStatData.noScaling(pokemon.getHp(), ModType.FLAT, PokemonHealth.getInstance().GUID()));

            event.addStatContext(new MiscStatCtx(baseStats));
        }
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
}
