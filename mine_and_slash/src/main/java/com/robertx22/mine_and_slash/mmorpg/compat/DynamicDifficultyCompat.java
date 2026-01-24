package com.robertx22.mine_and_slash.mmorpg.compat;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.ModList;

/**
 * Soft compatibility layer for Dynamic Difficulty mod.
 * Uses class loading guards to handle when DD isn't present at runtime.
 */
public class DynamicDifficultyCompat {

    private static Boolean loaded = null;

    /**
     * Check if Dynamic Difficulty mod is loaded.
     * Cached after first check.
     */
    public static boolean isLoaded() {
        if (loaded == null) {
            loaded = ModList.get().isLoaded("dynamic_difficulty");
        }
        return loaded;
    }

    /**
     * Check if an entity has a level in Dynamic Difficulty.
     * Returns false if DD is not loaded.
     */
    public static boolean hasLevel(Entity entity) {
        if (!isLoaded()) {
            return false;
        }
        try {
            return DynamicDifficultyBridge.hasLevel(entity);
        } catch (NoClassDefFoundError e) {
            loaded = false;
            return false;
        }
    }

    /**
     * Get an entity's level from Dynamic Difficulty.
     * Returns -1 if DD is not loaded or entity has no level.
     */
    public static int getLevel(LivingEntity entity) {
        if (!isLoaded()) {
            return -1;
        }
        try {
            return DynamicDifficultyBridge.getLevel(entity);
        } catch (NoClassDefFoundError e) {
            loaded = false;
            return -1;
        }
    }

    /**
     * Set an entity's level in Dynamic Difficulty.
     * No-op if DD is not loaded or entity is a player.
     */
    public static void setLevel(LivingEntity entity, int level) {
        if (!isLoaded() || entity instanceof Player) {
            return;
        }
        try {
            DynamicDifficultyBridge.setLevel(entity, level);
        } catch (NoClassDefFoundError e) {
            loaded = false;
        }
    }

    /**
     * Register Mine and Slash as a player level provider with Dynamic Difficulty.
     * Should be called during mod initialization.
     */
    public static void registerPlayerLevelProvider() {
        if (!isLoaded()) {
            return;
        }
        try {
            DynamicDifficultyBridge.registerPlayerLevelProvider();
        } catch (NoClassDefFoundError e) {
            loaded = false;
        }
    }
}
