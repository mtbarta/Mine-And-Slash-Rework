package com.robertx22.mns_cobblemon.network;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.robertx22.mine_and_slash.aoe_data.database.spells.schools.SummonSpells;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import com.robertx22.mns_cobblemon.events.PokemonRecruitmentHandler;

import static com.robertx22.mns_cobblemon.MnSCobblemonCompat.MODID;

public class PacketRecruitMinion implements CustomPacketPayload {

    public static final Type<PacketRecruitMinion> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(MODID, "recruit_minion"));

    private final int entityId;

    public PacketRecruitMinion(int entityId) {
        this.entityId = entityId;
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, PacketRecruitMinion> STREAM_CODEC = StreamCodec
            .composite(
                    net.minecraft.network.codec.ByteBufCodecs.VAR_INT,
                    p -> p.entityId,
                    PacketRecruitMinion::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(PacketRecruitMinion packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer sp) {
                Entity entity = sp.level().getEntity(packet.entityId);
                if (entity instanceof PokemonEntity pokemonEntity) {
                    // Check ownership
                    if (pokemonEntity.getOwner() != sp) {
                        return;
                    }

                    com.cobblemon.mod.common.pokemon.Pokemon pokemon = pokemonEntity.getPokemon();
                    if (pokemon == null)
                        return;

                    net.minecraft.nbt.CompoundTag persistentData = pokemon.getPersistentData();
                    boolean isMinion = persistentData.contains("mns_is_minion")
                            && persistentData.getBoolean("mns_is_minion");

                    if (isMinion) {
                        // Dismiss: Remove tag
                        persistentData.remove("mns_is_minion");

                        var data = Load.Unit(pokemonEntity);
                        if (data != null) {
                            data.summonedPetData.spell = "";
                        }
                        sp.sendSystemMessage(Component.literal("Pokemon is no longer aiding you."));
                    } else {
                        // Recruit: Add tag
                        persistentData.putBoolean("mns_is_minion", true);

                        // Apply immediately
                        com.robertx22.mns_cobblemon.events.PokemonRecruitmentHandler.applyMinionStatus(pokemonEntity);

                        sp.sendSystemMessage(Component.literal("Pokemon is now aiding you!"));
                    }
                }
            }
        });
    }
}
