package com.robertx22.mine_and_slash.mmorpg;

import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.IModBusEvent;

import java.util.function.Consumer;

public class ForgeEvents {
    private static IEventBus modBus;

    public static void setModBus(IEventBus bus) {
        modBus = bus;
    }

    public static <T extends Event> void registerForgeEvent(Class<T> clazz, Consumer<T> event, EventPriority priority) {
        if (IModBusEvent.class.isAssignableFrom(clazz) || clazz.isAssignableFrom(IModBusEvent.class)) {
            modBus.addListener(priority, event);
        } else {
            NeoForge.EVENT_BUS.addListener(priority, event);
        }
    }

    public static <T extends Event> void registerForgeEvent(Class<T> clazz, Consumer<T> event) {
        registerForgeEvent(clazz, event, EventPriority.NORMAL);
    }

}
