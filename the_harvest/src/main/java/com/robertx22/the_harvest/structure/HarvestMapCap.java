package com.robertx22.the_harvest.structure;

import com.robertx22.library_of_exile.utils.LoadSave;
import com.robertx22.the_harvest.main.HarvestMain;
import com.robertx22.the_harvest.registry.HarvestAttachments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class HarvestMapCap {

    public Level world;

    public static final ResourceLocation RESOURCE = ResourceLocation.fromNamespaceAndPath(HarvestMain.MODID, "world_data");
    

    public HarvestMapCap(Level world) {
        this.world = world;
    }

    public static HarvestMapCap get(Level entity) {
        return entity.getServer().overworld().getData(HarvestAttachments.HARVEST_MAP);
    }
    
    public static HarvestMapCap getFromServer() {
        return get(ServerLifecycleHooks.getCurrentServer().overworld());
    }


    public HarvestWorldData data = new HarvestWorldData();

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
            this.data = LoadSave.loadOrBlank(HarvestWorldData.class, new HarvestWorldData(), nbt, "data", new HarvestWorldData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
