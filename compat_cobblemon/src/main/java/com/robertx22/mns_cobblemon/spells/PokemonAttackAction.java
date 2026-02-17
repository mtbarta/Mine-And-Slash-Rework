package com.robertx22.mns_cobblemon.spells;

import com.cobblemon.mod.common.api.moves.Move;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;
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
import com.robertx22.mine_and_slash.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.EntityFinder;
import net.minecraft.world.entity.LivingEntity;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

public class PokemonAttackAction extends SpellAction {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Random itemRand = new Random();

    public PokemonAttackAction() {
        super(Arrays.asList());
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {
        LOGGER.info("PokemonAttackAction: tryActivate called for entity {}", ctx.sourceEntity);
        if (ctx.world.isClientSide) {
            return;
        }

        if (!(ctx.sourceEntity instanceof PokemonEntity pokemonEntity)) {
            return;
        }

        // Fallback if no targets found by selector (safety check)
        if (targets.isEmpty() && pokemonEntity.getTarget() != null) {
            targets.add(pokemonEntity.getTarget());
        }

        if (targets.isEmpty()) {
            LOGGER.warn("PokemonAttackAction: No targets found for {}", pokemonEntity);
            return;
        }

        LOGGER.info("PokemonAttackAction: Targets found: {}", targets.size());
        Pokemon pokemon = pokemonEntity.getPokemon();
        MoveSet moveSet = pokemon.getMoveSet();
        List<Move> availableMoves = moveSet.getMoves();

        if (availableMoves.isEmpty()) {
            return;
        }

        // Filter for damaging moves
        List<Move> damagingMoves = availableMoves.stream()
                .filter(move -> !"STATUS".equalsIgnoreCase(move.getDamageCategory().toString()))
                .toList();

        Move selectedMove;
        if (!damagingMoves.isEmpty()) {
            selectedMove = damagingMoves.get(itemRand.nextInt(damagingMoves.size()));
        } else {
            // Pick a random move from all moves if no damaging moves are found
            selectedMove = availableMoves.get(itemRand.nextInt(availableMoves.size()));
        }

        LOGGER.info("PokemonAttackAction: Selected move: {} (Type: {}, Power: {}, Category: {})",
                selectedMove.getName(), selectedMove.getType(), selectedMove.getPower(),
                selectedMove.getDamageCategory());

        // Determine Element from Move Type
        Elements element = PokemonTypeMapping.getElement(selectedMove.getType());

        // AOE Implementation postponed: Move.getTarget() API is not available.
        // Defaulting to single target for now.

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

        // Check Move Category (Physical/Special)
        // Using toString to avoid import issues with DamageCategory enum
        boolean isPhysical = "PHYSICAL".equalsIgnoreCase(selectedMove.getDamageCategory().toString());

        float effectiveness = (float) (power / 100.0);
        int baseValue;

        if (isPhysical) {
            // Use EntityData to get calculated Weapon Damage
            // pokemonEntity is already cast from ctx.sourceEntity above
            com.robertx22.mine_and_slash.capability.entity.EntityData eData = com.robertx22.mine_and_slash.capability.entity.EntityData
                    .get(pokemonEntity);
            double weaponDmg = 0;
            if (eData != null) {
                weaponDmg = eData.getUnit()
                        .getCalculatedStat(
                                com.robertx22.mine_and_slash.database.data.stats.types.offense.WeaponDamage
                                        .getInstance())
                        .getValue();
            }

            if (weaponDmg < 1) {
                // log warning that weapon damage is too low
                weaponDmg = 8;
            }

            // Scale by power
            baseValue = (int) (weaponDmg * effectiveness);
            LOGGER.info("PokemonAttackAction: Physical attack. WeaponDmg: {}, Effectiveness: {}, BaseValue: {}",
                    weaponDmg, effectiveness, baseValue);
        } else {
            // Special attacks
            // Use EntityData to get calculated Special Attack
            com.robertx22.mine_and_slash.capability.entity.EntityData eData = com.robertx22.mine_and_slash.capability.entity.EntityData
                    .get(pokemonEntity);
            double spAtk = 0;
            double physAtk = 0;

            if (eData != null) {
                spAtk = eData.getUnit()
                        .getCalculatedStat(
                                com.robertx22.mns_cobblemon.core.stats.PokemonSpAtk
                                        .getInstance())
                        .getValue();

                physAtk = eData.getUnit()
                        .getCalculatedStat(
                                com.robertx22.mine_and_slash.database.data.stats.types.offense.WeaponDamage
                                        .getInstance())
                        .getValue();
            }

            if (spAtk < 1)
                spAtk = 8;
            if (physAtk < 1)
                physAtk = 8;

            // Hybrid scaling: Average of SpAtk and PhysAtk (WeaponDmg)
            // This ensures high physical stat Pokemon still do okay with special moves and
            // vice versa
            double hybridStat = (spAtk + physAtk) / 2.0;

            baseValue = (int) (hybridStat * effectiveness);
            LOGGER.info("PokemonAttackAction: Special/Hybrid attack. HybridStat: {}, Effectiveness: {}, BaseValue: {}",
                    hybridStat, effectiveness, baseValue);
        }

        // Check Ability properties
        String abilityName = pokemon.getAbility().getName();
        boolean isAoe = com.robertx22.mns_cobblemon.core.spells.PokemonAttackProperties.isAoe(abilityName);
        boolean isRanged = com.robertx22.mns_cobblemon.core.spells.PokemonAttackProperties.isRanged(abilityName);

        LOGGER.info("PokemonAttackAction: Ability: {}, isAoe: {}, isRanged: {}", abilityName, isAoe, isRanged);

        java.util.Set<LivingEntity> finalTargets = new java.util.HashSet<>();

        // 1. Process initial targets and check Range
        for (LivingEntity target : targets) {
            double dist = pokemonEntity.distanceTo(target);
            // Melee range check (allow some leeway, e.g., 5 blocks)
            if (!isRanged && dist > 5.0) {
                LOGGER.info("PokemonAttackAction: Target {} skipped (Distance: {}, Melee Limit: 5.0)", target, dist);
                continue;
            }
            finalTargets.add(target);
        }

        // 2. Apply AOE if applicable
        if (isAoe) {
            java.util.Set<LivingEntity> currentTargets = new java.util.HashSet<>(finalTargets);
            for (LivingEntity center : currentTargets) {
                // Use EntityFinder to find enemies around the target
                // Radius 3.0 for AOE splash
                java.util.List<LivingEntity> aoeFound = EntityFinder
                        .start(pokemonEntity, LivingEntity.class, center.position())
                        .radius(3.0)
                        .searchFor(AllyOrEnemy.enemies)
                        .build();

                finalTargets.addAll(aoeFound);
            }
        }

        if (finalTargets.isEmpty()) {
            LOGGER.info("PokemonAttackAction: No valid targets after range/AOE checks.");
            return;
        }

        for (LivingEntity target : finalTargets) {
            DamageEvent dmg;

            if (isPhysical) {
                // Physical Hit
                dmg = EventBuilder
                        .ofDamage(ctx.caster, target, baseValue)
                        .setupDamage(com.robertx22.mine_and_slash.uncommon.enumclasses.AttackType.hit,
                                com.robertx22.mine_and_slash.uncommon.enumclasses.WeaponTypes.none,
                                com.robertx22.mine_and_slash.uncommon.enumclasses.PlayStyle.STR)
                        .build();
                dmg.setElement(element);
            } else {
                // Spell/Special
                dmg = EventBuilder
                        .ofSpellDamage(ctx.caster, target, baseValue, ctx.calculatedSpellData.getSpell())
                        .build();
                dmg.setElement(element);
                dmg.data.setupNumber(EventData.DMG_EFFECTIVENESS, effectiveness);
            }

            dmg.data.setBoolean(EventData.IS_SUMMON_ATTACK, true);
            dmg.petEntity = pokemonEntity;

            dmg.Activate();
            LOGGER.info("PokemonAttackAction: Damage event activated for target {}", target);

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
