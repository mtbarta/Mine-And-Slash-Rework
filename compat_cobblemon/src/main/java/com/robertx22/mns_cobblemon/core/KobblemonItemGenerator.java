package com.robertx22.mns_cobblemon.core;

import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class KobblemonItemGenerator {

    public static ItemStack generateHeldItem(int level) {
        // Create LootInfo for generation context (using explicit level)
        LootInfo info = LootInfo.ofLevel(level);

        // Create Blueprint
        GearBlueprint blueprint = new GearBlueprint(info);

        // Force the type to be our Pokemon Held Item type
        blueprint.setType(KobblemonGearTypes.POKEMON_HELD_ITEM.GUID());

        // Generate the stack
        return blueprint.createStack();
    }
}
