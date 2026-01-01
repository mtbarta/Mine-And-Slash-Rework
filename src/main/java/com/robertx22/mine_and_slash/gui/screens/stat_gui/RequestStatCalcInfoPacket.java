package com.robertx22.mine_and_slash.gui.screens.stat_gui;

import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.packets.ExilePacketContext;

import net.minecraft.resources.ResourceLocation;

import net.minecraft.network.RegistryFriendlyByteBuf;

public class RequestStatCalcInfoPacket extends MyPacket<RequestStatCalcInfoPacket> {

    @Override
    public ResourceLocation getIdentifier() {
        return SlashRef.id("reqsi");
    }

    @Override
    public void loadFromData(RegistryFriendlyByteBuf buf) {

    }

    @Override
    public void saveToData(RegistryFriendlyByteBuf buf) {

    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        Packets.sendToClient(ctx.getPlayer(), new SendStatCalcInfoToClientPacket(Load.player(ctx.getPlayer()).ctxs));
    }

    @Override
    public MyPacket<RequestStatCalcInfoPacket> newInstance() {
        return new RequestStatCalcInfoPacket();
    }
}
