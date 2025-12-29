package com.robertx22.dungeon_realm.capability;

import com.robertx22.dungeon_realm.main.DungeonMain; // Keep DungeonMain for MODID if DungeonRef is not defined
import com.robertx22.dungeon_realm.registry.DungeonAttachments;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player; // Added as per user's snippet, though not used in the provided context

import java.util.UUID; // Added as per user's snippet, though not used in the provided context

public class DungeonEntityCapability {

    // Assuming DungeonRef.MODID is intended to replace DungeonMain.MODID.
    // If DungeonRef is not a real class, this will cause a compilation error.
    // For now, I will use DungeonMain.MODID as it exists in the original code.
    // If DungeonRef is a new class, it needs to be imported or defined.
    public static final ResourceLocation RESOURCE = new ResourceLocation(DungeonMain.MODID, "dungeon_info");

    public LivingEntity entity; // Changed 'en' to 'entity' as per user's snippet

    public DungeonEntityCapability(LivingEntity entity) {
        this.entity = entity;
    }

    public static DungeonEntityCapability get(LivingEntity entity) {
        return entity.getData(DungeonAttachments.DUNGEON_ENTITY);
    }

    public DungeonEntityData data = new DungeonEntityData();

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
            this.data = LoadSave.loadOrBlank(DungeonEntityData.class, new DungeonEntityData(), nbt, "data", new DungeonEntityData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void syncToClient(Player player) {

    }

    public String getCapIdForSyncing() {
        return "dungeon_entity_info";
    }
}
