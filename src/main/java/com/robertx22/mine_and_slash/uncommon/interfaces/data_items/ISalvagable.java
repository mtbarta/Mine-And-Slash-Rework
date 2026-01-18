package com.robertx22.mine_and_slash.uncommon.interfaces.data_items;

import com.robertx22.library_of_exile.utils.AllItemStackSavers;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import com.robertx22.mine_and_slash.gui.inv_gui.actions.auto_salvage.ToggleAutoSalvageRarity;
import com.robertx22.mine_and_slash.itemstack.CustomItemData;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.StackKeys;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface ISalvagable {

    List<ItemStack> getSalvageResult(ExileStack stack);

    public ToggleAutoSalvageRarity.SalvageType getSalvageType();

    default String getSalvageConfigurationId() {
        return null;
    }

    default boolean isSalvagable(ExileStack stack) {
        return !stack.get(StackKeys.CUSTOM).getOrCreate().data.get(CustomItemData.KEYS.SALVAGING_DISABLED);
    }

    static ISalvagable load(ItemStack stack) {

        try {
            for (java.lang.reflect.Field field : com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving.class
                    .getFields()) {
                if (com.robertx22.library_of_exile.components.ComponentDataSaver.class
                        .isAssignableFrom(field.getType())) {
                    com.robertx22.library_of_exile.components.ComponentDataSaver<?> saver = (com.robertx22.library_of_exile.components.ComponentDataSaver<?>) field
                            .get(null);
                    if (saver.getClazz() != null && ISalvagable.class.isAssignableFrom(saver.getClazz())) {
                        @SuppressWarnings("unchecked")
                        com.robertx22.library_of_exile.components.ComponentDataSaver<? extends ISalvagable> typedSaver = (com.robertx22.library_of_exile.components.ComponentDataSaver<? extends ISalvagable>) saver;
                        ISalvagable data = typedSaver.loadFrom(stack);
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
