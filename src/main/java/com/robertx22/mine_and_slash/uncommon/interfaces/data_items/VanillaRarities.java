package com.robertx22.mine_and_slash.uncommon.interfaces.data_items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Rarity;

import java.util.HashMap;
import java.util.function.UnaryOperator;

public class VanillaRarities {

    // Style provider methods for the extensible enum system (referenced by
    // enumextensions.json)
    public static UnaryOperator<Style> legendaryStyle() {
        return style -> style.withColor(ChatFormatting.GOLD);
    }

    public static UnaryOperator<Style> mythicStyle() {
        return style -> style.withColor(ChatFormatting.DARK_PURPLE);
    }

    public static UnaryOperator<Style> uniqueStyle() {
        return style -> style.withColor(ChatFormatting.RED);
    }

    public static UnaryOperator<Style> runedStyle() {
        return style -> style.withColor(ChatFormatting.YELLOW);
    }

    public static UnaryOperator<Style> uncommonStyle() {
        return style -> style.withColor(ChatFormatting.GREEN);
    }

    // Access the custom rarities created by the enum extension system
    public static Rarity LEGENDARY_ITEM = Rarity.valueOf("MNS_LEGENDARY");
    public static Rarity MYTHIC_ITEM = Rarity.valueOf("MNS_MYTHIC");
    public static Rarity UNIQUE_ITEM = Rarity.valueOf("MNS_UNIQUE");
    public static Rarity RUNED_ITEM = Rarity.valueOf("MNS_RUNED");
    public static Rarity UNCOMMON_ITEM = Rarity.valueOf("MNS_UNCOMMON");

    public static HashMap<String, Rarity> MAP = new HashMap<>();

    public static void init() {

        MAP.put(IRarity.COMMON_ID, Rarity.COMMON);
        MAP.put(IRarity.UNCOMMON, UNCOMMON_ITEM);
        MAP.put(IRarity.RARE_ID, Rarity.RARE);
        MAP.put(IRarity.EPIC_ID, Rarity.EPIC);
        MAP.put(IRarity.LEGENDARY_ID, LEGENDARY_ITEM);
        MAP.put(IRarity.MYTHIC_ID, MYTHIC_ITEM);

        MAP.put(IRarity.UNIQUE_ID, UNIQUE_ITEM);
        MAP.put(IRarity.RUNEWORD_ID, RUNED_ITEM);

    }

}
