package com.robertx22.ancient_obelisks.registry;

import com.mojang.serialization.Codec;
import com.robertx22.ancient_obelisks.item.ObeliskItemMapData;
import com.robertx22.ancient_obelisks.main.ObelisksMain;
import com.robertx22.library_of_exile.components.DataComponentCodecs;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registry for all custom Data Components used on ItemStacks in the
 * ancient_obelisks mod.
 * Uses GSON-based codecs for backward compatibility.
 */
public class ObeliskDataComponents {

    public static final DeferredRegister.DataComponents REGISTRY = DeferredRegister
            .createDataComponents(ObelisksMain.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ObeliskItemMapData>> OBELISK_MAP_DATA = register(
            "obelisk_map_data", ObeliskItemMapData.class, ObeliskItemMapData::new);

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
