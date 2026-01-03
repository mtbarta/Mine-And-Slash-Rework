package com.robertx22.mine_and_slash.mmorpg.registers.common;

import net.neoforged.bus.api.IEventBus;

public class SlashCapabilities {

    public static void register(IEventBus bus) {
        // Register the AttachmentTypes DeferredRegister to the mod event bus
        SlashAttachments.register(bus);
    }
}
