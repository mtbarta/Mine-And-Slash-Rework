package com.robertx22.mine_and_slash.mmorpg.registers.common;

import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.mine_and_slash.a_libraries.jei.LockRecipePacket;
import com.robertx22.mine_and_slash.capability.player.data.Backpacks;
import com.robertx22.mine_and_slash.characters.CreateCharPacket;
import com.robertx22.mine_and_slash.characters.ToonActionPacket;
import com.robertx22.mine_and_slash.characters.reworked_gui.ToonActionButton;
import com.robertx22.mine_and_slash.gui.screens.stat_gui.RequestStatCalcInfoPacket;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.prophecy.AcceptProphecyAffixPacket;
import com.robertx22.mine_and_slash.prophecy.AcceptProphecyPacket;
import com.robertx22.mine_and_slash.vanilla_mc.packets.*;
import com.robertx22.mine_and_slash.vanilla_mc.packets.backpack.BackPackLootMenuPacket;
import com.robertx22.mine_and_slash.vanilla_mc.packets.backpack.OpenBackpackPacket;
import com.robertx22.mine_and_slash.vanilla_mc.packets.perks.PerkChangePacket;
import com.robertx22.mine_and_slash.vanilla_mc.packets.spells.TellServerToCancelSpellCast;
import com.robertx22.mine_and_slash.vanilla_mc.packets.spells.TellServerToCastSpellPacket;

public class C2SPacketRegister {

    public static void register() {

        int i = 100;
        Packets.registerClientToServerPacket(new TellServerToCastSpellPacket(), i++);
        Packets.registerClientToServerPacket(new PerkChangePacket(), i++);
        Packets.registerClientToServerPacket(new AllocateClassPointPacket(), i++);
        Packets.registerClientToServerPacket(new AllocateStatPacket(), i++);
        Packets.registerClientToServerPacket(new TellServerToCancelSpellCast(), i++);
        Packets.registerClientToServerPacket(new OpenContainerPacket(OpenContainerPacket.GuiType.SKILL_GEMS), i++);
        Packets.registerClientToServerPacket(new OpenBackpackPacket(Backpacks.BackpackType.GEARS), i++);
        Packets.registerClientToServerPacket(new InvGuiPacket(), i++);
        Packets.registerClientToServerPacket(new OpenJewelsPacket(), i++);
        Packets.registerClientToServerPacket(new CraftPacket(), i++);

        Packets.registerClientToServerPacket(new CreateCharPacket(""), i++);
        Packets.registerClientToServerPacket(new ToonActionPacket(ToonActionButton.Action.LOAD, 0, ""), i++);
        Packets.registerClientToServerPacket(new AcceptProphecyPacket(""), i++);
        Packets.registerClientToServerPacket(new AcceptProphecyAffixPacket(""), i++);
        Packets.registerClientToServerPacket(new LockTogglePacket(), i++);
        Packets.registerClientToServerPacket(new UnsummonPacket(), i++);
        Packets.registerClientToServerPacket(new RequestStatCalcInfoPacket(), i++);
        Packets.registerClientToServerPacket(new LockRecipePacket(""), i++);

        Packets.registerClientToServerPacket(new BackPackLootMenuPacket(), i++);
        Packets.registerClientToServerPacket(new QuickUsePotionPacket(), i++);
        Packets.registerClientToServerPacket(new OpenEntityStatsRequestPacket(), i++);


        // Packets.registerClientToServerPacket(MMORPG.NETWORK, new SetupHotbarPacket(), i++);
    }

}


