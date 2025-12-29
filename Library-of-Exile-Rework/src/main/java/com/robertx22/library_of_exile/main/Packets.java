package com.robertx22.library_of_exile.main;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

public class Packets {

    private static IPayloadRegistrar registrar;

    public static void setRegistrar(IPayloadRegistrar reg) {
        registrar = reg;
    }

    public static <T extends MyPacket<T>> void sendToClient(Player player, T packet) {
        if (!(player instanceof ServerPlayer sp)) {
            ExileLog.get().debug("skip sending packet " + packet.id().getPath() + " to client!");
            return;
        }
        try {
            PacketDistributor.PLAYER.with(sp).send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T extends MyPacket<T>> void sendToServer(T packet) {
        try {
            PacketDistributor.SERVER.noArg().send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T extends MyPacket<T>> void registerClientToServerPacket(MyPacket<T> packet, int id) {
       // id is unused in new system, relied on id() (formerly type())
        registrar.play(
                packet.id(),
                // We need a reader.
                (buf) -> {
                    T newP = (T) packet.newInstance(); 
                    newP.loadFromData(buf);
                    return newP;
                },
                (p, ctx) -> MyPacket.handle(p, ctx)
        );
    }

    public static <T extends MyPacket<T>> void registerServerToClient(MyPacket<T> packet, int id) {
         registrar.play(
                packet.id(),
                (buf) -> {
                    T newP = (T) packet.newInstance();
                    newP.loadFromData(buf);
                    return newP;
                },
                (p, ctx) -> MyPacket.handle(p, ctx)
        );
    }

    public static void sendToTracking(MyPacket<?> msg, BlockPos pos, Level world) {
         PacketDistributor.TRACKING_CHUNK.with(world.getChunkAt(pos)).send(msg);
    }

    public static void sendToTracking(MyPacket<?> msg, Entity en) {
        if (!en.level().isClientSide) {
             PacketDistributor.TRACKING_ENTITY.with(en).send(msg);
        }
    }

}
