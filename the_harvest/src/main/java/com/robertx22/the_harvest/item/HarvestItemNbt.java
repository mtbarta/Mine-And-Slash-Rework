package com.robertx22.the_harvest.item;

import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import com.robertx22.the_harvest.main.HarvestMain;
import com.robertx22.the_harvest.registry.HarvestDataComponents;

public class HarvestItemNbt {

    public static ItemstackDataSaver<HarvestItemMapData> HARVEST_MAP = new ItemstackDataSaver<>(
            HarvestMain.MODID + "_map", HarvestItemMapData.class, HarvestItemMapData::new,
            () -> HarvestDataComponents.HARVEST_MAP_DATA.get());

}
