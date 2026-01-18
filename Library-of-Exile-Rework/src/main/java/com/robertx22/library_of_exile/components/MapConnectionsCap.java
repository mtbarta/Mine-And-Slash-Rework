package com.robertx22.library_of_exile.components;

import com.google.gson.JsonSyntaxException;
import com.robertx22.library_of_exile.main.Ref;
import com.robertx22.library_of_exile.registry.LibAttachments;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class MapConnectionsCap implements ICap {

    public Level world;

    public static final ResourceLocation RESOURCE = ResourceLocation.fromNamespaceAndPath(Ref.MODID, "map_connections");

    public MapConnectionsCap(Level world) {
        this.world = world;
    }

    public static MapConnectionsCap get(Level entity) {
        var overworld = entity.getServer().overworld();
        return overworld.getData(LibAttachments.MAP_CONNECTIONS);
    }

    public AllMapConnectionData data = new AllMapConnectionData();

    @Override
    public String getCapIdForSyncing() {
        return "map_connections";
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        var nbt = new CompoundTag();
        try {
            LoadSave.Save(data, nbt, "data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        try {
            this.data = LoadSave.loadOrBlank(AllMapConnectionData.class, new AllMapConnectionData(), nbt, "data",
                    new AllMapConnectionData());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }
}
