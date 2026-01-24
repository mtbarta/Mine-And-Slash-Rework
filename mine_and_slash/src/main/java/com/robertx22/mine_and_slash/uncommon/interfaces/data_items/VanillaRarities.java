package com.robertx22.mine_and_slash.uncommon.interfaces.data_items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Rarity;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.HashMap;
import java.util.function.UnaryOperator;

public class VanillaRarities {

    // IMPORTANT: Hardcode the mod ID here to avoid class loading order issues
    // EnumProxy is loaded very early during bootstrap
    private static final String MOD_ID = "mmorpg";

    // EnumProxy fields for the extensible enum system (referenced by
    // enumextensions.json)
    // Parameters: Rarity.class, int id (-1 for auto), String name (modid:path
    // format), UnaryOperator<Style>
    public static final EnumProxy<Rarity> LEGENDARY_PROXY = new EnumProxy<>(
            Rarity.class, -1, MOD_ID + ":legendary",
            (UnaryOperator<Style>) style -> style.withColor(ChatFormatting.GOLD));

    public static final EnumProxy<Rarity> MYTHIC_PROXY = new EnumProxy<>(
            Rarity.class, -1, MOD_ID + ":mythic",
            (UnaryOperator<Style>) style -> style.withColor(ChatFormatting.DARK_PURPLE));

    public static final EnumProxy<Rarity> UNIQUE_PROXY = new EnumProxy<>(
            Rarity.class, -1, MOD_ID + ":unique", (UnaryOperator<Style>) style -> style.withColor(ChatFormatting.RED));

    public static final EnumProxy<Rarity> RUNED_PROXY = new EnumProxy<>(
            Rarity.class, -1, MOD_ID + ":runed",
            (UnaryOperator<Style>) style -> style.withColor(ChatFormatting.YELLOW));

    public static final EnumProxy<Rarity> UNCOMMON_PROXY = new EnumProxy<>(
            Rarity.class, -1, MOD_ID + ":uncommon",
            (UnaryOperator<Style>) style -> style.withColor(ChatFormatting.GREEN));

    // Access the custom rarities from the proxies
    public static Rarity LEGENDARY_ITEM;
    public static Rarity MYTHIC_ITEM;
    public static Rarity UNIQUE_ITEM;
    public static Rarity RUNED_ITEM;
    public static Rarity UNCOMMON_ITEM;

    public static HashMap<String, Rarity> MAP = new HashMap<>();

    public static void init() {
        LEGENDARY_ITEM = LEGENDARY_PROXY.getValue();
        MYTHIC_ITEM = MYTHIC_PROXY.getValue();
        UNIQUE_ITEM = UNIQUE_PROXY.getValue();
        RUNED_ITEM = RUNED_PROXY.getValue();
        UNCOMMON_ITEM = UNCOMMON_PROXY.getValue();

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
