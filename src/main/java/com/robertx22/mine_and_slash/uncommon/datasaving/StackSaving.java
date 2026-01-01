package com.robertx22.mine_and_slash.uncommon.datasaving;

import com.robertx22.library_of_exile.components.ComponentDataSaver;
import com.robertx22.mine_and_slash.database.data.loot_chest.base.LootChestData;
import com.robertx22.mine_and_slash.database.data.omen.OmenData;
import com.robertx22.mine_and_slash.itemstack.CustomItemData;
import com.robertx22.mine_and_slash.itemstack.DroppedItemData;
import com.robertx22.mine_and_slash.itemstack.PotentialData;
import com.robertx22.mine_and_slash.maps.MapItemData;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.mmorpg.registers.DataComponentTypes;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import net.minecraft.core.component.DataComponentType;
import com.robertx22.mine_and_slash.saveclasses.jewel.JewelItemData;
import com.robertx22.mine_and_slash.saveclasses.prof_tool.ProfessionToolData;
import com.robertx22.mine_and_slash.saveclasses.skill_gem.SkillGemData;
import com.robertx22.mine_and_slash.saveclasses.stat_soul.StatSoulData;
import com.robertx22.mine_and_slash.vanilla_mc.items.crates.gem_crate.LootCrateData;

/**
 * Central registry for all ItemStack data components.
 * Migrated from NBT-based ItemstackDataSaver to Data Component API.
 */
public class StackSaving {

        public static ComponentDataSaver<GearItemData> GEARS = of(new ComponentDataSaver<>(
                        SlashRef.MODID + "_gear",
                        GearItemData.class,
                        GearItemData::new,
                        () -> DataComponentTypes.GEAR_DATA.get()));

        public static ComponentDataSaver<SkillGemData> SKILL_GEM = of(new ComponentDataSaver<>(
                        SlashRef.MODID + "_skill_gem",
                        SkillGemData.class,
                        SkillGemData::new,
                        () -> DataComponentTypes.SKILL_GEM_DATA.get()));

        public static ComponentDataSaver<StatSoulData> STAT_SOULS = of(new ComponentDataSaver<>(
                        SlashRef.MODID + "_stat_soul",
                        StatSoulData.class,
                        StatSoulData::new,
                        () -> DataComponentTypes.STAT_SOUL_DATA.get()));

        public static ComponentDataSaver<LootCrateData> GEM_CRATE = of(new ComponentDataSaver<>(
                        SlashRef.MODID + "_loot_crate",
                        LootCrateData.class,
                        LootCrateData::new,
                        () -> DataComponentTypes.LOOT_CRATE_DATA.get()));

        public static ComponentDataSaver<MapItemData> MAP = of(new ComponentDataSaver<>(
                        SlashRef.MODID + "_map",
                        MapItemData.class,
                        MapItemData::new,
                        () -> DataComponentTypes.MAP_DATA.get()));

        public static ComponentDataSaver<JewelItemData> JEWEL = of(new ComponentDataSaver<>(
                        SlashRef.MODID + "_jewel",
                        JewelItemData.class,
                        JewelItemData::new,
                        () -> DataComponentTypes.JEWEL_DATA.get()));

        public static ComponentDataSaver<LootChestData> LOOT_CHEST = of(new ComponentDataSaver<>(
                        SlashRef.MODID + "_loot_chest",
                        LootChestData.class,
                        LootChestData::new,
                        () -> DataComponentTypes.LOOT_CHEST_DATA.get()));

        public static ComponentDataSaver<ProfessionToolData> TOOL = of(new ComponentDataSaver<>(
                        SlashRef.MODID + "_tool_stats",
                        ProfessionToolData.class,
                        ProfessionToolData::new,
                        () -> DataComponentTypes.TOOL_DATA.get()));

        public static ComponentDataSaver<OmenData> OMEN = of(new ComponentDataSaver<>(
                        SlashRef.MODID + "_omen",
                        OmenData.class,
                        OmenData::new,
                        () -> DataComponentTypes.OMEN_DATA.get()));

        public static ComponentDataSaver<PotentialData> POTENTIAL = of(new ComponentDataSaver<>(
                        SlashRef.MODID + "_potential",
                        PotentialData.class,
                        PotentialData::new,
                        () -> DataComponentTypes.POTENTIAL_DATA.get()));

        public static ComponentDataSaver<CustomItemData> CUSTOM_DATA = of(new ComponentDataSaver<>(
                        SlashRef.MODID + "_custom_data",
                        CustomItemData.class,
                        CustomItemData::new,
                        () -> DataComponentTypes.CUSTOM_DATA.get()));

        public static ComponentDataSaver<DroppedItemData> DROPPED = of(new ComponentDataSaver<>(
                        SlashRef.MODID + "_dropped",
                        DroppedItemData.class,
                        DroppedItemData::new,
                        () -> DataComponentTypes.DROPPED_DATA.get()));

        // Simple boolean flags
        public static DataComponentType<Boolean> FREE_SOULED = DataComponentTypes.FREE_SOULED.get();
        public static DataComponentType<String> FORCE_TAG = DataComponentTypes.FORCE_TAG.get();
        public static DataComponentType<Integer> TIER = DataComponentTypes.TIER.get();

        static ComponentDataSaver of(ComponentDataSaver t) {
                return t;
        }

        public static void init() {
                // Initialization handled by DeferredRegister
        }
}
