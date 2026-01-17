package com.robertx22.library_of_exile.components;

import com.robertx22.library_of_exile.dimension.MapDataFinder;
import com.robertx22.library_of_exile.dimension.MapDimensionInfo;
import com.robertx22.library_of_exile.dimension.MapDimensions;
import com.robertx22.library_of_exile.main.Ref;
import com.robertx22.library_of_exile.registry.LibAttachments;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class LibMapCap {

    public Level world;

    public static final ResourceLocation RESOURCE = ResourceLocation.fromNamespaceAndPath(Ref.MODID, "world_data");

    public LibMapCap(Level world) {
        this.world = world;
    }

    public static LibMapCap get(Level entity) {
        return entity.getServer().overworld().getData(LibAttachments.LIB_MAP_CAP.get());
    }

    public static LibMapCap getFromServer() {
        return get(ServerLifecycleHooks.getCurrentServer().overworld());
    }

    public LibMapDataSaver data = new LibMapDataSaver();

    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        var nbt = new CompoundTag();
        try {
            LoadSave.Save(data, nbt, "data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nbt;
    }

    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        try {
            this.data = LoadSave.loadOrBlank(LibMapDataSaver.class, new LibMapDataSaver(), nbt, "data",
                    new LibMapDataSaver());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MapDataFinder<LibMapData> DATA_GETTER = new MapDataFinder<>() {
        @Override
        public LibMapData getData(Pos pos) {
            return get(pos.level).data.getData(this.getInfo().structure, pos.pos);
        }

        @Override
        public MapDimensionInfo getInfo() {
            return MapDimensions.getInfo(ResourceLocation.parse(Ref.MODID)); // Assuming Ref.MODID is correct context.
        }
    };

    public static LibMapData getData(Level level, net.minecraft.core.BlockPos pos) {
        return DATA_GETTER.getData(new MapDataFinder.Pos(level, pos));
    }
}
