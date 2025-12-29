package com.robertx22.the_harvest.capability;

import com.robertx22.library_of_exile.utils.LoadSave;
import com.robertx22.the_harvest.main.HarvestMain;
import com.robertx22.the_harvest.registry.HarvestAttachments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class HarvestEntityCap {
    public static final ResourceLocation RESOURCE = new ResourceLocation(HarvestMain.MODID, "entity_data");


    public LivingEntity entity;

    public HarvestEntityCap(LivingEntity entity) {
        this.entity = entity;
    }

    public static HarvestEntityCap get(LivingEntity entity) {
        return entity.getData(HarvestAttachments.HARVEST_ENTITY);
    }

    public HarvestEntityData data = new HarvestEntityData();

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
            this.data = LoadSave.loadOrBlank(HarvestEntityData.class, new HarvestEntityData(), nbt, "data", new HarvestEntityData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void syncToClient(Player player) {

    }

    public String getCapIdForSyncing() {
        return "harvest_entity_info";
    }
}
