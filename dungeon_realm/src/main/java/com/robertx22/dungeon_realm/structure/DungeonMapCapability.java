package com.robertx22.dungeon_realm.structure;

import com.google.gson.JsonSyntaxException;
import com.robertx22.dungeon_realm.main.DungeonMain;
import com.robertx22.dungeon_realm.registry.DungeonAttachments;
import com.robertx22.library_of_exile.dimension.MapDataFinder;
import com.robertx22.library_of_exile.dimension.MapDimensionInfo;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DungeonMapCapability {

    public Level world;

    public static final ResourceLocation RESOURCE = new ResourceLocation(DungeonMain.MODID, "world_data");

    public DungeonMapCapability(Level world) {
        this.world = world;
    }

    public static DungeonMapCapability get(Level entity) {
        return entity.getServer().overworld().getData(DungeonAttachments.DUNGEON_MAP);
    }

    public static DungeonMapCapability getFromServer() {
        return get(ServerLifecycleHooks.getCurrentServer().overworld());
    }


    public DungeonWorldData data = new DungeonWorldData();

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
            this.data = LoadSave.loadOrBlank(DungeonWorldData.class, new DungeonWorldData(), nbt, "data", new DungeonWorldData());


        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

    }

    public static MapDataFinder<DungeonMapData> DATA_GETTER = new MapDataFinder<>() {
        @Override
        public DungeonMapData getData(Pos pos) {
            return get(pos.level).data.data.getData(this.getInfo().structure, pos.pos);
        }

        @Override
        public MapDimensionInfo getInfo() {
            return DungeonMain.MAP;
        }

    };
}
