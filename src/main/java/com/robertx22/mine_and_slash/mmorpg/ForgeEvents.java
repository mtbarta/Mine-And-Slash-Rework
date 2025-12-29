package com.robertx22.mine_and_slash.mmorpg;

import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.fml.event.IModBusEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.function.Consumer;

public class ForgeEvents {
    public static <T extends Event> void registerForgeEvent(Class<T> clazz, Consumer<T> event, EventPriority priority) {
        if (IModBusEvent.class.isAssignableFrom(clazz) || clazz.isAssignableFrom(IModBusEvent.class)) {
            FMLJavaModLoadingContext.get()
                    .getModEventBus()
                    .addListener(priority, event);
        } else {
            NeoForge.EVENT_BUS.addListener(priority, event);
        }
    }

    public static <T extends Event> void registerForgeEvent(Class<T> clazz, Consumer<T> event) {
        registerForgeEvent(clazz, event, EventPriority.NORMAL);
    }

}
