package com.robertx22.mine_and_slash.mmorpg.registers;

import com.mojang.serialization.Codec;
import com.robertx22.library_of_exile.components.DataComponentCodecs;
import com.robertx22.mine_and_slash.database.data.loot_chest.base.LootChestData;
import com.robertx22.mine_and_slash.database.data.omen.OmenData;
import com.robertx22.mine_and_slash.itemstack.CustomItemData;
import com.robertx22.mine_and_slash.itemstack.DroppedItemData;
import com.robertx22.mine_and_slash.itemstack.PotentialData;
import com.robertx22.mine_and_slash.maps.MapItemData;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.saveclasses.jewel.JewelItemData;
import com.robertx22.mine_and_slash.saveclasses.prof_tool.ProfessionToolData;
import com.robertx22.mine_and_slash.saveclasses.skill_gem.SkillGemData;
import com.robertx22.mine_and_slash.saveclasses.stat_soul.StatSoulData;
import com.robertx22.mine_and_slash.vanilla_mc.items.crates.gem_crate.LootCrateData;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registry for all custom Data Components used on ItemStacks.
 * Replaces the old NBT-based storage system with Minecraft 1.20.5+ Data
 * Components.
 */
public class DataComponentTypes {

    public static final DeferredRegister.DataComponents REGISTRY = DeferredRegister
            .createDataComponents(Registries.DATA_COMPONENT_TYPE, SlashRef.MODID);

    // Gear and Equipment
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<GearItemData>> GEAR_DATA = register(
            "gear_data",
            GearItemData.class,
            GearItemData::new);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<JewelItemData>> JEWEL_DATA = register(
            "jewel_data",
            JewelItemData.class,
            JewelItemData::new);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SkillGemData>> SKILL_GEM_DATA = register(
            "skill_gem_data",
            SkillGemData.class,
            SkillGemData::new);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StatSoulData>> STAT_SOUL_DATA = register(
            "stat_soul_data",
            StatSoulData.class,
            StatSoulData::new);

    // Profession and Tools
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ProfessionToolData>> TOOL_DATA = register(
            "tool_data",
            ProfessionToolData.class,
            ProfessionToolData::new);

    // Maps and World Data
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<MapItemData>> MAP_DATA = register(
            "map_data",
            MapItemData.class,
            MapItemData::new);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<OmenData>> OMEN_DATA = register(
            "omen_data",
            OmenData.class,
            OmenData::new);

    // Loot and Containers
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<LootChestData>> LOOT_CHEST_DATA = register(
            "loot_chest_data",
            LootChestData.class,
            LootChestData::new);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<LootCrateData>> LOOT_CRATE_DATA = register(
            "loot_crate_data",
            LootCrateData.class,
            LootCrateData::new);

    // Utility Components
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<PotentialData>> POTENTIAL_DATA = register(
            "potential_data",
            PotentialData.class,
            PotentialData::new);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<CustomItemData>> CUSTOM_DATA = register(
            "custom_data",
            CustomItemData.class,
            CustomItemData::new);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<DroppedItemData>> DROPPED_DATA = register(
            "dropped_data",
            DroppedItemData.class,
            DroppedItemData::new);

    /**
     * Register a data component type with automatic GSON-based codecs.
     * This allows existing data classes to work without modification.
     */
    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(
            String name,
            Class<T> clazz,
            java.util.function.Supplier<T> constructor) {

        return REGISTRY.registerComponentType(
                name,
                builder -> {
                    Codec<T> codec = DataComponentCodecs.createGsonCodec(clazz);
                    StreamCodec<ByteBuf, T> streamCodec = DataComponentCodecs.createGsonStreamCodec(clazz,
                            constructor);

                    return builder
                            .persistent(codec)
                            .networkSynchronized(streamCodec)
                            .build();
                });
    }
}
