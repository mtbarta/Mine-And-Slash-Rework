package com.robertx22.dungeon_realm.registry;

import com.mojang.serialization.Codec;
import com.robertx22.dungeon_realm.item.DungeonItemMapData;
import com.robertx22.dungeon_realm.item.relic.RelicItemData;
import com.robertx22.dungeon_realm.main.DungeonMain;
import com.robertx22.library_of_exile.components.DataComponentCodecs;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registry for all custom Data Components used on ItemStacks in dungeon_realm.
 */
public class DungeonDataComponentTypes {

    public static final DeferredRegister.DataComponents REGISTRY = DeferredRegister
            .createDataComponents(DungeonMain.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<DungeonItemMapData>> DUNGEON_MAP_DATA = register(
            "dungeon_map_data",
            DungeonItemMapData.class,
            DungeonItemMapData::new);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<RelicItemData>> RELIC_DATA = register(
            "relic_data",
            RelicItemData.class,
            RelicItemData::new);

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
