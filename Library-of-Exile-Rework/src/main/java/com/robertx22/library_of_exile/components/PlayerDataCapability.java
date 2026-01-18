package com.robertx22.library_of_exile.components;

import com.robertx22.library_of_exile.dimension.teleport.SavedPlayerMapTeleports;
import com.robertx22.library_of_exile.main.Ref;
import com.robertx22.library_of_exile.registry.LibAttachments;
import com.robertx22.library_of_exile.utils.LoadSave;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class PlayerDataCapability implements ICap {

    public static final ResourceLocation RESOURCE = ResourceLocation.fromNamespaceAndPath(Ref.MODID, "player");

    public static PlayerDataCapability get(Player p) {
        PlayerDataCapability cap = p.getData(LibAttachments.LIB_PLAYER_DATA.get());
        cap.player = p;
        return cap;
    }

    private static final String MAP_TPS = "map_tps";

    transient Player player;

    public SavedPlayerMapTeleports mapTeleports = new SavedPlayerMapTeleports();

    public PlayerDataCapability(Player player) {
        this.player = player;
    }

    public DelayedTeleportData delayedTeleportData = null;

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {

        CompoundTag nbt = new CompoundTag();

        try {
            if (mapTeleports != null) {
                LoadSave.Save(mapTeleports, nbt, MAP_TPS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return nbt;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {

        try {
            this.mapTeleports = LoadSave.Load(SavedPlayerMapTeleports.class, new SavedPlayerMapTeleports(), nbt,
                    MAP_TPS);
            if (mapTeleports == null) {
                mapTeleports = new SavedPlayerMapTeleports();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void syncToClient(Player player) {
        // dont sync backpacks to client
        // Packets.sendToClient(player, new SyncPlayerCapToClient(player,
        // this.getCapIdForSyncing()));
    }

    public static final String CAP_ID = "player_data";

    public String getCapIdForSyncing() {
        return CAP_ID;
    }

}
