package com.robertx22.ancient_obelisks.item;

import com.robertx22.ancient_obelisks.main.ObelisksMain;
import com.robertx22.ancient_obelisks.registry.ObeliskDataComponents;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;

public class ObeliskItemNbt {

    public static ItemstackDataSaver<ObeliskItemMapData> OBELISK_MAP = new ItemstackDataSaver<>(
            ObelisksMain.MODID + "_obelisk", ObeliskItemMapData.class, ObeliskItemMapData::new,
            () -> ObeliskDataComponents.OBELISK_MAP_DATA.get());

}
