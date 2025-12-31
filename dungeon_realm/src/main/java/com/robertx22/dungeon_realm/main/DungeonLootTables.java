package com.robertx22.dungeon_realm.main;

import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

public class DungeonLootTables {

    public static final ResourceLocation TIER_1_DUNGEON_CHEST_ID = new ResourceLocation(DungeonMain.MODID,
            "chests/tier_1_dungeon");
    public static final ResourceLocation TIER_2_DUNGEON_CHEST_ID = new ResourceLocation(DungeonMain.MODID,
            "chests/tier_2_dungeon");
    public static final ResourceLocation TIER_3_DUNGEON_CHEST_ID = new ResourceLocation(DungeonMain.MODID,
            "chests/tier_3_dungeon");
    public static final ResourceLocation TIER_4_DUNGEON_CHEST_ID = new ResourceLocation(DungeonMain.MODID,
            "chests/tier_4_dungeon");
    public static final ResourceLocation TIER_5_DUNGEON_CHEST_ID = new ResourceLocation(DungeonMain.MODID,
            "chests/tier_5_dungeon");

    public static final net.minecraft.resources.ResourceKey<LootTable> TIER_1_DUNGEON_CHEST = net.minecraft.resources.ResourceKey
            .create(net.minecraft.core.registries.Registries.LOOT_TABLE, TIER_1_DUNGEON_CHEST_ID);
    public static final net.minecraft.resources.ResourceKey<LootTable> TIER_2_DUNGEON_CHEST = net.minecraft.resources.ResourceKey
            .create(net.minecraft.core.registries.Registries.LOOT_TABLE, TIER_2_DUNGEON_CHEST_ID);
    public static final net.minecraft.resources.ResourceKey<LootTable> TIER_3_DUNGEON_CHEST = net.minecraft.resources.ResourceKey
            .create(net.minecraft.core.registries.Registries.LOOT_TABLE, TIER_3_DUNGEON_CHEST_ID);
    public static final net.minecraft.resources.ResourceKey<LootTable> TIER_4_DUNGEON_CHEST = net.minecraft.resources.ResourceKey
            .create(net.minecraft.core.registries.Registries.LOOT_TABLE, TIER_4_DUNGEON_CHEST_ID);
    public static final net.minecraft.resources.ResourceKey<LootTable> TIER_5_DUNGEON_CHEST = net.minecraft.resources.ResourceKey
            .create(net.minecraft.core.registries.Registries.LOOT_TABLE, TIER_5_DUNGEON_CHEST_ID);

    public static net.minecraft.resources.ResourceKey<LootTable> randomMapLoot() {
        return RandomUtils.randomFromList(tieredMapLoot());
    }

    public static List<net.minecraft.resources.ResourceKey<LootTable>> tieredMapLoot() {
        return Arrays.asList(
                TIER_1_DUNGEON_CHEST,
                TIER_2_DUNGEON_CHEST,
                TIER_3_DUNGEON_CHEST,
                TIER_4_DUNGEON_CHEST,
                TIER_5_DUNGEON_CHEST);
    }

    public static class DungeonLootTableProvider implements LootTableSubProvider {

        @Override
        public void generate(net.minecraft.core.HolderLookup.Provider registries,
                BiConsumer<net.minecraft.resources.ResourceKey<LootTable>, LootTable.Builder> output) {

        }
    }

}
