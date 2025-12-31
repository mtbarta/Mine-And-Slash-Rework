package com.robertx22.library_of_exile.components;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

/**
 * Central registry for all ComponentDataSaver instances.
 * Similar to AllItemStackSavers but for Data Components.
 */
public class AllComponentSavers {

    /**
     * All registered component savers.
     * Used for lookup and iteration.
     */
    public static final List<ComponentDataSaver<?>> ALL = new ArrayList<>();

    /**
     * Register a component saver.
     * This should be called during mod initialization.
     */
    public static <T> void register(ComponentDataSaver<T> saver) {
        if (ALL.stream().noneMatch(s -> s.getId().equals(saver.getId()))) {
            ALL.add(saver);
        }
    }

    /**
     * Find a saver by its ID.
     */
    public static ComponentDataSaver<?> getById(String id) {
        return ALL.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Find savers that can handle a specific class.
     */
    @SuppressWarnings("unchecked")
    public static <T> List<ComponentDataSaver<? extends T>> getByClass(Class<T> clazz) {
        List<ComponentDataSaver<? extends T>> result = new ArrayList<>();
        for (ComponentDataSaver<?> saver : ALL) {
            if (clazz.isAssignableFrom(saver.getClazz())) {
                result.add((ComponentDataSaver<? extends T>) saver);
            }
        }
        return result;
    }
}
