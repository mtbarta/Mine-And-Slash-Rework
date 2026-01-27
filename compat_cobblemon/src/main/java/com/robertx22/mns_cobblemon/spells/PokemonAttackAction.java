package com.robertx22.mns_cobblemon.spells;

import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.moves.MoveSet;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.robertx22.mine_and_slash.database.data.spells.components.MapHolder;
import com.robertx22.mine_and_slash.database.data.spells.components.actions.SpellAction;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EventBuilder;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.EventData;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mns_cobblemon.core.types.PokemonTypeMapping;
import net.minecraft.world.entity.LivingEntity;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class PokemonAttackAction extends SpellAction {

    private static final Random itemRand = new Random();

    public PokemonAttackAction() {
        super(Arrays.asList());
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {
        if (ctx.world.isClientSide) {
            return;
        }

        if (!(ctx.caster instanceof PokemonEntity pokemonEntity)) {
            return;
        }

        Pokemon pokemon = pokemonEntity.getPokemon();
        MoveSet moveSet = pokemon.getMoveSet();
        List<Move> availableMoves = moveSet.getMoves();

        if (availableMoves.isEmpty()) {
            return;
        }

        // Pick a random move
        Move selectedMove = availableMoves.get(itemRand.nextInt(availableMoves.size()));

        // Determine Element from Move Type
        Elements element = PokemonTypeMapping.getElement(selectedMove.getType());

        // Calculate Damage
        // Logic: (Move Power / 20) * (Attack or SpAtk based on move category)
        // For simplicity initially, we'll just scale slightly with power and let stats
        // do the heavy lifting via M&S damage calculation
        // M&S dmg calculation happens when we trigger the event, using the caster's
        // stats.
        // We just need to provide a base value or rely on the stats.

        // If we want the move's power to matter:
        double power = selectedMove.getPower();
        if (power <= 0)
            power = 40; // Default for status moves if they get picked, though usually we might filter
                        // them? Keeping simple.

        // M&S spells usually have a base value + scaling.
        // Since we are "hacking" this into a single spell, we'll treat the power as a
        // multiplier on the base "Hit"
        // But `EventBuilder.ofSpellDamage` takes a flat value.

        // Let's assume the "Weapon Damage" equivalent is the Pokemon's stats which are
        // already mapped.
        // We will pass a flat value that scales with the move power.
        // Standard basic attack is often ~100% weapon damage.
        // Let's say Power 100 = 100% effectiveness. Power 40 = 40%.

        float effectiveness = (float) (power / 100.0);

        // However, `dmg.data.setupNumber(EventData.DMG_EFFECTIVENESS,
        // dmgEffectiveness)` handles spell scaling.
        // And the `value` passed to `ofSpellDamage` is usually the base damage.
        // If we want it to scale purely off stats, we might want a small base value.

        int baseValue = (int) (power * 0.1f); // Small base flat damage

        for (LivingEntity target : targets) {
            DamageEvent dmg = EventBuilder
                    .ofSpellDamage(ctx.caster, target, baseValue, ctx.calculatedSpellData.getSpell()).build();

            // Set dynamic element
            dmg.setElement(element);

            // Set effectiveness based on move power
            dmg.data.setupNumber(EventData.DMG_EFFECTIVENESS, effectiveness);

            dmg.data.setBoolean(EventData.IS_SUMMON_ATTACK, true);
            dmg.petEntity = pokemonEntity;

            // Log move name for flavor? M&S doesn't easily support arbitrary strings in
            // damage log log without extra work.

            dmg.Activate();

            // If specific status effects need to be applied, that would go here, but that's
            // complex mapping.
        }
    }

    public MapHolder create() {
        MapHolder map = new MapHolder();
        map.type = GUID();
        return map;
    }

    @Override
    public String GUID() {
        return "pokemon_attack";
    }
}
