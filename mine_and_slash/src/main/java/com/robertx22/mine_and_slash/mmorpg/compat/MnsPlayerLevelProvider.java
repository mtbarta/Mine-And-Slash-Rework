package com.robertx22.mine_and_slash.mmorpg.compat;

import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import dev.muon.dynamic_difficulty.api.PlayerLevelProvider;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

/**
 * Provides Mine and Slash player levels to Dynamic Difficulty.
 * This allows DD to use MnS player levels for mob scaling calculations.
 */
public class MnsPlayerLevelProvider implements PlayerLevelProvider {

    @Override
    public int getPlayerLevel(@NotNull ServerPlayer player) {
        var data = Load.Unit(player);
        if (data != null) {
            // Return the internal level field directly to avoid recursion
            return data.getInternalLevel();
        }
        return 1;
    }

    @Override
    public boolean isEnabled() {
        return true; // Always enabled when this provider is registered
    }
}
