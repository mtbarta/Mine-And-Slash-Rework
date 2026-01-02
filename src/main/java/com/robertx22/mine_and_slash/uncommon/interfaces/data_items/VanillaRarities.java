package com.robertx22.mine_and_slash.uncommon.interfaces.data_items;

import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Rarity;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;

import java.util.HashMap;

public class VanillaRarities {

    public static Rarity LEGENDARY_ITEM = Rarity.create("LEGENDARY_ITEM",
            new ResourceLocation(SlashRef.MODID, "legendary_item"), ChatFormatting.GOLD);
    public static Rarity MYTHIC_ITEM = Rarity.create("MYTHIC_ITEM",
            new ResourceLocation(SlashRef.MODID, "mythic_item"), ChatFormatting.DARK_PURPLE);
    public static Rarity UNIQUE_ITEM = Rarity.create("UNIQUE_ITEM",
            new ResourceLocation(SlashRef.MODID, "unique_item"), ChatFormatting.RED);
    public static Rarity RUNED_ITEM = Rarity.create("RUNED_ITEM", new ResourceLocation(SlashRef.MODID, "runed_item"),
            ChatFormatting.YELLOW);
    public static Rarity UNCOMMON_ITEM = Rarity.create("UNCOMMON_ITEM",
            new ResourceLocation(SlashRef.MODID, "uncommon_item"), ChatFormatting.GREEN);

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
