package com.robertx22.library_of_exile.main;

import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public abstract class MyPacket<T extends MyPacket<T>> implements CustomPacketPayload {

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull Type<T> type() {
        return new Type<>(getIdentifier());
    }

    public ResourceLocation getIdentifier() {
        throw new UnsupportedOperationException("Packet must implement getIdentifier()");
    }

    public abstract void loadFromData(RegistryFriendlyByteBuf tag);

    public abstract void saveToData(RegistryFriendlyByteBuf tag);

    // Convenience alias for NeoForge StreamCodec compatibility
    public final void write(RegistryFriendlyByteBuf buf) {
        saveToData(buf);
    }

    public abstract void onReceived(ExilePacketContext ctx);

    public abstract MyPacket<T> newInstance();

    public static <T extends MyPacket<T>> void handle(final T packet, final IPayloadContext context) {
        try {
            packet.onReceived(new ExilePacketContext(context));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ...
}
