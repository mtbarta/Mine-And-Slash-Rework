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
        // ctx.player() returns a Player directly in newer versions, checking if method exists?
        // Error said: "incompatible types: Optional<Player> cannot be converted to Player"
        // So strict 1.20.4 probably returns Optional.
        return ctx.player().orElse(null);
    }

    public <T extends MyPacket<T>> void reply(T msg) {
        if (msg == null) {
            ExileLog.get().log("Reply packet is null!");
            return;
        }
        // ctx.reply(msg);
    }
}