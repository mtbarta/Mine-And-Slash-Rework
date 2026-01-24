package com.robertx22.mine_and_slash.mmorpg.registers.common;

import com.robertx22.library_of_exile.components.PlayerCapabilities;
import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.capability.player.PlayerBackpackData;
import com.robertx22.mine_and_slash.capability.player.PlayerData;

import net.neoforged.bus.api.IEventBus;

public class SlashCapabilities {

    public static void register(IEventBus bus) {
        // Register the AttachmentTypes DeferredRegister to the mod event bus
        SlashAttachments.register(bus);

        PlayerCapabilities.register(SlashAttachments.ENTITY_DATA, new EntityData(null));
        PlayerCapabilities.register(SlashAttachments.PLAYER_DATA, new PlayerData(null));
        PlayerCapabilities.register(SlashAttachments.PLAYER_BACKPACK_DATA, new PlayerBackpackData(null));

    }
}
