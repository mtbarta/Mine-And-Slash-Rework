package com.robertx22.library_of_exile.main;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class Packets {

    private static PayloadRegistrar registrar;

    public static void setRegistrar(PayloadRegistrar reg) {
        registrar = reg;
    }

    public static <T extends MyPacket<T>> void sendToClient(Player player, T packet) {
        if (!(player instanceof ServerPlayer sp)) {
            ExileLog.get().debug("skip sending packet " + packet.getIdentifier().getPath() + " to client!");
            return;
        }
        try {
            PacketDistributor.sendToPlayer(sp, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T extends MyPacket<T>> void sendToServer(T packet) {
        try {
            PacketDistributor.sendToServer(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T extends MyPacket<T>> void registerClientToServerPacket(MyPacket<T> packet, int id) {
        registrar.playToServer(
                packet.type(),
                StreamCodec.of((RegistryFriendlyByteBuf buf, T p) -> p.saveToData(buf),
                        (RegistryFriendlyByteBuf buf) -> {
                            T newP = (T) packet.newInstance();
                            newP.loadFromData(buf);
                            return newP;
                        }),
                (p, ctx) -> MyPacket.handle(p, ctx));
    }

    public static <T extends MyPacket<T>> void registerServerToClient(MyPacket<T> packet, int id) {
        registrar.playToClient(
                packet.type(),
                StreamCodec.of((RegistryFriendlyByteBuf buf, T p) -> p.saveToData(buf),
                        (RegistryFriendlyByteBuf buf) -> {
                            T newP = (T) packet.newInstance();
                            newP.loadFromData(buf);
                            return newP;
                        }),
                (p, ctx) -> MyPacket.handle(p, ctx));
    }

    public static void sendToTracking(MyPacket<?> msg, BlockPos pos, Level world) {
        if (world instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            PacketDistributor.sendToPlayersTrackingChunk(serverLevel, world.getChunkAt(pos).getPos(), msg);
        }
    }

    public static void sendToTracking(MyPacket<?> msg, Entity en) {
        if (!en.level().isClientSide) {
            PacketDistributor.sendToPlayersTrackingEntity(en, msg);
        }
    }

}
