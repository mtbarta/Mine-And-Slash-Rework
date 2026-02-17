package com.robertx22.mns_cobblemon.core;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.mine_and_slash.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.health.Health;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.energy.Energy;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.mana.Mana;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.BonusPhysicalAsElemental;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.ElementalResist;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.DodgeRating;
import com.robertx22.mine_and_slash.tags.imp.SlotTag;

import com.robertx22.mine_and_slash.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.Armor;

import static com.robertx22.mine_and_slash.uncommon.enumclasses.Elements.*;

public class KobblemonAffixes implements ExileRegistryInit {

        @Override
        public void registerAll() {
                // ===== Implicit Affixes for Pokemon Held Items =====
                // Follows the same pattern as ImplicitAffixes.java in base mod
                SlotTag pokemonHeldItem = SlotTag.of("pokemon_held_item");

                AffixBuilder.Normal("pokemon_hp_item").Named("Sitrus Berry")
                                .stats(Health.getInstance().mod(5, 15))
                                .includesTags(pokemonHeldItem).Implicit().Build();

                AffixBuilder.Normal("pokemon_mana_item").Named("Leppa Berry")
                                .stats(Mana.getInstance().mod(10, 20))
                                .includesTags(pokemonHeldItem).Implicit().Build();

                AffixBuilder.Normal("pokemon_ms_item").Named("Apicot Berry")
                                .stats(MagicShield.getInstance().mod(5, 10))
                                .includesTags(pokemonHeldItem).Implicit().Build();

                AffixBuilder.Normal("pokemon_energy_item").Named("Lum Berry")
                                .stats(Energy.getInstance().mod(10, 20))
                                .includesTags(pokemonHeldItem).Implicit().Build();

                // ===== Type-Based Affixes (all 18 Pokemon types) =====
                // ===== Type-Based Affixes (all 18 Pokemon types) =====
                registerTypeAffix("normal", "Normal Mastery", Physical);
                registerTypeAffix("fire", "Fire Mastery", Fire);
                registerTypeAffix("water", "Water Mastery", Cold);
                registerTypeAffix("grass", "Grass Mastery", Nature);
                registerTypeAffix("electric", "Electric Mastery", Nature);
                registerTypeAffix("ice", "Ice Mastery", Cold);
                registerTypeAffix("fighting", "Fighting Mastery", Physical);
                registerTypeAffix("poison", "Poison Mastery", Shadow);
                registerTypeAffix("ground", "Ground Mastery", Physical);
                registerTypeAffix("flying", "Flying Mastery", Nature);
                registerTypeAffix("psychic", "Psychic Mastery", Shadow);
                registerTypeAffix("bug", "Bug Mastery", Nature);
                registerTypeAffix("rock", "Rock Mastery", Physical);
                registerTypeAffix("ghost", "Ghost Mastery", Shadow);
                registerTypeAffix("dragon", "Dragon Mastery", Fire);
                registerTypeAffix("dark", "Dark Mastery", Shadow);
                registerTypeAffix("steel", "Steel Mastery", Physical);
                registerTypeAffix("fairy", "Fairy Mastery", Shadow);

                // ===== Nature-Inspired Stat Affixes (Pokemon Natures) =====
                // using StatMod.percent() to ensure they are multipliers

                // Adamant: +Attack (STR), -Sp.Attack (INT)
                AffixBuilder.Normal("adamant").Named("Adamant")
                                .stats(
                                                StatMod.percent(15, 25, DatapackStats.STR))
                                .includesTags(pokemonHeldItem).Prefix().Weight(80).Build();

                // Bold: +Defense (Armor), -Attack (STR)
                AffixBuilder.Normal("bold").Named("Bold")
                                .stats(
                                                StatMod.percent(15, 25, Armor.getInstance()))
                                .includesTags(pokemonHeldItem).Prefix().Weight(80).Build();

                // Modest: +Sp.Attack (INT), -Attack (STR)
                AffixBuilder.Normal("modest").Named("Modest")
                                .stats(
                                                StatMod.percent(15, 25, DatapackStats.INT))
                                .includesTags(pokemonHeldItem).Prefix().Weight(80).Build();

                // Timid: +Speed (DEX), -Attack (STR)
                AffixBuilder.Normal("timid").Named("Timid")
                                .stats(
                                                StatMod.percent(15, 25, DatapackStats.DEX))
                                .includesTags(pokemonHeldItem).Prefix().Weight(80).Build();

                // Calm: +Sp.Defense (Magic Shield), -Attack (STR)
                AffixBuilder.Normal("calm").Named("Calm")
                                .stats(
                                                StatMod.percent(15, 25, MagicShield.getInstance()))
                                .includesTags(pokemonHeldItem).Prefix().Weight(80).Build();

                // Jolly: +Speed (DEX), -Sp.Attack (INT)
                AffixBuilder.Normal("jolly").Named("Jolly")
                                .stats(
                                                StatMod.percent(15, 25, DatapackStats.DEX))
                                .includesTags(pokemonHeldItem).Prefix().Weight(80).Build();

                // ===== Simple Buff Affixes =====

                // Tanky: +HP, +Defense (Armor)
                AffixBuilder.Normal("tanky_mon").Named("Tanky")
                                .stats(
                                                StatMod.percent(30, 50, Health.getInstance()),
                                                StatMod.percent(10, 20, Armor.getInstance()))
                                .includesTags(pokemonHeldItem).Prefix().Weight(100).Build();

                // Powerful: +Attack (STR), +Sp.Attack (INT)
                AffixBuilder.Normal("powerful_mon").Named("Powerful")
                                .stats(
                                                StatMod.percent(15, 25, DatapackStats.STR),
                                                StatMod.percent(15, 25, DatapackStats.INT))
                                .includesTags(pokemonHeldItem).Suffix().Weight(100).Build();

                // Swift: +Speed (DEX), +Dodge
                AffixBuilder.Normal("swift_mon").Named("Swift")
                                .stats(
                                                StatMod.percent(20, 30, DatapackStats.DEX),
                                                StatMod.percent(5, 15, DodgeRating.getInstance()))
                                .includesTags(pokemonHeldItem).Suffix().Weight(100).Build();

                // Resilient: +Sp.Defense (Magic Shield), +Defense (Armor)
                AffixBuilder.Normal("resilient_mon").Named("Resilient")
                                .stats(
                                                StatMod.percent(10, 20, Armor.getInstance()),
                                                StatMod.percent(10, 20, MagicShield.getInstance()))
                                .includesTags(pokemonHeldItem).Suffix().Weight(100).Build();
        }

        /**
         * Helper method to register a type-based affix
         */
        private void registerTypeAffix(String id, String name,
                        com.robertx22.mine_and_slash.uncommon.enumclasses.Elements element) {
                AffixBuilder.Normal(id + "_mastery").Named(name)
                                .stats(
                                                new StatMod(15, 25, new BonusPhysicalAsElemental(element)),
                                                new StatMod(15, 25, new ElementalResist(element)))
                                .includesTags(SlotTag.of("pokemon_held_item"))
                                .Suffix()
                                .Weight(100)
                                .Build();
        }

}
