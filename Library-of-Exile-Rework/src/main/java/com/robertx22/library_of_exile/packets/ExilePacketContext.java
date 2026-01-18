package com.robertx22.library_of_exile.packets;

import com.robertx22.library_of_exile.main.ExileLog;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ExilePacketContext {

    IPayloadContext ctx;

    public ExilePacketContext(IPayloadContext ctx) {
        this.ctx = ctx;
    }

    public Player getPlayer() {
        return ctx.player();
    }

    public <T extends MyPacket<T>> void reply(T msg) {
        if (msg == null) {
            ExileLog.get().log("Reply packet is null!");
            return;
        }
        // ctx.reply(msg);
    }
}