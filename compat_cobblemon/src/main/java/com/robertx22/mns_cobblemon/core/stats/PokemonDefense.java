package com.robertx22.mns_cobblemon.core.stats;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import net.minecraft.ChatFormatting;

/**
 * Pokemon Defense stat - reduces physical damage taken
 */
public class PokemonDefense extends Stat {
    public static String GUID = "pokemon_defense";

    private PokemonDefense() {
        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.MAIN;
        this.order = 3;
        this.icon = "\u26E8";
        this.format = ChatFormatting.GOLD.getName();
    }

    public static PokemonDefense getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Reduces physical damage taken by your Pokemon.";
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Pokemon Defense";
    }

    private static class SingletonHolder {
        private static final PokemonDefense INSTANCE = new PokemonDefense();
    }
}
