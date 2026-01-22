package com.robertx22.mns_cobblemon;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.robertx22.mns_cobblemon.abilities.KobblemonAbilities;
import com.robertx22.mns_cobblemon.core.KobblemonAffixes;
import com.robertx22.mns_cobblemon.core.KobblemonGearSlots;
import com.robertx22.mns_cobblemon.core.KobblemonGearTypes;
import com.robertx22.mns_cobblemon.core.stats.KobblemonStats;
import com.robertx22.mns_cobblemon.events.CobblemonSpawning;
import com.robertx22.mns_cobblemon.gui.KobblemonContainer;
import com.robertx22.mns_cobblemon.gui.KobblemonEquipmentScreen;
import com.robertx22.mns_cobblemon.items.KobblemonItems;
import com.robertx22.mns_cobblemon.network.PacketOpenKobblemonGui;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(MnSCobblemonCompat.MODID)
public class MnSCobblemonCompat {
    public static final String MODID = "mns_cobblemon";

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, MODID);

    public MnSCobblemonCompat(IEventBus modBus) {
        // Register Items
        KobblemonItems.ITEMS.register(modBus);

        // Register Menu Types
        MENU_TYPES.register(modBus);
        KobblemonContainer.TYPE = IMenuTypeExtension.create((id, inv, data) -> {
            int entityId = data.readInt();
            Entity entity = inv.player.level().getEntity(entityId);
            if (entity instanceof PokemonEntity pokemon) {
                return new KobblemonContainer(id, inv, pokemon);
            }
            return null; // Should not happen
        });
        MENU_TYPES.register("kobblemon_equipment", () -> KobblemonContainer.TYPE);

        // Register Network
        modBus.addListener(this::registerNetwork);

        // Register Screens
        modBus.addListener(this::registerScreens);

        // Register Exile Registries (GearSlots, GearTypes, Affixes, Stats, Abilities)
        new KobblemonGearSlots().registerAll();
        new KobblemonGearTypes().registerAll();
        new KobblemonAffixes().registerAll();
        new KobblemonStats().registerAll();
        new KobblemonAbilities().registerAll();
    }

    private void registerNetwork(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(MODID);
        registrar.playToClient(PacketOpenKobblemonGui.TYPE, PacketOpenKobblemonGui.STREAM_CODEC,
                PacketOpenKobblemonGui::handle);
    }

    private void registerScreens(final RegisterMenuScreensEvent event) {
        MenuType<KobblemonContainer> type = KobblemonContainer.TYPE;
        MenuScreens.ScreenConstructor<KobblemonContainer, KobblemonEquipmentScreen> constructor = (menu, inv,
                title) -> new KobblemonEquipmentScreen(menu, inv, title);
        event.register(type, constructor);
    }
}
