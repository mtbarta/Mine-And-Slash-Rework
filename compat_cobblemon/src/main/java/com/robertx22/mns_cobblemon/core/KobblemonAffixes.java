package com.robertx22.mns_cobblemon.core;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.database.data.mob_affixes.MobAffix;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.SkillDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.health.Health;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.BonusPhysicalAsElemental;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.ElementalResist;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.DodgeRating;
import com.robertx22.mns_cobblemon.core.types.PokemonTypeMapping;
import net.minecraft.ChatFormatting;

import com.robertx22.mine_and_slash.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.SpellChangeStats;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.Armor;

import static com.robertx22.mine_and_slash.uncommon.enumclasses.Elements.*;

public class KobblemonAffixes implements ExileRegistryInit {

        @Override
        public void registerAll() {
                // ===== Type-Based Affixes (all 18 Pokemon types) =====
                registerTypeAffix("normal", "Normal Mastery", ChatFormatting.WHITE, Physical);
                registerTypeAffix("fire", "Fire Mastery", ChatFormatting.RED, Fire);
                registerTypeAffix("water", "Water Mastery", ChatFormatting.BLUE, Cold);
                registerTypeAffix("grass", "Grass Mastery", ChatFormatting.GREEN, Nature);
                registerTypeAffix("electric", "Electric Mastery", ChatFormatting.YELLOW, Nature);
                registerTypeAffix("ice", "Ice Mastery", ChatFormatting.AQUA, Cold);
                registerTypeAffix("fighting", "Fighting Mastery", ChatFormatting.DARK_RED, Physical);
                registerTypeAffix("poison", "Poison Mastery", ChatFormatting.DARK_PURPLE, Shadow);
                registerTypeAffix("ground", "Ground Mastery", ChatFormatting.GOLD, Physical);
                registerTypeAffix("flying", "Flying Mastery", ChatFormatting.WHITE, Nature);
                registerTypeAffix("psychic", "Psychic Mastery", ChatFormatting.LIGHT_PURPLE, Shadow);
                registerTypeAffix("bug", "Bug Mastery", ChatFormatting.DARK_GREEN, Nature);
                registerTypeAffix("rock", "Rock Mastery", ChatFormatting.GRAY, Physical);
                registerTypeAffix("ghost", "Ghost Mastery", ChatFormatting.DARK_PURPLE, Shadow);
                registerTypeAffix("dragon", "Dragon Mastery", ChatFormatting.DARK_BLUE, Fire);
                registerTypeAffix("dark", "Dark Mastery", ChatFormatting.DARK_GRAY, Shadow);
                registerTypeAffix("steel", "Steel Mastery", ChatFormatting.GRAY, Physical);
                registerTypeAffix("fairy", "Fairy Mastery", ChatFormatting.LIGHT_PURPLE, Shadow);

                // ===== Nature-Inspired Stat Affixes (Pokemon Natures) =====
                // using StatMod.percent() to ensure they are multipliers

                // Adamant: +Attack (STR), -Sp.Attack (INT)
                new MobAffix("adamant", "Adamant", ChatFormatting.DARK_RED, Affix.AffixSlot.prefix)
                                .setMods(
                                                StatMod.percent(15, 25, DatapackStats.STR),
                                                StatMod.percent(-10, -10, DatapackStats.INT))
                                .setWeight(80)
                                .addToSerializables(MMORPG.SERIAZABLE_REGISTRATION_INFO);

                // Bold: +Defense (Armor), -Attack (STR)
                new MobAffix("bold", "Bold", ChatFormatting.BLUE, Affix.AffixSlot.prefix)
                                .setMods(
                                                StatMod.percent(15, 25, Armor.getInstance()),
                                                StatMod.percent(-10, -10, DatapackStats.STR))
                                .setWeight(80)
                                .addToSerializables(MMORPG.SERIAZABLE_REGISTRATION_INFO);

                // Modest: +Sp.Attack (INT), -Attack (STR)
                new MobAffix("modest", "Modest", ChatFormatting.LIGHT_PURPLE, Affix.AffixSlot.prefix)
                                .setMods(
                                                StatMod.percent(15, 25, DatapackStats.INT),
                                                StatMod.percent(-10, -10, DatapackStats.STR))
                                .setWeight(80)
                                .addToSerializables(MMORPG.SERIAZABLE_REGISTRATION_INFO);

                // Timid: +Speed (DEX), -Attack (STR)
                new MobAffix("timid", "Timid", ChatFormatting.YELLOW, Affix.AffixSlot.prefix)
                                .setMods(
                                                StatMod.percent(15, 25, DatapackStats.DEX),
                                                StatMod.percent(-10, -10, DatapackStats.STR))
                                .setWeight(80)
                                .addToSerializables(MMORPG.SERIAZABLE_REGISTRATION_INFO);

                // Calm: +Sp.Defense (Magic Shield), -Attack (STR)
                new MobAffix("calm", "Calm", ChatFormatting.AQUA, Affix.AffixSlot.prefix)
                                .setMods(
                                                StatMod.percent(15, 25, MagicShield.getInstance()),
                                                StatMod.percent(-10, -10, DatapackStats.STR))
                                .setWeight(80)
                                .addToSerializables(MMORPG.SERIAZABLE_REGISTRATION_INFO);

                // Jolly: +Speed (DEX), -Sp.Attack (INT)
                new MobAffix("jolly", "Jolly", ChatFormatting.GREEN, Affix.AffixSlot.prefix)
                                .setMods(
                                                StatMod.percent(15, 25, DatapackStats.DEX),
                                                StatMod.percent(-10, -10, DatapackStats.INT))
                                .setWeight(80)
                                .addToSerializables(MMORPG.SERIAZABLE_REGISTRATION_INFO);

                // ===== Simple Buff Affixes =====

                // Tanky: +HP, +Defense (Armor)
                new MobAffix("tanky_mon", "Tanky", ChatFormatting.GREEN, Affix.AffixSlot.prefix)
                                .setMods(
                                                StatMod.percent(30, 50, Health.getInstance()),
                                                StatMod.percent(10, 20, Armor.getInstance()))
                                .setWeight(100)
                                .addToSerializables(MMORPG.SERIAZABLE_REGISTRATION_INFO);

                // Powerful: +Attack (STR), +Sp.Attack (INT)
                new MobAffix("powerful_mon", "Powerful", ChatFormatting.DARK_RED, Affix.AffixSlot.suffix)
                                .setMods(
                                                StatMod.percent(15, 25, DatapackStats.STR),
                                                StatMod.percent(15, 25, DatapackStats.INT))
                                .setWeight(100)
                                .addToSerializables(MMORPG.SERIAZABLE_REGISTRATION_INFO);

                // Swift: +Speed (DEX), +Dodge
                new MobAffix("swift_mon", "Swift", ChatFormatting.YELLOW, Affix.AffixSlot.suffix)
                                .setMods(
                                                StatMod.percent(20, 30, DatapackStats.DEX),
                                                StatMod.percent(5, 15, DodgeRating.getInstance()))
                                .setWeight(100)
                                .addToSerializables(MMORPG.SERIAZABLE_REGISTRATION_INFO);

                // Resilient: +Sp.Defense (Magic Shield), +Defense (Armor)
                new MobAffix("resilient_mon", "Resilient", ChatFormatting.DARK_AQUA, Affix.AffixSlot.suffix)
                                .setMods(
                                                StatMod.percent(10, 20, Armor.getInstance()),
                                                StatMod.percent(10, 20, MagicShield.getInstance()))
                                .setWeight(100)
                                .addToSerializables(MMORPG.SERIAZABLE_REGISTRATION_INFO);
        }

        /**
         * Helper method to register a type-based affix
         */
        private void registerTypeAffix(String id, String name, ChatFormatting color,
                        com.robertx22.mine_and_slash.uncommon.enumclasses.Elements element) {
                new MobAffix(id + "_mastery", name, color, Affix.AffixSlot.suffix)
                                .setMods(
                                                new StatMod(15, 25, new BonusPhysicalAsElemental(element)),
                                                new StatMod(15, 25, new ElementalResist(element)))
                                .setWeight(100)
                                .addToSerializables(MMORPG.SERIAZABLE_REGISTRATION_INFO);
        }
}
