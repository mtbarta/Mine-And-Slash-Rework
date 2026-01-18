package com.robertx22.library_of_exile.utils;

import com.robertx22.library_of_exile.components.ComponentDataSaver;
import com.robertx22.library_of_exile.registry.IGUID;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class ItemstackDataSaver<T> implements IGUID {

    String id;
    Class<T> clazz;
    Supplier<T> constructor;
    ComponentDataSaver<T> componentSaver;

    public ItemstackDataSaver(String id, Class<T> clazz, Supplier<T> constructor,
            Supplier<DataComponentType<T>> componentTypeSupplier) {
        this.id = id;
        this.clazz = clazz;
        this.constructor = constructor;
        this.componentSaver = new ComponentDataSaver<>(id, clazz, constructor, componentTypeSupplier);

        if (AllItemStackSavers.ALL.stream()
                .noneMatch(x -> x.GUID()
                        .equals(GUID()))) {
            AllItemStackSavers.ALL.add(this);
        }

    }

    /**
     * Creates a new ItemstackDataSaver using GSON for serialization.
     * This provides backward compatibility for existing data classes.
     */
    public static <T> ItemstackDataSaver<T> createWithGson(String id, Class<T> clazz, Supplier<T> constructor) {
        return new ItemstackDataSaver<>(id, clazz, constructor,
                () -> com.robertx22.library_of_exile.components.DataComponentCodecs.createUnregistered(
                        com.robertx22.library_of_exile.components.DataComponentCodecs.createGsonCodec(clazz),
                        com.robertx22.library_of_exile.components.DataComponentCodecs.createGsonStreamCodec(clazz,
                                constructor)));
    }

    public Supplier<T> getConstructor() {
        return constructor;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public boolean has(ItemStack stack) {
        return componentSaver.has(stack);
    }

    public void removeFrom(ItemStack stack) {
        componentSaver.removeFrom(stack);
    }

    public T loadFrom(ItemStack stack) {
        if (stack == null) {
            return null;
        }
        return componentSaver.loadFrom(stack);
    }

    public void saveTo(ItemStack stack, T object) {
        this.saveToObject(stack, object);
    }

    public void saveToObject(ItemStack stack, Object object) {
        if (stack == null) {
            return;
        }
        componentSaver.saveToObject(stack, object);
    }

    @Override
    public String GUID() {
        return id;
    }
}
