package com.robertx22.ancient_obelisks.structure;

import com.robertx22.ancient_obelisks.main.ObelisksMain;
import com.robertx22.ancient_obelisks.registry.ObeliskAttachments;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class ObeliskMapCapability {

    public Level world;

    public static final ResourceLocation RESOURCE = new ResourceLocation(ObelisksMain.MODID, "world_data");

    public ObeliskMapCapability(Level world) {
        this.world = world;
    }

    public static ObeliskMapCapability get(Level entity) {
        return entity.getServer().overworld().getData(ObeliskAttachments.OBELISK_MAP);
    }

    public ObeliskWorldData data = new ObeliskWorldData();

    public CompoundTag serializeNBT() {
        var nbt = new CompoundTag();
        try {
            LoadSave.Save(data, nbt, "data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nbt;
    }

    public void deserializeNBT(CompoundTag nbt) {
        try {
            this.data = LoadSave.loadOrBlank(ObeliskWorldData.class, new ObeliskWorldData(), nbt, "data", new ObeliskWorldData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
