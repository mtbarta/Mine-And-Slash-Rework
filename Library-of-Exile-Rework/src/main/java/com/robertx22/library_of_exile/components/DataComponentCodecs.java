package com.robertx22.library_of_exile.components;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.Supplier;

/**
 * Utilities for creating Codecs and StreamCodecs for Data Components.
 * Provides GSON-based codecs for backward compatibility with existing data
 * classes.
 */
public class DataComponentCodecs {

    private static final Gson GSON = new GsonBuilder().create();

    /**
     * Create a Codec for a class using GSON serialization.
     * This provides backward compatibility - existing data classes work without
     * modification.
     */
    public static <T> Codec<T> createGsonCodec(Class<T> clazz) {
        return new Codec<T>() {
            @Override
            public <D> DataResult<com.mojang.datafixers.util.Pair<T, D>> decode(DynamicOps<D> ops, D input) {
                try {
                    // Convert to JSON, then deserialize with GSON
                    String json = JsonOps.INSTANCE.getStringValue(ops.convertTo(JsonOps.INSTANCE, input))
                            .result()
                            .orElse("{}");

                    T object = GSON.fromJson(json, clazz);
                    return DataResult.success(com.mojang.datafixers.util.Pair.of(object, input));
                } catch (Exception e) {
                    return DataResult.error(() -> "Failed to decode " + clazz.getSimpleName() + ": " + e.getMessage());
                }
            }

            @Override
            public <D> DataResult<D> encode(T input, DynamicOps<D> ops, D prefix) {
                try {
                    // Serialize with GSON, then convert to target format
                    String json = GSON.toJson(input);
                    D jsonElement = JsonOps.INSTANCE.convertTo(ops, JsonOps.INSTANCE.createString(json));
                    return DataResult.success(jsonElement);
                } catch (Exception e) {
                    return DataResult.error(() -> "Failed to encode " + clazz.getSimpleName() + ": " + e.getMessage());
                }
            }
        };
    }

    /**
     * Create a StreamCodec for network serialization using GSON.
     * Data is serialized as JSON strings over the network.
     */
    public static <T> StreamCodec<ByteBuf, T> createGsonStreamCodec(Class<T> clazz, Supplier<T> defaultInstance) {
        return StreamCodec.<ByteBuf, T, String>composite(
                ByteBufCodecs.STRING_UTF8,
                obj -> GSON.toJson(obj),
                json -> {
                    try {
                        return GSON.fromJson(json, clazz);
                    } catch (Exception e) {
                        // Return default instance on error
                        return defaultInstance.get();
                    }
                });
    }

    /**
     * Functional interface for building DataComponentTypes.
     * This allows callers to handle registration themselves.
     */
    @FunctionalInterface
    public interface ComponentTypeBuilder<T> {
        DataComponentType<T> build(Codec<T> codec, StreamCodec<ByteBuf, T> streamCodec);
    }

    /**
     * Create a simple unregistered DataComponentType.
     * Note: This must be registered to a DeferredRegister before use!
     */
    public static <T> DataComponentType<T> createUnregistered(Codec<T> codec, StreamCodec<ByteBuf, T> streamCodec) {
        return DataComponentType.<T>builder()
                .persistent(codec)
                .networkSynchronized(streamCodec)
                .build();
    }
}
