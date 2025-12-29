package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;

import java.util.function.Supplier;

public class DataSaverCheckUtil {
    public static boolean checkForDataSaver(String saverGUID, ItemStack itemStack){
        if (itemStack.hasTag()) {
            return !itemStack.getTag().getString(saverGUID).isEmpty();
        }
        return false;

    }
}
