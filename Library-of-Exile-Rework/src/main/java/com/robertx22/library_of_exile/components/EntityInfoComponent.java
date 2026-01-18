package com.robertx22.library_of_exile.components;

import com.robertx22.library_of_exile.main.Ref;
import com.robertx22.library_of_exile.registry.LibAttachments;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;

public class EntityInfoComponent {

    public static final ResourceLocation RESOURCE = ResourceLocation.fromNamespaceAndPath(Ref.MODID, "entity_info");

    public static EntityInfoComponent get(LivingEntity entity) {
        return entity.getData(LibAttachments.ENTITY_INFO);
    }

    private static final String DMG_STATS = "dmg_stats";
    private static final String SPAWN_POS = "spawn_pos";
    private static final String SPAWN_REASON = "spawn";
    private static final String CUSTOM_SAVE_DATA = "custom_data";

    public EntityDmgStatsData dmgStats = new EntityDmgStatsData();
    private BlockPos spawnPos;
    public MySpawnReason spawnReason = null;
    public LivingEntity owner;

    public EntityInfoComponent(LivingEntity en) {
        this.owner = en;
    }

    public BlockPos getSpawnPos() {
        if (isSpawnInit()) {
            return spawnPos;
        }
        if (this.owner != null)
            return this.owner.blockPosition();
        return BlockPos.ZERO;
    }

    private boolean isSpawnInit() {
        return spawnPos != null && !this.spawnPos.equals(BlockPos.ZERO);
    }

    public void spawnInit(Entity entity) {
        if (isSpawnInit()) {
            this.spawnPos = entity.blockPosition();
        }
    }

    public MySpawnReason getSpawnReason() {
        return spawnReason == null ? MySpawnReason.OTHER : spawnReason;
    }

    public void setSpawnReasonOnCreate(MobSpawnType reason) {
        if (spawnReason == null) {
            spawnReason = MySpawnReason.get(reason);
        }
    }

    public EntityDmgStatsData getDamageStats() {
        return dmgStats;
    }

    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag nbt = new CompoundTag();
        try {
            if (dmgStats != null) {
                LoadSave.Save(dmgStats, nbt, DMG_STATS);
            }
            if (spawnPos != null) {
                nbt.putLong(SPAWN_POS, spawnPos.asLong());
            }

            nbt.putString(getSpawnReason().name(), SPAWN_REASON);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return nbt;
    }

    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {

        try {

            this.dmgStats = LoadSave.Load(EntityDmgStatsData.class, new EntityDmgStatsData(), nbt, DMG_STATS);
            if (dmgStats == null) {
                dmgStats = new EntityDmgStatsData();
            }
            if (nbt.contains(SPAWN_POS))
                this.spawnPos = BlockPos.of(nbt.getLong(SPAWN_POS));

            String res = nbt.getString(SPAWN_REASON);
            if (res != null && !res.isEmpty()) {
                this.spawnReason = MySpawnReason.valueOf(res);
            } else {
                this.spawnReason = MySpawnReason.OTHER;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void syncToClient(Player player) {
    }

    public String getCapIdForSyncing() {
        return "entity_info";
    }
}
