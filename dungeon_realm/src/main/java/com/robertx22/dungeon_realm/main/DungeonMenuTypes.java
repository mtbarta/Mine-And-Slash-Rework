package com.robertx22.dungeon_realm.main;

import com.robertx22.dungeon_realm.block_entity.MapDeviceMenu;
import com.robertx22.dungeon_realm.block_entity.MapDeviceScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DungeonMenuTypes {
    public static final DeferredHolder<MenuType<?>, MenuType<MapDeviceMenu>> MAP_DEVICE_MENU_TYPE = registerMenuType("map_device_menu", MapDeviceMenu::new);

    public static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return DungeonEntries.MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        DungeonEntries.MENUS.register(eventBus);
    }

    @Mod.EventBusSubscriber(modid = "dungeon_realm", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> MenuScreens.register(MAP_DEVICE_MENU_TYPE.get(), MapDeviceScreen::new));
        }
    }
}
