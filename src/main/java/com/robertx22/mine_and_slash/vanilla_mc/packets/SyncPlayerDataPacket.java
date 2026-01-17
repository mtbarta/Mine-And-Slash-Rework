package com.robertx22.mine_and_slash.vanilla_mc.packets;

import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import com.robertx22.mine_and_slash.capability.player.PlayerData;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashAttachments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

/**
 * Syncs the mod's PlayerData from server to client.
 * This packet is needed because the library's SyncPlayerCapToClient doesn't
 * know about the mod's PlayerData attachment.
 */
public class SyncPlayerDataPacket extends MyPacket<SyncPlayerDataPacket> {

    public CompoundTag nbt;

    public SyncPlayerDataPacket() {
    }

    public SyncPlayerDataPacket(Player player) {
        PlayerData data = player.getData(SlashAttachments.PLAYER_DATA);
        this.nbt = data.serializeNBT(player.registryAccess());
    }

    @Override
    public ResourceLocation getIdentifier() {
        return ResourceLocation.fromNamespaceAndPath(SlashRef.MODID, "sync_player_data");
    }

    @Override
    public void loadFromData(RegistryFriendlyByteBuf tag) {
        nbt = tag.readNbt();
    }

    @Override
    public void saveToData(RegistryFriendlyByteBuf tag) {
        tag.writeNbt(nbt);
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        try {
            Player player = ctx.getPlayer();
            if (player.level().isClientSide) {
                PlayerData data = player.getData(SlashAttachments.PLAYER_DATA);
                if (data != null && nbt != null) {
                    data.deserializeNBT(player.registryAccess(), nbt);
                    data.player = player; // Ensure player reference is set
                    data.cachedStats.setAllDirty();
                    // print player data
                    System.out.println("[SyncPlayerDataPacket] Client received and loaded PlayerData");
                    System.out.println("[SyncPlayerDataPacket] PlayerData: " + data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public MyPacket<SyncPlayerDataPacket> newInstance() {
        return new SyncPlayerDataPacket();
    }
}
