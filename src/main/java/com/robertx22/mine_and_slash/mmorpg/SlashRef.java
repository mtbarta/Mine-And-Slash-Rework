package com.robertx22.mine_and_slash.mmorpg;

import com.robertx22.library_of_exile.localization.TranslationKeyBuilder;
import net.minecraft.resources.ResourceLocation;

public class SlashRef {

    public static ResourceLocation id(String id) {
        return ResourceLocation.fromNamespaceAndPath(MODID, id);
    }

    public static ResourceLocation guiId(String id) {
        return ResourceLocation.fromNamespaceAndPath(SlashRef.MODID, "textures/gui/" + id + ".png");
    }

    // For WidgetSprites and blitSprite - expects textures in textures/gui/sprites/
    // Path format: just the sprite path without "textures/gui/sprites/" prefix or
    // ".png" extension
    public static ResourceLocation spriteId(String id) {
        return ResourceLocation.fromNamespaceAndPath(SlashRef.MODID, id);
    }

    public static final String MODID = "mmorpg";
    public static final String MOD_NAME = "Mine and Slash";

    public static TranslationKeyBuilder TRANSLATION_KEY = new TranslationKeyBuilder(MODID);

}
