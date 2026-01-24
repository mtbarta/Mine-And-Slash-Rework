package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class DataSaverCheckUtil {
    public static boolean checkForDataSaver(String saverGUID, ItemStack itemStack) {
        CustomData customData = itemStack.get(DataComponents.CUSTOM_DATA);
        if (customData != null) {
            return customData.getUnsafe().contains(saverGUID);
        }
        return false;

    }
}
