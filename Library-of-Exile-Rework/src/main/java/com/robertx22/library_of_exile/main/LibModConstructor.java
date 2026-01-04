package com.robertx22.library_of_exile.main;

import com.robertx22.library_of_exile.database.affix.LibAffixesHolder;
import com.robertx22.library_of_exile.database.extra_map_content.LibMapContents;
import com.robertx22.library_of_exile.database.init.LibDatabase;
import com.robertx22.library_of_exile.database.league.LibLeagues;
import com.robertx22.library_of_exile.database.map_data_block.LibMapDataBlocks;
import com.robertx22.library_of_exile.database.map_finish_rarity.LibMapFinishRarities;
import com.robertx22.library_of_exile.database.mob_list.LibMobLists;
import com.robertx22.library_of_exile.database.relic.affix.LibRelicAffixes;
import com.robertx22.library_of_exile.database.relic.relic_rarity.LibRelicRarities;
import com.robertx22.library_of_exile.database.relic.relic_type.LibRelicTypes;
import com.robertx22.library_of_exile.database.relic.stat.LibRelicStats;
import com.robertx22.library_of_exile.registry.ExileRegistryEventClass;
import com.robertx22.library_of_exile.registry.helpers.ExileKeyHolder;
import com.robertx22.library_of_exile.registry.helpers.OrderedModConstructor;
import com.robertx22.orbs_of_crafting.register.Modifications;
import com.robertx22.orbs_of_crafting.register.OrbEdits;
import com.robertx22.orbs_of_crafting.register.Orbs;
import com.robertx22.orbs_of_crafting.register.Requirements;
import net.neoforged.bus.api.IEventBus;

import java.util.Arrays;
import java.util.List;

public class LibModConstructor extends OrderedModConstructor {
    public LibModConstructor(String modid) {
        super(modid);
    }

    @Override
    public List<ExileRegistryEventClass> getRegisterEvents() {
        return Arrays.asList();
    }

    @Override
    public List<ExileKeyHolder> getAllKeyHolders() {
        return Arrays.asList(
                LibLeagues.INSTANCE,
                LibMapDataBlocks.INSTANCE,
                LibMobLists.INSTANCE,
                LibAffixesHolder.INSTANCE,
                LibMapFinishRarities.INSTANCE,
                LibMapContents.INSTANCE,
                LibRelicStats.INSTANCE,
                LibRelicTypes.INSTANCE,
                LibRelicAffixes.INSTANCE,
                LibRelicRarities.INSTANCE,
                Modifications.INSTANCE,
                Requirements.INSTANCE,
                Orbs.INSTANCE,
                OrbEdits.INSTANCE);
    }

    @Override
    public void registerDeferredContainers(IEventBus bus) {
        CommonInit.initDeferred(bus);
        bus.addListener(this::registerPayloads);
    }

    public void registerPayloads(final net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent event) {
        Packets.setRegistrar(event.registrar(Ref.MODID));
        // Register server-to-client packets for library_of_exile
        int i = 0;
        Packets.registerServerToClient(new com.robertx22.library_of_exile.packets.registry.EfficientRegistryPacket<>(),
                i++);
        Packets.registerServerToClient(new com.robertx22.library_of_exile.packets.TileUpdatePacket(), i++);
        Packets.registerServerToClient(
                new com.robertx22.library_of_exile.packets.registry.TellClientToRegisterFromPackets(), i++);
        // Register client-to-server packets for library_of_exile
        Packets.registerClientToServerPacket(new com.robertx22.library_of_exile.packets.RequestTilePacket(), i++);
    }

    @Override
    public void registerDeferredEntries() {
        ExileLibEntries.init();
    }

    @Override
    public void registerDatabases() {
        LibDatabase.INSTANCE.initDatabases();
    }

    @Override
    public void registerDatabaseEntries() {

    }
}
