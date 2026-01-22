package com.robertx22.mns_cobblemon.core.types;

import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import net.minecraft.ChatFormatting;

import java.util.HashMap;
import java.util.Map;

/**
 * Maps Cobblemon's 18 Pokemon types to Mine and Slash's element system.
 * 
 * Mapping Strategy:
 * - Fire: Fire, Dragon (hot/burning theme)
 * - Cold: Ice, Water (cold/water theme)
 * - Nature (Lightning): Electric, Grass, Bug (nature/energy theme)
 * - Shadow (Chaos): Dark, Ghost, Poison, Psychic (dark/otherworldly theme)
 * - Physical: Normal, Fighting, Rock, Ground, Steel (physical/mundane theme)
 * - Flying and Fairy are mapped to Nature and Shadow respectively for gameplay
 * balance
 */
public class PokemonTypeMapping {

    private static final Map<String, Elements> TYPE_TO_ELEMENT = new HashMap<>();
    private static final Map<String, ChatFormatting> TYPE_COLORS = new HashMap<>();

    static {
        // Fire element mappings
        TYPE_TO_ELEMENT.put("Fire", Elements.Fire);
        TYPE_TO_ELEMENT.put("Dragon", Elements.Fire);

        // Cold element mappings
        TYPE_TO_ELEMENT.put("Ice", Elements.Cold);
        TYPE_TO_ELEMENT.put("Water", Elements.Cold);

        // Nature (Lightning) element mappings
        TYPE_TO_ELEMENT.put("Electric", Elements.Nature);
        TYPE_TO_ELEMENT.put("Grass", Elements.Nature);
        TYPE_TO_ELEMENT.put("Bug", Elements.Nature);
        TYPE_TO_ELEMENT.put("Flying", Elements.Nature);

        // Shadow (Chaos) element mappings
        TYPE_TO_ELEMENT.put("Dark", Elements.Shadow);
        TYPE_TO_ELEMENT.put("Ghost", Elements.Shadow);
        TYPE_TO_ELEMENT.put("Poison", Elements.Shadow);
        TYPE_TO_ELEMENT.put("Psychic", Elements.Shadow);
        TYPE_TO_ELEMENT.put("Fairy", Elements.Shadow);

        // Physical element mappings
        TYPE_TO_ELEMENT.put("Normal", Elements.Physical);
        TYPE_TO_ELEMENT.put("Fighting", Elements.Physical);
        TYPE_TO_ELEMENT.put("Rock", Elements.Physical);
        TYPE_TO_ELEMENT.put("Ground", Elements.Physical);
        TYPE_TO_ELEMENT.put("Steel", Elements.Physical);

        // Type colors for affixes
        TYPE_COLORS.put("Normal", ChatFormatting.WHITE);
        TYPE_COLORS.put("Fire", ChatFormatting.RED);
        TYPE_COLORS.put("Water", ChatFormatting.BLUE);
        TYPE_COLORS.put("Grass", ChatFormatting.GREEN);
        TYPE_COLORS.put("Electric", ChatFormatting.YELLOW);
        TYPE_COLORS.put("Ice", ChatFormatting.AQUA);
        TYPE_COLORS.put("Fighting", ChatFormatting.DARK_RED);
        TYPE_COLORS.put("Poison", ChatFormatting.DARK_PURPLE);
        TYPE_COLORS.put("Ground", ChatFormatting.GOLD);
        TYPE_COLORS.put("Flying", ChatFormatting.WHITE);
        TYPE_COLORS.put("Psychic", ChatFormatting.LIGHT_PURPLE);
        TYPE_COLORS.put("Bug", ChatFormatting.DARK_GREEN);
        TYPE_COLORS.put("Rock", ChatFormatting.GRAY);
        TYPE_COLORS.put("Ghost", ChatFormatting.DARK_PURPLE);
        TYPE_COLORS.put("Dragon", ChatFormatting.DARK_BLUE);
        TYPE_COLORS.put("Dark", ChatFormatting.DARK_GRAY);
        TYPE_COLORS.put("Steel", ChatFormatting.GRAY);
        TYPE_COLORS.put("Fairy", ChatFormatting.LIGHT_PURPLE);
    }

    /**
     * Get the MnS Element for a Cobblemon type name
     */
    public static Elements getElement(String pokemonTypeName) {
        return TYPE_TO_ELEMENT.getOrDefault(pokemonTypeName, Elements.Physical);
    }

    /**
     * Get the MnS Element for a Cobblemon ElementalType
     */
    public static Elements getElement(ElementalType type) {
        if (type == null) {
            return Elements.Physical;
        }
        return getElement(type.getName());
    }

    /**
     * Get the chat color for a Pokemon type
     */
    public static ChatFormatting getColor(String pokemonTypeName) {
        return TYPE_COLORS.getOrDefault(pokemonTypeName, ChatFormatting.WHITE);
    }

    /**
     * Get all Pokemon type names
     */
    public static String[] getAllTypeNames() {
        return new String[] {
                "Normal", "Fire", "Water", "Grass", "Electric", "Ice",
                "Fighting", "Poison", "Ground", "Flying", "Psychic", "Bug",
                "Rock", "Ghost", "Dragon", "Dark", "Steel", "Fairy"
        };
    }

    /**
     * Get the Cobblemon ElementalType by name
     */
    public static ElementalType getCobblemonType(String name) {
        return ElementalTypes.get(name);
    }
}
