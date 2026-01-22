package com.robertx22.mns_cobblemon.core.stats;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import net.minecraft.ChatFormatting;

/**
 * Pokemon Attack stat - increases physical attack damage
 */
public class PokemonAttack extends Stat {
    public static String GUID = "pokemon_attack";

    private PokemonAttack() {
        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.MAIN;
        this.order = 1;
        this.icon = "\u2694";
        this.format = ChatFormatting.DARK_RED.getName();
    }

    public static PokemonAttack getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases your Pokemon's physical attack damage.";
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
        return "Pokemon Attack";
    }

    private static class SingletonHolder {
        private static final PokemonAttack INSTANCE = new PokemonAttack();
    }
}
