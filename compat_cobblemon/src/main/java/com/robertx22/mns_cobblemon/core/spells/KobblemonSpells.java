package com.robertx22.mns_cobblemon.core.spells;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.mine_and_slash.aoe_data.database.spells.PartBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.spells.SpellBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.spells.schools.SummonSpells;
import com.robertx22.mine_and_slash.database.data.spells.components.SpellConfiguration;
import com.robertx22.mine_and_slash.database.data.spells.components.actions.SpellAction;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.mine_and_slash.tags.all.SpellTags;
import com.robertx22.mine_and_slash.uncommon.enumclasses.PlayStyle;
import com.robertx22.mns_cobblemon.spells.PokemonAttackAction;

import java.util.Arrays;

public class KobblemonSpells implements ExileRegistryInit {

    public static String POKEMON_BASIC_ATTACK = "pokemon_basic_attack";

    public static PokemonAttackAction POKEMON_ATTACK = new PokemonAttackAction();

    @Override
    public void registerAll() {
        // Register the action manually since we can't add to the enum-like static block
        // in SpellAction
        SpellAction.MAP.put(POKEMON_ATTACK.GUID(), POKEMON_ATTACK);

        SpellBuilder.of(POKEMON_BASIC_ATTACK, PlayStyle.INT,
                SpellConfiguration.Builder.energy(3, 1).setUsesSupportGemsFrom(SummonSpells.SUMMON_POKEMON),
                "Pokemon Attack",
                Arrays.asList(SpellTags.summon, SpellTags.damage))
                .defaultAndMaxLevel(1)
                .manualDesc("A dynamic attack based on the Pokemon's moves.")
                .weaponReq(CastingWeapon.ANY_WEAPON)
                .onHit(PartBuilder.justAction(POKEMON_ATTACK))
                .levelReq(1)
                .build();
    }
}
