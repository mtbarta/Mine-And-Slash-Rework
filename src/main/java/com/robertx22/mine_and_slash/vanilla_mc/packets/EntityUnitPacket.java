package com.robertx22.mine_and_slash.vanilla_mc.packets;

import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class EntityUnitPacket extends MyPacket<EntityUnitPacket> {

    public int id;
    public CompoundTag nbt;

    public EntityUnitPacket() {

    }

    public EntityUnitPacket(Entity entity) {
        this.id = entity.getId();
        this.nbt = Load.Unit(entity).serializeNBT(entity.level().registryAccess());
    }

    @Override
    public ResourceLocation getIdentifier() {
        return ResourceLocation.fromNamespaceAndPath(SlashRef.MODID, "enpack");
    }

    @Override
    public void loadFromData(RegistryFriendlyByteBuf tag) {
        id = tag.readInt();
        nbt = tag.readNbt();

    }

    @Override
    public void saveToData(RegistryFriendlyByteBuf tag) {
        tag.writeInt(id);
        tag.writeNbt(nbt);

    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        Entity entity = ctx.getPlayer().level().getEntity(id);

        if (entity instanceof LivingEntity) {

            LivingEntity en = (LivingEntity) entity;

            Load.Unit(en).deserializeNBT(en.level().registryAccess(), nbt);
        }
    }

    @Override
    public MyPacket<EntityUnitPacket> newInstance() {
        return new EntityUnitPacket();
    }
}
