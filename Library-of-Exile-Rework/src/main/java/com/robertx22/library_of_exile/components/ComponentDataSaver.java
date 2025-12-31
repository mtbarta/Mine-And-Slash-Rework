package com.robertx22.library_of_exile.components;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

/**
 * Data Component-based replacement for ItemstackDataSaver.
 * Provides similar API but uses Minecraft 1.20.5+ Data Components instead of
 * NBT.
 * 
 * @param <T> The data type to store on ItemStacks
 */
public class ComponentDataSaver<T> {

    private final String id;
    private final Class<T> clazz;
    private final Supplier<T> constructor;
    private final Supplier<DataComponentType<T>> componentTypeSupplier;

    /**
     * Create a new ComponentDataSaver.
     * 
     * @param id                    Unique identifier for this component
     * @param clazz                 Class of the data type
     * @param constructor           Supplier for creating new instances
     * @param componentTypeSupplier Supplier for the DataComponentType (typically
     *                              from a DeferredRegister)
     */
    public ComponentDataSaver(
            String id,
            Class<T> clazz,
            Supplier<T> constructor,
            Supplier<DataComponentType<T>> componentTypeSupplier) {
        this.id = id;
        this.clazz = clazz;
        this.constructor = constructor;
        this.componentTypeSupplier = componentTypeSupplier;
    }

    /**
     * Create a ComponentDataSaver with auto-generated codecs using GSON.
     * This provides backward compatibility with existing data classes.
     * 
     * @param id           Unique identifier
     * @param clazz        Data class
     * @param constructor  Instance constructor
     * @param codecBuilder Builder that will create and register the
     *                     DataComponentType
     */
    public static <T> ComponentDataSaver<T> createWithGsonCodec(
            String id,
            Class<T> clazz,
            Supplier<T> constructor,
            DataComponentCodecs.ComponentTypeBuilder<T> codecBuilder) {

        // Generate GSON-based codecs
        Codec<T> codec = DataComponentCodecs.createGsonCodec(clazz);
        StreamCodec<ByteBuf, T> streamCodec = DataComponentCodecs.createGsonStreamCodec(clazz, constructor);

        // Register the component type
        Supplier<DataComponentType<T>> typeSupplier = () -> codecBuilder.build(codec, streamCodec);

        return new ComponentDataSaver<>(id, clazz, constructor, typeSupplier);
    }

    /**
     * Check if the stack has this component.
     */
    public boolean has(ItemStack stack) {
        return stack != null && stack.has(getComponentType());
    }

    /**
     * Remove this component from the stack.
     */
    public void removeFrom(ItemStack stack) {
        if (stack != null) {
            stack.remove(getComponentType());
        }
    }

    /**
     * Load the component data from the stack.
     * Returns null if the stack is null or doesn't have the component.
     */
    public T loadFrom(ItemStack stack) {
        if (stack == null) {
            return null;
        }
        return stack.get(getComponentType());
    }

    /**
     * Get the component data from the stack, or return a default value if not
     * present.
     */
    public T getOrDefault(ItemStack stack, T defaultValue) {
        if (stack == null) {
            return defaultValue;
        }
        return stack.getOrDefault(getComponentType(), defaultValue);
    }

    /**
     * Get the component data from the stack, or create a new instance if not
     * present.
     */
    public T getOrCreate(ItemStack stack) {
        if (stack == null) {
            return constructor.get();
        }
        T existing = stack.get(getComponentType());
        if (existing != null) {
            return existing;
        }
        return constructor.get();
    }

    /**
     * Save the data to the stack.
     * Note: Components are immutable and shared between stacks, so this sets a new
     * component instance.
     */
    public void saveTo(ItemStack stack, T data) {
        if (stack != null && data != null) {
            stack.set(getComponentType(), data);
        }
    }

    /**
     * Save data to stack (type-erased version for compatibility).
     */
    @SuppressWarnings("unchecked")
    public void saveToObject(ItemStack stack, Object object) {
        if (stack != null && object != null) {
            if (clazz.isInstance(object)) {
                stack.set(getComponentType(), (T) object);
            }
        }
    }

    /**
     * Get the component type. Lazy loads from supplier.
     */
    private DataComponentType<T> getComponentType() {
        return componentTypeSupplier.get();
    }

    /**
     * Get the unique identifier for this saver.
     */
    public String getId() {
        return id;
    }

    /**
     * Get the data class.
     */
    public Class<T> getClazz() {
        return clazz;
    }

    /**
     * Get the constructor supplier.
     */
    public Supplier<T> getConstructor() {
        return constructor;
    }
}
