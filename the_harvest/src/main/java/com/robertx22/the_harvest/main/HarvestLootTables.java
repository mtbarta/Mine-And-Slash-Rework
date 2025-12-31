package com.robertx22.the_harvest.main;

import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class HarvestLootTables {
    public static ResourceLocation LOOT_ID = HarvestMain.id("harvest_loot");
    public static net.minecraft.resources.ResourceKey<LootTable> LOOT = net.minecraft.resources.ResourceKey
            .create(net.minecraft.core.registries.Registries.LOOT_TABLE, LOOT_ID);

    public static class Provider implements LootTableSubProvider {

        @Override
        public void generate(net.minecraft.core.HolderLookup.Provider registries,
                BiConsumer<net.minecraft.resources.ResourceKey<LootTable>, LootTable.Builder> output) {

            var essB = LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1, 2))
                            .add(LootItem.lootTableItem(HarvestEntries.GREEN.get()))
                            .add(LootItem.lootTableItem(HarvestEntries.BLUE.get()))
                            .add(LootItem.lootTableItem(HarvestEntries.PURPLE.get())))
                    .withPool(
                            LootPool.lootPool().setRolls(UniformGenerator.between(2, 4))
                                    .add(LootItem.lootTableItem(Items.WHEAT))
                                    .add(LootItem.lootTableItem(Items.BEETROOT))
                                    .add(LootItem.lootTableItem(Items.MELON)));

            output.accept(LOOT, essB);

        }
    }

}
