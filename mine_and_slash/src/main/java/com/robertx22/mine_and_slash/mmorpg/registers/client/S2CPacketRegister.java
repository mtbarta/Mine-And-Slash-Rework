package com.robertx22.mine_and_slash.mmorpg.registers.client;

import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.mine_and_slash.database.data.profession.StationPacket;
import com.robertx22.mine_and_slash.database.data.profession.StationSyncData;
import com.robertx22.mine_and_slash.database.data.spells.components.packets.ParticlesPacket;
import com.robertx22.mine_and_slash.gui.screens.stat_gui.SendStatCalcInfoToClientPacket;
import com.robertx22.mine_and_slash.gui.screens.stat_gui.StatCalcInfoData;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.vanilla_mc.packets.*;
import com.robertx22.mine_and_slash.vanilla_mc.packets.backpack.SetBackpackContentPacket;
import com.robertx22.mine_and_slash.vanilla_mc.packets.backpack.SetBackpackSlotPacket;
import com.robertx22.mine_and_slash.vanilla_mc.packets.interaction.ExileInteractionResultPacket;
import com.robertx22.mine_and_slash.vanilla_mc.packets.spells.TellClientEntityCastingSpell;
import com.robertx22.mine_and_slash.vanilla_mc.packets.spells.TellClientEntityIsCastingSpellPacket;

public class S2CPacketRegister {

    public static void register() {
        int i = 1000;

        Packets.registerServerToClient(new DmgNumPacket(), i++);
        Packets.registerServerToClient(new EfficientMobUnitPacket(), i++);
        Packets.registerServerToClient(new EntityUnitPacket(), i++);
        Packets.registerServerToClient(new NoManaPacket(), i++);
        Packets.registerServerToClient(new OpenGuiPacket(), i++);
        Packets.registerServerToClient(new TellClientEntityCastingSpell(), i++);
        Packets.registerServerToClient(new SyncAreaLevelPacket(), i++);
        Packets.registerServerToClient(new TellClientEntityIsCastingSpellPacket(), i++);
        Packets.registerServerToClient(new ParticlesPacket(new ParticlesPacket.Data()), i++);
        Packets.registerServerToClient(new StationPacket(new StationSyncData()), i++);
        Packets.registerServerToClient(new SendStatCalcInfoToClientPacket(new StatCalcInfoData()), i++);
        Packets.registerServerToClient(new ExileInteractionResultPacket(), i++);
        Packets.registerServerToClient(new TellClientResetCaches(), i++);
        Packets.registerServerToClient(new MapCompletePacket(), i++);
        Packets.registerServerToClient(new OpenEntityStatsReplyPacket(), i++);
        Packets.registerServerToClient(new SetBackpackContentPacket(), i++);
        Packets.registerServerToClient(new SetBackpackSlotPacket(), i++);

    }
}
