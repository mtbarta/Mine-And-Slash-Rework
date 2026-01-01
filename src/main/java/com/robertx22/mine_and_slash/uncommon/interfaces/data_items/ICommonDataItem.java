package com.robertx22.mine_and_slash.uncommon.interfaces.data_items;

import com.robertx22.library_of_exile.utils.AllItemStackSavers;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.itemstack.CustomItemData;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.StackKeys;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.ITooltip;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import net.minecraft.world.item.ItemStack;

public interface ICommonDataItem<R extends Rarity> extends ISalvagable, ITooltip, IRarity {

    @Override
    default boolean isSalvagable(ExileStack stack) {
        return !stack.get(StackKeys.CUSTOM).getOrCreate().data.get(CustomItemData.KEYS.SALVAGING_DISABLED);
    }

    public int getLevel();

    public default int getSalvageExpReward() {
        GearRarity rar = getRarity();
        return (int) (30 * rar.item_tier_power * getLevel());
    }

    public default int getAutoSalvageExpReward() {
        GearRarity rar = getRarity();
        return (int) (3 * rar.item_tier_power * getLevel());
    }

    com.robertx22.library_of_exile.components.ComponentDataSaver<? extends ICommonDataItem> getStackSaver();

    void saveToStack(ItemStack stack);

    static ICommonDataItem load(ItemStack stack) {
        // Iterate over known savers from StackSaving
        // This assumes StackSaving has all relevant savers public static
        try {
            for (java.lang.reflect.Field field : com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving.class
                    .getFields()) {
                if (com.robertx22.library_of_exile.components.ComponentDataSaver.class
                        .isAssignableFrom(field.getType())) {
                    com.robertx22.library_of_exile.components.ComponentDataSaver<?> saver = (com.robertx22.library_of_exile.components.ComponentDataSaver<?>) field
                            .get(null);
                    if (saver.getClazz() != null && ICommonDataItem.class.isAssignableFrom(saver.getClazz())) {
                        @SuppressWarnings("unchecked")
                        com.robertx22.library_of_exile.components.ComponentDataSaver<? extends ICommonDataItem> typedSaver = (com.robertx22.library_of_exile.components.ComponentDataSaver<? extends ICommonDataItem>) saver;
                        ICommonDataItem data = typedSaver.loadFrom(stack);
                        if (data != null) {
                            return data;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
