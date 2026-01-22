package com.robertx22.mns_cobblemon.network;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.robertx22.mns_cobblemon.gui.KobblemonContainer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.Nullable;

import static com.robertx22.mns_cobblemon.MnSCobblemonCompat.MODID;

public class PacketOpenKobblemonGui implements CustomPacketPayload {

    public static final Type<PacketOpenKobblemonGui> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(MODID, "open_kobblemon_gui"));

    private final int entityId;

    public PacketOpenKobblemonGui(int entityId) {
        this.entityId = entityId;
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, PacketOpenKobblemonGui> STREAM_CODEC = StreamCodec
            .composite(
                    net.minecraft.network.codec.ByteBufCodecs.VAR_INT,
                    p -> p.entityId,
                    PacketOpenKobblemonGui::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(PacketOpenKobblemonGui packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer sp) {
                Entity entity = sp.level().getEntity(packet.entityId);
                if (entity instanceof PokemonEntity pokemon) {
                    sp.openMenu(new MenuProvider() {
                        @Override
                        public Component getDisplayName() {
                            return Component.literal("Pokemon Equipment");
                        }

                        @Nullable
                        @Override
                        public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
                            return new KobblemonContainer(id, playerInventory, pokemon);
                        }
                    }, buf -> buf.writeInt(packet.entityId));
                }
            }
        });
    }
}
