package com.robertx22.dungeon_realm.registry;

import com.robertx22.dungeon_realm.capability.DungeonEntityCapability;
import com.robertx22.dungeon_realm.main.DungeonMain;
import com.robertx22.dungeon_realm.structure.DungeonMapCapability;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.bus.api.IEventBus;
import com.mojang.serialization.Codec;
import java.util.function.Supplier;

public class DungeonAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, DungeonMain.MODID);

    public static final Supplier<AttachmentType<DungeonEntityCapability>> DUNGEON_ENTITY = ATTACHMENT_TYPES.register("dungeon_entity",
            () -> AttachmentType.builder(() -> new DungeonEntityCapability(null))
                    .serialize(Codec.unit(() -> new DungeonEntityCapability(null)))
                    .build());

    public static final Supplier<AttachmentType<DungeonMapCapability>> DUNGEON_MAP = ATTACHMENT_TYPES.register("dungeon_map",
            () -> AttachmentType.builder(() -> new DungeonMapCapability(null))
                    .serialize(Codec.unit(() -> new DungeonMapCapability(null)))
                    .build());

    public static void register(IEventBus bus) {
        ATTACHMENT_TYPES.register(bus);
    }
}
