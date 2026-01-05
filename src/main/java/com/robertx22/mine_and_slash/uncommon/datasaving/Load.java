package com.robertx22.mine_and_slash.uncommon.datasaving;

import com.robertx22.mine_and_slash.capability.chunk.ChunkCap;
import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.event_hooks.my_events.CachedEntityStats;
import com.robertx22.mine_and_slash.capability.player.PlayerBackpackData;
import com.robertx22.mine_and_slash.capability.player.PlayerData;
import com.robertx22.mine_and_slash.capability.world.WorldData;
import com.robertx22.mine_and_slash.database.data.spells.components.Spell;
import com.robertx22.mine_and_slash.maps.MapData;
import com.robertx22.mine_and_slash.saveclasses.unit.Unit;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashAttachments;
import org.jetbrains.annotations.Nullable;

/**
 * Central class for accessing entity/player/world data attachments.
 * 
 * IMPORTANT DESIGN NOTE:
 * NeoForge's attachment system creates default instances via factories that
 * don't have access
 * to the holder entity. This means all attachments are initially created with
 * null entity references.
 * 
 * To handle this:
 * 1. All accessor methods validate the entity is not null
 * 2. After retrieving the attachment, we set the entity reference if it's
 * missing
 * 3. Callers should handle null returns gracefully
 * 
 * This ensures both server and client code works correctly during:
 * - Initial world creation
 * - NBT deserialization
 * - Client/server sync
 * - Dimension changes
 */
public class Load {

    /**
     * Gets the spell-specific unit stats, or falls back to base unit stats.
     * Returns null if entity is null.
     */
    @Nullable
    public static Unit getSpellUnit(Entity entity, Spell spell) {
        if (entity == null) {
            return null;
        }
        if (spell != null && entity instanceof Player p) {
            PlayerData pd = player(p);
            if (pd != null && pd.canHaveSpellUnit(spell)) {
                return pd.getSpellUnitStats(spell);
            }
        }
        EntityData data = Unit(entity);
        return data != null ? data.getUnit() : null;
    }

    /**
     * Gets EntityData for any entity (players, mobs, etc).
     * Ensures the entity reference is set in the data.
     * Returns null if entity is null.
     */
    @Nullable
    public static EntityData Unit(Entity entity) {
        if (entity == null) {
            return null;
        }
        EntityData data = entity.getData(SlashAttachments.ENTITY_DATA);
        if (data == null) {
            return null;
        }
        // NeoForge attachments are created with null entity, so we must set it here
        if (data.getEntity() == null && entity instanceof LivingEntity le) {
            data.setEntity(le);
        }
        // Ensure equipmentCache is initialized (setEntity should handle this, but
        // double-check)
        if (data.equipmentCache == null && entity instanceof LivingEntity le) {
            data.equipmentCache = new CachedEntityStats(le);
        }
        return data;
    }

    /**
     * Gets PlayerData for a player.
     * Ensures the player reference is set in the data.
     * Returns null if player is null.
     * 
     * Client-side note: Minecraft.getInstance().player may be null during:
     * - Early loading
     * - Between dimension changes
     * - During certain render phases
     * Always check for null return!
     */
    @Nullable
    public static PlayerData player(Player player) {
        if (player == null) {
            return null;
        }
        PlayerData data = player.getData(SlashAttachments.PLAYER_DATA);

        if (data == null) {
            return null;
        }

        // NeoForge attachments are created with null player, so we must set it here
        if (data.player == null) {
            data.player = player;
        }
        return data;
    }

    /**
     * Gets PlayerBackpackData for a player.
     * Returns null if player is null.
     */
    @Nullable
    public static PlayerBackpackData backpacks(Player player) {
        if (player == null) {
            return null;
        }
        return player.getData(SlashAttachments.PLAYER_BACKPACK_DATA);
    }

    /**
     * Gets WorldData for the overworld.
     * Returns null if level or server is null.
     */
    @Nullable
    public static WorldData worldData(Level l) {
        if (l == null || l.getServer() == null) {
            return null;
        }
        return l.getServer().overworld().getData(SlashAttachments.WORLD_DATA);
    }

    /**
     * Gets MapData at a specific position.
     * Returns null if not found or on error.
     */
    @Nullable
    public static MapData mapAt(Level l, BlockPos pos) {
        if (l == null || pos == null) {
            return null;
        }
        try {
            return WorldUtils.ifMapData(l, pos).get();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets ChunkCap for a chunk.
     * Returns null if chunk is null.
     */
    @Nullable
    public static ChunkCap chunkData(LevelChunk c) {
        if (c == null) {
            return null;
        }
        return c.getData(SlashAttachments.CHUNK_CAP);
    }

}
