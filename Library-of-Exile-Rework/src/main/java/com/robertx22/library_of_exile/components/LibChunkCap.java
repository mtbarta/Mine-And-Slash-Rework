package com.robertx22.library_of_exile.components;

import com.robertx22.library_of_exile.main.Ref;
import com.robertx22.library_of_exile.registry.LibAttachments;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.NotNull;

public class LibChunkCap {

    public static final ResourceLocation RESOURCE = ResourceLocation.fromNamespaceAndPath(Ref.MODID, "chunk_data");

    public static LibChunkCap get(LevelChunk chunk) {
        return chunk.getData(LibAttachments.LIB_CHUNK_CAP.get());
    }

    transient LevelChunk chunk;

    public MapChunkData mapGenData = new MapChunkData();

    public LibChunkCap(LevelChunk chunk) {
        this.chunk = chunk;
    }

    public CompoundTag serializeNBT() {

        CompoundTag nbt = new CompoundTag();
        try {
            LoadSave.Save(mapGenData, nbt, "map");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return nbt;
    }

    public void deserializeNBT(CompoundTag nbt) {
        try {
            this.mapGenData = LoadSave.loadOrBlank(MapChunkData.class, new MapChunkData(), nbt, "map", new MapChunkData());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
