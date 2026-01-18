package com.robertx22.library_of_exile.config.map_dimension;

import com.robertx22.library_of_exile.dimension.MapChunkGenEvent;
import com.robertx22.library_of_exile.dimension.MapChunkGens;
import com.robertx22.library_of_exile.dimension.MapDimensionInfo;
import com.robertx22.library_of_exile.dimension.MapDimensions;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;

public class MapRegisterBuilder {

    public MapDimensionInfo info;

    public MapRegisterBuilder(MapDimensionInfo info) {
        this.info = info;
    }

    // In NeoForge 1.21, modEventBus must be passed from the mod constructor
    public MapRegisterBuilder chunkGenerator(IEventBus modEventBus, EventConsumer<MapChunkGenEvent> event,
            ResourceLocation chunkGenId) {
        MapChunkGens.registerMapChunkGenerator(modEventBus, chunkGenId, event);
        return this;
    }

    public void build() {
        MapDimensions.register(info);
    }
}
