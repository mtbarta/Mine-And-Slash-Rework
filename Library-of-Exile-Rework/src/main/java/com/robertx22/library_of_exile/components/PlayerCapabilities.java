package com.robertx22.library_of_exile.components;

import com.robertx22.library_of_exile.registry.LibAttachments;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.HashMap;
import java.util.function.Supplier;

public class PlayerCapabilities {

    static HashMap<String, Supplier<? extends AttachmentType<? extends ICap>>> caps = new HashMap<>();

    static {
        // Register default capabilities
        caps.put("player_data", LibAttachments.LIB_PLAYER_DATA);
    }

    public static ICap get(Player player, String id) {
        if (caps.containsKey(id)) {
            return player.getData(caps.get(id).get());
        }
        return null;
    }

    public static <T extends ICap> void register(Supplier<AttachmentType<T>> cap, T obj) {
        caps.put(obj.getCapIdForSyncing(), (Supplier<AttachmentType<? extends ICap>>) (Supplier<?>) cap);
    }

    public static void register(Supplier<AttachmentType<? extends ICap>> cap, String syncid) {
        caps.put(syncid, cap);
    }

    public static void syncAllToClient(Player player) {
        try {
            caps.forEach((id, type) -> {
                ICap cap = player.getData(type.get());
                if (cap != null) {
                    cap.syncToClient(player);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveAllOnDeath(net.neoforged.neoforge.event.entity.player.PlayerEvent.Clone event) {
        // NeoForge attachments handle copyOnDeath via builder.
    }
}
