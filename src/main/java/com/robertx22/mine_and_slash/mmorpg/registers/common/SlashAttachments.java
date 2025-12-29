package com.robertx22.mine_and_slash.mmorpg.registers.common;

import com.robertx22.library_of_exile.components.LibChunkCap;
import com.robertx22.library_of_exile.components.PlayerDataCapability;
import com.robertx22.mine_and_slash.capability.chunk.ChunkCap;
import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.capability.player.PlayerBackpackData;
import com.robertx22.mine_and_slash.capability.player.PlayerData;
import com.robertx22.mine_and_slash.capability.world.WorldData;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.bus.api.IEventBus;
import java.util.function.Supplier;

public class SlashAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, SlashRef.MODID);

    // Player Data
    public static final Supplier<AttachmentType<PlayerData>> PLAYER_DATA = ATTACHMENT_TYPES.register("player_data",
            () -> AttachmentType.builder(() -> new PlayerData(null))
                    .serialize(PlayerData::serializeNBT, PlayerData::deserializeNBT)
                    .copyOnDeath()
                    .build());

    public static final Supplier<AttachmentType<PlayerBackpackData>> PLAYER_BACKPACK_DATA = ATTACHMENT_TYPES.register("player_backpack_data",
            () -> AttachmentType.builder(() -> new PlayerBackpackData(null))
                    .serialize(PlayerBackpackData::serializeNBT, PlayerBackpackData::deserializeNBT)
                    .copyOnDeath()
                    .build());

    // Entity Data
    public static final Supplier<AttachmentType<EntityData>> ENTITY_DATA = ATTACHMENT_TYPES.register("entity_data",
            () -> AttachmentType.builder(() -> new EntityData(null))
                    .serialize(EntityData::serializeNBT, EntityData::deserializeNBT)
                    .build());


    // Chunk Data
    public static final Supplier<AttachmentType<ChunkCap>> CHUNK_CAP = ATTACHMENT_TYPES.register("chunk_cap",
            () -> AttachmentType.builder(() -> new ChunkCap(null))
                    .serialize(ChunkCap::serializeNBT, ChunkCap::deserializeNBT)
                    .build());


    // World Data
    public static final Supplier<AttachmentType<WorldData>> WORLD_DATA = ATTACHMENT_TYPES.register("world_data",
            () -> AttachmentType.builder(() -> new WorldData(null))
                    .serialize(WorldData::serializeNBT, WorldData::deserializeNBT)
                    .build());


    public static void register(IEventBus bus) {
        ATTACHMENT_TYPES.register(bus);
    }
}
