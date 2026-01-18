package com.robertx22.library_of_exile.main;

import com.robertx22.library_of_exile.components.EntityInfoComponent;
import com.robertx22.library_of_exile.components.PlayerCapabilities;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.IModBusEvent;

import java.util.function.Consumer;

public class ApiForgeEvents {

    // Store the mod event bus for registering mod bus events
    private static IEventBus modEventBus;

    public static void setModEventBus(IEventBus bus) {
        modEventBus = bus;
    }

    public static <T extends Event> void registerForgeEvent(Class<T> clazz, Consumer<T> event, EventPriority priority) {
        if (IModBusEvent.class.isAssignableFrom(clazz) || clazz.isAssignableFrom(IModBusEvent.class)) {
            if (modEventBus != null) {
                modEventBus.addListener(priority, event);
            } else {
                // Fallback for game events that may be misidentified
                NeoForge.EVENT_BUS.addListener(priority, event);
            }
        } else {
            NeoForge.EVENT_BUS.addListener(priority, event);
        }
    }

    public static <T extends Event> void registerForgeEvent(Class<T> clazz, Consumer<T> event) {
        registerForgeEvent(clazz, event, EventPriority.NORMAL);
    }

    public static void register() {

        // LivingAttackEvent is now LivingIncomingDamageEvent in NeoForge 1.21
        // This event fires before damage calculation - allows cancellation
        registerForgeEvent(LivingIncomingDamageEvent.class, event -> {
            ExileEvents.OnDamageEntity after = ExileEvents.DAMAGE_BEFORE_CALC.callEvents(
                    new ExileEvents.OnDamageEntity(event.getSource(), event.getAmount(), event.getEntity()));
            if (after.canceled) {
                event.setCanceled(true);
            }
            // Update the amount if modified by event handlers
            event.setAmount(after.damage);
        }, EventPriority.HIGHEST);

        // LivingDamageEvent.Pre is now the event for final damage before application
        registerForgeEvent(LivingDamageEvent.Pre.class, event -> {
            ExileEvents.OnDamageEntity after = ExileEvents.DAMAGE_AFTER_CALC.callEvents(
                    new ExileEvents.OnDamageEntity(event.getSource(), event.getOriginalDamage(), event.getEntity()));
            event.setNewDamage(after.damage);
        }, EventPriority.LOWEST);

        registerForgeEvent(EntityJoinLevelEvent.class, event -> {
            if (event.getEntity() instanceof LivingEntity en) {
                EntityInfoComponent.get(en).spawnInit(en);
            }
        });

        registerForgeEvent(EntityTickEvent.Post.class, event -> {
            if (!(event.getEntity() instanceof LivingEntity))
                return;
            LivingEntity entity = (LivingEntity) event.getEntity();
            if (entity.tickCount == 20) {
                EntityInfoComponent.get(entity).spawnInit(entity);
            }
            ExileEvents.LIVING_ENTITY_TICK.callEvents(new ExileEvents.OnEntityTick(entity));
        });

        registerForgeEvent(LivingDeathEvent.class, event -> {
            if (event.getEntity() instanceof Player == false && event.getSource()
                    .getEntity() instanceof LivingEntity) {
                ExileEvents.MOB_DEATH
                        .callEvents(new ExileEvents.OnMobDeath(event.getEntity(), (LivingEntity) event.getSource()
                                .getEntity()));
            }
        });

        registerForgeEvent(PlayerEvent.Clone.class, event -> {
            PlayerCapabilities.saveAllOnDeath(event);
            PlayerCapabilities.syncAllToClient(event.getEntity());
        });

        registerForgeEvent(ServerStartedEvent.class, event -> {
            CommonInit.onDatapacksReloaded();
        });

        registerForgeEvent(AddReloadListenerEvent.class, event -> {
            CommonInit.onDatapacksReloaded();
        });
    }
}