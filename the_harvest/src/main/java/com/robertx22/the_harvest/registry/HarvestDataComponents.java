package com.robertx22.the_harvest.registry;

import com.mojang.serialization.Codec;
import com.robertx22.library_of_exile.components.DataComponentCodecs;
import com.robertx22.the_harvest.item.HarvestItemMapData;
import com.robertx22.the_harvest.main.HarvestMain;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registry for all custom Data Components used on ItemStacks in the_harvest
 * mod.
 * Uses GSON-based codecs for backward compatibility.
 */
public class HarvestDataComponents {

    public static final DeferredRegister.DataComponents REGISTRY = DeferredRegister
            .createDataComponents(HarvestMain.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<HarvestItemMapData>> HARVEST_MAP_DATA = register(
            "harvest_map_data", HarvestItemMapData.class, HarvestItemMapData::new);

    /**
     * Register a data component type with automatic GSON-based codecs.
     */
    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(
            String name,
            Class<T> clazz,
            java.util.function.Supplier<T> constructor) {

        return REGISTRY.registerComponentType(
                name,
                builder -> {
                    Codec<T> codec = DataComponentCodecs.createGsonCodec(clazz);
                    StreamCodec<ByteBuf, T> streamCodec = DataComponentCodecs.createGsonStreamCodec(
                            clazz,
                            constructor);

                    return builder
                            .persistent(codec)
                            .networkSynchronized(streamCodec);
                });
    }
}
