package com.robertx22.ancient_obelisks.registry;

import com.robertx22.ancient_obelisks.capability.ObeliskEntityCapability;
import com.robertx22.ancient_obelisks.main.ObelisksMain;
import com.robertx22.ancient_obelisks.structure.ObeliskMapCapability;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.bus.api.IEventBus;
import com.mojang.serialization.Codec;
import java.util.function.Supplier;

public class ObeliskAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, ObelisksMain.MODID);

    public static final Supplier<AttachmentType<ObeliskEntityCapability>> OBELISK_ENTITY = ATTACHMENT_TYPES.register("obelisk_entity",
            () -> AttachmentType.builder(() -> new ObeliskEntityCapability(null))
                    .serialize(Codec.unit(() -> new ObeliskEntityCapability(null)))
                    .build());

    public static final Supplier<AttachmentType<ObeliskMapCapability>> OBELISK_MAP = ATTACHMENT_TYPES.register("obelisk_map",
            () -> AttachmentType.builder(() -> new ObeliskMapCapability(null))
                    .serialize(Codec.unit(() -> new ObeliskMapCapability(null)))
                    .build());

    public static void register(IEventBus bus) {
        ATTACHMENT_TYPES.register(bus);
    }
}
