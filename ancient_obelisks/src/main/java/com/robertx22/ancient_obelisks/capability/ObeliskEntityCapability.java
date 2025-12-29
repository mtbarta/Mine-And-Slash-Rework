package com.robertx22.ancient_obelisks.capability;

import com.google.gson.JsonSyntaxException;
import com.robertx22.ancient_obelisks.main.ObelisksMain;
import com.robertx22.ancient_obelisks.registry.ObeliskAttachments;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ObeliskEntityCapability implements INBTSerializable<CompoundTag> {
    public static final ResourceLocation RESOURCE = new ResourceLocation(ObelisksMain.MODID, "entity_data");

    public static ObeliskEntityCapability get(LivingEntity entity) {
        return entity.getData(ObeliskAttachments.OBELISK_ENTITY);
    }

    LivingEntity entity;

    public ObeliskEntityCapability(LivingEntity entity) {
        this.entity = entity;
    }

    public ObeliskEntityData data = new ObeliskEntityData();

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
            this.data = LoadSave.loadOrBlank(ObeliskEntityData.class, new ObeliskEntityData(), nbt, "data", new ObeliskEntityData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void syncToClient(Player player) {

    }

    public String getCapIdForSyncing() {
        return "obelisk_entity_info";
    }
}
