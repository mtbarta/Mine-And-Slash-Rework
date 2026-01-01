package com.robertx22.mine_and_slash.database.data.profession;

import com.robertx22.mine_and_slash.uncommon.utilityclasses.LevelUtils;
import com.robertx22.temp.SkillItemTier;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import net.minecraft.world.item.ItemStack;

public class LeveledItem {

    // todo remove levels, not used

    public static int getLevel(ItemStack stack) {
        if (stack.has(StackSaving.TIER)) {
            return LevelUtils.tierToLevel(getTier(stack).tier).getMinLevel();
        }
        return 0;
    }

    public static int getTierNum(ItemStack stack) {
        return stack.getOrDefault(StackSaving.TIER, 0);
    }

    public static SkillItemTier getTier(ItemStack stack) {
        return SkillItemTier.of(getTierNum(stack));
    }

    public static void setTier(ItemStack stack, int lvl) {
        stack.set(StackSaving.TIER, lvl);
    }
}
