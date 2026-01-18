package com.robertx22.library_of_exile.components;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.packets.SyncPlayerCapToClient;
import net.neoforged.neoforge.common.util.INBTSerializable;

public interface ICap extends INBTSerializable<CompoundTag> {
    public String getCapIdForSyncing();

    public default void syncToClient(Player player) {
        Packets.sendToClient(player, new SyncPlayerCapToClient(player, getCapIdForSyncing()));
    }
}
