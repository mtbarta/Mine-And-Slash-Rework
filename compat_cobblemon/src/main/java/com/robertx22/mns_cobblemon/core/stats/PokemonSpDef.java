package com.robertx22.mns_cobblemon.core.stats;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import net.minecraft.ChatFormatting;

/**
 * Pokemon Special Defense stat - reduces elemental damage taken
 */
public class PokemonSpDef extends Stat {
    public static String GUID = "pokemon_sp_def";

    private PokemonSpDef() {
        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.MAIN;
        this.order = 4;
        this.icon = "\u2748";
        this.format = ChatFormatting.DARK_AQUA.getName();
    }

    public static PokemonSpDef getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Reduces elemental damage taken by your Pokemon.";
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return Elements.Elemental;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Pokemon Sp. Defense";
    }

    private static class SingletonHolder {
        private static final PokemonSpDef INSTANCE = new PokemonSpDef();
    }
}
