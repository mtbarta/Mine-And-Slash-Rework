package com.robertx22.mine_and_slash.mmorpg.compat;

import dev.muon.dynamic_difficulty.api.LevelingAPI;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

/**
 * Bridge class that directly calls Dynamic Difficulty API.
 * This class is isolated so it only loads when DD is present.
 */
class DynamicDifficultyBridge {

    static boolean hasLevel(Entity entity) {
        return LevelingAPI.hasLevel(entity);
    }

    static int getLevel(LivingEntity entity) {
        return LevelingAPI.getLevel(entity);
    }

    static void setLevel(LivingEntity entity, int level) {
        LevelingAPI.setAndUpdateLevel(entity, level);
    }

    static void registerPlayerLevelProvider() {
        LevelingAPI.registerPlayerLevelProvider(new MnsPlayerLevelProvider());
    }
}
