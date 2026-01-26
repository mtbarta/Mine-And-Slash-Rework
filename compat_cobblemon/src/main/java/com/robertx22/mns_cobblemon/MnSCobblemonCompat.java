package com.robertx22.mns_cobblemon;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.robertx22.mns_cobblemon.abilities.KobblemonAbilities;
import com.robertx22.mns_cobblemon.core.KobblemonAffixes;
import com.robertx22.mns_cobblemon.core.KobblemonGearSlots;
import com.robertx22.mns_cobblemon.core.KobblemonGearTypes;
import com.robertx22.mns_cobblemon.core.stats.KobblemonStats;
import com.robertx22.mns_cobblemon.events.PokemonInteractionHandler;
import com.robertx22.mns_cobblemon.gui.KobblemonContainer;
import com.robertx22.mns_cobblemon.gui.KobblemonEquipmentScreen;
import com.robertx22.mns_cobblemon.items.KobblemonItems;
import com.robertx22.mns_cobblemon.network.PacketOpenKobblemonGui;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import java.util.function.Supplier;
import com.robertx22.mns_cobblemon.capability.KobblemonData;

@Mod(MnSCobblemonCompat.MODID)
public class MnSCobblemonCompat {
    public static final String MODID = "mns_cobblemon";

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, MODID);
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister
            .create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, MODID);

    public static final Supplier<AttachmentType<KobblemonData>> KOBBLEMON_DATA = ATTACHMENT_TYPES.register(
            "kobblemon_data",
            () -> AttachmentType.serializable(() -> new KobblemonData(null))
                    .build());

    public MnSCobblemonCompat(IEventBus modBus) {
        // Register Items
        KobblemonItems.ITEMS.register(modBus);
        ATTACHMENT_TYPES.register(modBus);

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
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS
                .register(com.robertx22.mns_cobblemon.core.stats.PokemonStatSync.class);

        // Register Network
        modBus.addListener(this::registerNetwork);

        // Register Screens
        modBus.addListener(this::registerScreens);

        // Register Client Setup (for interaction wheel handler)
        modBus.addListener(this::onClientSetup);
        modBus.addListener(this::commonSetup);
    }

    private void commonSetup(final net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent event) {
        // Register Exile Registries (GearSlots, GearTypes, Affixes, Stats, Abilities)
        event.enqueueWork(() -> {
            new KobblemonGearSlots().registerAll();
            new KobblemonGearTypes().registerAll();
            new KobblemonAffixes().registerAll();
            new KobblemonStats().registerAll();
            new KobblemonAbilities().registerAll();
            new com.robertx22.mns_cobblemon.core.spells.KobblemonSpells().registerAll();

            // Link Pokemon Summon to Basic Attack
            var spell = com.robertx22.mine_and_slash.database.registry.ExileDB.Spells()
                    .get(com.robertx22.mine_and_slash.aoe_data.database.spells.schools.SummonSpells.SUMMON_POKEMON);
            if (spell != null) {
                spell.getConfig().setSummonBasicAttack(
                        com.robertx22.mns_cobblemon.core.spells.KobblemonSpells.POKEMON_BASIC_ATTACK);
            }
        });
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            // Register interaction handlers
            PokemonInteractionHandler.register();
            com.robertx22.mns_cobblemon.events.PokemonRecruitmentHandler.register();
        });
    }

    private void registerNetwork(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(MODID);
        // Changed to playToServer: client sends packet to server to open equipment menu
        registrar.playToServer(PacketOpenKobblemonGui.TYPE, PacketOpenKobblemonGui.STREAM_CODEC,
                PacketOpenKobblemonGui::handle);
        registrar.playToServer(com.robertx22.mns_cobblemon.network.PacketRecruitMinion.TYPE,
                com.robertx22.mns_cobblemon.network.PacketRecruitMinion.STREAM_CODEC,
                com.robertx22.mns_cobblemon.network.PacketRecruitMinion::handle);
    }

    private void registerScreens(final RegisterMenuScreensEvent event) {
        MenuType<KobblemonContainer> type = KobblemonContainer.TYPE;
        MenuScreens.ScreenConstructor<KobblemonContainer, KobblemonEquipmentScreen> constructor = (menu, inv,
                title) -> new KobblemonEquipmentScreen(menu, inv, title);
        event.register(type, constructor);
    }
}
