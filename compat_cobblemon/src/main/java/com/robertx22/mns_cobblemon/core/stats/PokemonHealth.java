package com.robertx22.mns_cobblemon.core.stats;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import net.minecraft.ChatFormatting;

/**
 * Pokemon HP stat - increases the Pokemon's total health pool
 */
public class PokemonHealth extends Stat {
    public static String GUID = "pokemon_health";

    private PokemonHealth() {
        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.MAIN;
        this.order = 0;
        this.icon = "\u2764";
        this.format = ChatFormatting.RED.getName();
    }

    public static PokemonHealth getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases your Pokemon's total HP.";
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Pokemon HP";
    }

    private static class SingletonHolder {
        private static final PokemonHealth INSTANCE = new PokemonHealth();
    }
}
