package com.robertx22.mns_cobblemon.core.stats;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import net.minecraft.ChatFormatting;

/**
 * Pokemon Special Attack stat - increases elemental/special attack damage
 */
public class PokemonSpAtk extends Stat {
    public static String GUID = "pokemon_sp_atk";

    private PokemonSpAtk() {
        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.MAIN;
        this.order = 2;
        this.icon = "\u2728";
        this.format = ChatFormatting.LIGHT_PURPLE.getName();
    }

    public static PokemonSpAtk getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases your Pokemon's special/elemental attack damage.";
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
        return "Pokemon Sp. Attack";
    }

    private static class SingletonHolder {
        private static final PokemonSpAtk INSTANCE = new PokemonSpAtk();
    }
}
