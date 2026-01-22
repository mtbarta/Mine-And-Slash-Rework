package com.robertx22.mns_cobblemon.core.stats;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import net.minecraft.ChatFormatting;

/**
 * Pokemon Speed stat - increases movement speed and dodge chance
 */
public class PokemonSpeed extends Stat {
    public static String GUID = "pokemon_speed";

    private PokemonSpeed() {
        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.MAIN;
        this.order = 5;
        this.icon = "\u26A1";
        this.format = ChatFormatting.YELLOW.getName();
    }

    public static PokemonSpeed getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases your Pokemon's movement speed and dodge chance.";
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
        return "Pokemon Speed";
    }

    private static class SingletonHolder {
        private static final PokemonSpeed INSTANCE = new PokemonSpeed();
    }
}
