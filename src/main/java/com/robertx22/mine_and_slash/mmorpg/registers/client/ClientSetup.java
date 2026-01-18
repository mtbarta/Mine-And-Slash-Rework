package com.robertx22.mine_and_slash.mmorpg.registers.client;

public class ClientSetup {

    public static void setup() {
        RenderLayersRegister.setup();
        // ContainerGuiRegisters.reg() is now called in MMORPG constructor
        // to ensure RegisterMenuScreensEvent listener is registered early enough
    }
}
