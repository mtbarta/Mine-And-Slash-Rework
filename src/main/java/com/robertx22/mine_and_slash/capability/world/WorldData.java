package com.robertx22.mine_and_slash.capability.world;

import com.robertx22.dungeon_realm.main.DungeonMain;
import com.robertx22.library_of_exile.dimension.MapDataFinder;
import com.robertx22.library_of_exile.dimension.MapDimensionInfo;
import com.robertx22.library_of_exile.utils.LoadSave;
import com.robertx22.library_of_exile.components.ICap;
import com.robertx22.mine_and_slash.maps.MapData;
import com.robertx22.mine_and_slash.maps.MnsMapDataHolder;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashAttachments;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WorldData implements ICap {

    public static final ResourceLocation RESOURCE = ResourceLocation.fromNamespaceAndPath(SlashRef.MODID, "world");

    public static WorldData get(Level level) {
        if (level.isClientSide) {
            return new WorldData(level);
        }

        return level.getServer().overworld().getData(SlashAttachments.WORLD_DATA);
    }

    private static final String MAP = "mapdata";

    transient Level level;

    public MnsMapDataHolder map = new MnsMapDataHolder();

    public WorldData(Level level) {
        this.level = level;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {

        CompoundTag nbt = new CompoundTag();

        LoadSave.Save(map, nbt, MAP);

        return nbt;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {

        this.map = loadOrBlank(MnsMapDataHolder.class, new MnsMapDataHolder(), nbt, MAP, new MnsMapDataHolder());

    }

    public static <OBJ> OBJ loadOrBlank(Class theclass, OBJ newobj, CompoundTag nbt, String loc, OBJ blank) {
        try {
            OBJ data = LoadSave.Load(theclass, newobj, nbt, loc);
            if (data == null) {
                return blank;
            } else {
                return data;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blank;
    }

    @Override
    public String getCapIdForSyncing() {
        return "world_data";
    }

    public static MapDataFinder<MapData> DATA_GETTER = new MapDataFinder<>() {
        @Override
        public MapData getData(Pos pos) {
            return get(pos.level).map.getData(this.getInfo().structure, pos.pos);
        }

        @Override
        public MapDimensionInfo getInfo() {
            return DungeonMain.MAP;
        }

    };
}
