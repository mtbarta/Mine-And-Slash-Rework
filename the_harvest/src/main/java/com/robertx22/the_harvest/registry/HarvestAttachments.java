package com.robertx22.the_harvest.registry;

import com.robertx22.the_harvest.capability.HarvestEntityCap;
import com.robertx22.the_harvest.main.HarvestMain;
import com.robertx22.the_harvest.structure.HarvestMapCap;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.bus.api.IEventBus;
import com.mojang.serialization.Codec;
import java.util.function.Supplier;

public class HarvestAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, HarvestMain.MODID);

    public static final Supplier<AttachmentType<HarvestEntityCap>> HARVEST_ENTITY = ATTACHMENT_TYPES.register("harvest_entity",
            () -> AttachmentType.builder(() -> new HarvestEntityCap(null))
                    .serialize(Codec.unit(() -> new HarvestEntityCap(null)))
                    .build());

    public static final Supplier<AttachmentType<HarvestMapCap>> HARVEST_MAP = ATTACHMENT_TYPES.register("harvest_map",
            () -> AttachmentType.builder(() -> new HarvestMapCap(null))
                    .serialize(Codec.unit(() -> new HarvestMapCap(null)))
                    .build());

    public static void register(IEventBus bus) {
        ATTACHMENT_TYPES.register(bus);
    }
}
