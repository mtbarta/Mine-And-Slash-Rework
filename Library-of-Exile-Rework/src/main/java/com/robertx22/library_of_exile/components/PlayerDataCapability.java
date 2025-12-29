package com.robertx22.library_of_exile.components;

import com.robertx22.library_of_exile.dimension.teleport.SavedPlayerMapTeleports;
import com.robertx22.library_of_exile.main.Ref;
import com.robertx22.library_of_exile.registry.LibAttachments;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class PlayerDataCapability {


    public static final ResourceLocation RESOURCE = new ResourceLocation(Ref.MODID, "player");

    public static PlayerDataCapability get(Player p) {
        return p.getData(LibAttachments.LIB_PLAYER_DATA.get());
    }

    private static final String MAP_TPS = "map_tps";


    transient Player player;

    public SavedPlayerMapTeleports mapTeleports = new SavedPlayerMapTeleports();

    public PlayerDataCapability(Player player) {
        this.player = player;
    }


    public DelayedTeleportData delayedTeleportData = null;

    public CompoundTag serializeNBT() {

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

    public void deserializeNBT(CompoundTag nbt) {

        try {
            this.mapTeleports = LoadSave.Load(SavedPlayerMapTeleports.class, new SavedPlayerMapTeleports(), nbt, MAP_TPS);
            if (mapTeleports == null) {
                mapTeleports = new SavedPlayerMapTeleports();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void syncToClient(Player player) {
        // dont sync backpacks to client
        //  Packets.sendToClient(player, new SyncPlayerCapToClient(player, this.getCapIdForSyncing()));
    }


    public String getCapIdForSyncing() {
        return "player_data";
    }

}
