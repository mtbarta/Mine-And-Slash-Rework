package com.robertx22.mns_minecolonies;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(MnSMineColoniesCompat.MODID)
public class MnSMineColoniesCompat {

    public static final String MODID = "mns_minecolonies";

    public MnSMineColoniesCompat(IEventBus modEventBus) {

        // Register the compatibility logic
        MineColoniesIntegration.register();
    }
}
