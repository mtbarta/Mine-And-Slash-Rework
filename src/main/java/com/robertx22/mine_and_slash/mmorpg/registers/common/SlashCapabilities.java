package com.robertx22.mine_and_slash.mmorpg.registers.common;

import com.robertx22.mine_and_slash.mmorpg.ForgeEvents;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashAttachments;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;

public class SlashCapabilities {

    public static void register() {
        // Register the AttachmentTypes DeferredRegister to the mod event bus
        SlashAttachments.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
