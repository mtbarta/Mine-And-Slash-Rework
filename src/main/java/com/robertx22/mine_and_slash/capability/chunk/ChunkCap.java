package com.robertx22.mine_and_slash.capability.chunk;

import com.robertx22.library_of_exile.components.ICap;
import com.robertx22.library_of_exile.components.LibChunkCap;
import com.robertx22.library_of_exile.main.ExileLog;
import com.robertx22.library_of_exile.utils.LoadSave;
import com.robertx22.mine_and_slash.database.data.profession.all.Professions;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashAttachments;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChunkCap implements ICap {

    public static final ResourceLocation RESOURCE = ResourceLocation.fromNamespaceAndPath(SlashRef.MODID, "chunk_data");

    public static ChunkCap get(LevelChunk chunk) {
        return chunk.getData(SlashAttachments.CHUNK_CAP);
    }

    transient LevelChunk chunk;

    public boolean generatedMobs = false;
    public boolean generatedTerrain = false;

    public ChunkCap(LevelChunk chunk) {
        this.chunk = chunk;
    }

    List<CompoundTag> savedMobs = new ArrayList<>();

    public void tryLoadMobs(Level world) {
        if (!savedMobs.isEmpty()) {
            try {

                var saved = savedMobs.stream().toList();
                savedMobs.clear();
                mobIds.clear();

                for (CompoundTag nbt : saved) {
                    var en = EntityType.loadEntityRecursive(nbt, world, x -> x);
                    world.addFreshEntity(en);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public List<UUID> mobIds = new ArrayList<>();

    public void trySaveMob(LivingEntity en, net.minecraft.core.HolderLookup.Provider provider) {

        if (en instanceof Player) {
            return;
        }

        if (savedMobs.size() > 30) {
            if (MMORPG.RUN_DEV_TOOLS) {
                ExileLog.get().warn("Saved too many mobs in 1 chunk, stopping just in case");
            }
            return;
        }
        if (mobIds.contains(en.getUUID())) {
            return;
        }
        mobIds.add(en.getUUID());

        var nbt = en.serializeNBT(provider);

        savedMobs.add(nbt);
    }

    @Override
    public CompoundTag serializeNBT(net.minecraft.core.HolderLookup.Provider provider) {

        CompoundTag nbt = new CompoundTag();

        try {
            nbt.putBoolean("gen", generatedTerrain);
            nbt.putBoolean("genmobs", generatedMobs);

            nbt.putInt("mobs", savedMobs.size());

            for (int i = 0; i < savedMobs.size(); i++) {
                nbt.put(i + "", savedMobs.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
            ExileLog.get().warn("Mob Unloading/Loading Error");
        }

        return nbt;
    }

    @Override
    public void deserializeNBT(net.minecraft.core.HolderLookup.Provider provider, CompoundTag nbt) {

        try {
            this.generatedTerrain = nbt.getBoolean("gen");
            this.generatedMobs = nbt.getBoolean("genmobs");

            int mobs = nbt.getInt("mobs");

            this.savedMobs = new ArrayList<>();
            this.mobIds = new ArrayList<>();

            for (int i = 0; i < mobs; i++) {
                var mobnbt = nbt.getCompound(i + "");
                var id = mobnbt.getUUID("UUID");
                if (id != null) {
                    savedMobs.add(mobnbt);
                    mobIds.add(id);
                }
                // todo test if game still freezes
            }
        } catch (Exception e) {
            e.printStackTrace();
            ExileLog.get().warn("Mob Unloading/Loading Error");
        }

    }

    @Override
    public String getCapIdForSyncing() {
        return "chunk_data";
    }

}
