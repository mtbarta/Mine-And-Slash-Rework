package com.robertx22.library_of_exile.main;

import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public abstract class MyPacket<T extends MyPacket<T>> implements CustomPacketPayload {

    @Override
    public @NotNull ResourceLocation id() {
        return getIdentifier();
    }

    public ResourceLocation getIdentifier() {
        throw new UnsupportedOperationException("Packet must implement id() or getIdentifier()");
    }

    public abstract void loadFromData(FriendlyByteBuf tag);

    public abstract void saveToData(FriendlyByteBuf tag);

    public abstract void onReceived(ExilePacketContext ctx);

    public abstract MyPacket<T> newInstance();

    @Override
    public void write(FriendlyByteBuf pBuffer) {
        saveToData(pBuffer);
    }

    public static <T extends MyPacket<T>> void handle(final T packet, final IPayloadContext context) {
         context.workHandler().submitAsync(() -> {
            try {
                packet.onReceived(new ExilePacketContext(context));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // ...
}
