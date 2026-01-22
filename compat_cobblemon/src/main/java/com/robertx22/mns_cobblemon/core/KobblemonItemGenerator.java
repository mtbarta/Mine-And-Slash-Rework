package com.robertx22.mns_cobblemon.core;

import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import net.minecraft.world.item.ItemStack;

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
