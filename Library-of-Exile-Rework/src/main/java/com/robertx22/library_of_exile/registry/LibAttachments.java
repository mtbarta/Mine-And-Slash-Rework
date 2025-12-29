package com.robertx22.library_of_exile.registry;

import com.robertx22.library_of_exile.components.*;
import com.robertx22.library_of_exile.main.Ref;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class LibAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Ref.MODID);

    public static final Supplier<AttachmentType<PlayerDataCapability>> LIB_PLAYER_DATA = ATTACHMENT_TYPES.register("lib_player_data",
            () -> AttachmentType.builder(() -> new PlayerDataCapability(null))
                    /*
                    .serialize(new IAttachmentSerializer<PlayerDataCapability, net.minecraft.nbt.Tag>() {
                        @Override
                        public net.minecraft.nbt.Tag write(PlayerDataCapability attachment, net.minecraft.core.HolderLookup.Provider provider) {
                            return attachment.serializeNBT();
                        }
                        @Override
                        public PlayerDataCapability read(net.neoforged.neoforge.attachment.IAttachmentHolder holder, net.minecraft.nbt.Tag tag, net.minecraft.core.HolderLookup.Provider provider) {
                            var cap = new PlayerDataCapability(null);
                            cap.deserializeNBT((net.minecraft.nbt.CompoundTag) tag);
                            return cap;
                        }
                    })
                     */
                    .copyOnDeath()
                    .build());

    public static final Supplier<AttachmentType<LibChunkCap>> LIB_CHUNK_CAP = ATTACHMENT_TYPES.register("lib_chunk_cap",
            () -> AttachmentType.builder(() -> new LibChunkCap(null))
                    /*
                    .serialize(new IAttachmentSerializer<LibChunkCap, net.minecraft.nbt.Tag>() {
                         @Override
                        public net.minecraft.nbt.Tag write(LibChunkCap attachment, net.minecraft.core.HolderLookup.Provider provider) {
                            return attachment.serializeNBT();
                        }
                        @Override
                        public LibChunkCap read(net.neoforged.neoforge.attachment.IAttachmentHolder holder, net.minecraft.nbt.Tag tag, net.minecraft.core.HolderLookup.Provider provider) {
                            var cap = new LibChunkCap(null);
                            cap.deserializeNBT((net.minecraft.nbt.CompoundTag) tag);
                            return cap;
                        }
                    })
                     */
                    .build());

    // Map Cap
    public static final Supplier<AttachmentType<LibMapCap>> LIB_MAP_CAP = ATTACHMENT_TYPES.register("lib_map_cap",
            () -> AttachmentType.builder(() -> new LibMapCap(null))
                    /*
                     .serialize(new IAttachmentSerializer<LibMapCap, net.minecraft.nbt.Tag>() {
                         @Override
                        public net.minecraft.nbt.Tag write(LibMapCap attachment, net.minecraft.core.HolderLookup.Provider provider) {
                            return attachment.serializeNBT();
                        }
                        @Override
                        public LibMapCap read(net.neoforged.neoforge.attachment.IAttachmentHolder holder, net.minecraft.nbt.Tag tag, net.minecraft.core.HolderLookup.Provider provider) {
                            var cap = new LibMapCap(null);
                            cap.deserializeNBT((net.minecraft.nbt.CompoundTag) tag);
                            return cap;
                        }
                    })
                     */
                    .build());

    // Entity Info
    public static final Supplier<AttachmentType<EntityInfoComponent>> ENTITY_INFO = ATTACHMENT_TYPES.register("entity_info",
            () -> AttachmentType.builder(() -> new EntityInfoComponent(null))
                    /*
                   .serialize(new IAttachmentSerializer<EntityInfoComponent, net.minecraft.nbt.Tag>() {
                         @Override
                        public net.minecraft.nbt.Tag write(EntityInfoComponent attachment, net.minecraft.core.HolderLookup.Provider provider) {
                            return attachment.serializeNBT();
                        }
                        @Override
                        public EntityInfoComponent read(net.neoforged.neoforge.attachment.IAttachmentHolder holder, net.minecraft.nbt.Tag tag, net.minecraft.core.HolderLookup.Provider provider) {
                            var cap = new EntityInfoComponent(null);
                            cap.deserializeNBT((net.minecraft.nbt.CompoundTag) tag);
                            return cap;
                        }
                    })
                     */
                    .copyOnDeath()
                    .build());

    // Map Connections
    public static final Supplier<AttachmentType<MapConnectionsCap>> MAP_CONNECTIONS = ATTACHMENT_TYPES.register("map_connections",
            () -> AttachmentType.builder(() -> new MapConnectionsCap(null))
                    /*
                    .serialize(new IAttachmentSerializer<MapConnectionsCap, net.minecraft.nbt.Tag>() {
                         @Override
                        public net.minecraft.nbt.Tag write(MapConnectionsCap attachment, net.minecraft.core.HolderLookup.Provider provider) {
                            return attachment.serializeNBT();
                        }
                        @Override
                        public MapConnectionsCap read(net.neoforged.neoforge.attachment.IAttachmentHolder holder, net.minecraft.nbt.Tag tag, net.minecraft.core.HolderLookup.Provider provider) {
                            var cap = new MapConnectionsCap(null);
                            cap.deserializeNBT((net.minecraft.nbt.CompoundTag) tag);
                            return cap;
                        }
                    })
                     */
                    .build());

    public static void register(IEventBus bus) {
        ATTACHMENT_TYPES.register(bus);
    }
}
