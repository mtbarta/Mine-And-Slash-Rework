package com.robertx22.mine_and_slash.mmorpg.registers.common;

import com.mojang.serialization.MapCodec;
import com.robertx22.mine_and_slash.loot.modifiers.MobGearLootModifier;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class SlashLootModifiers {

    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> REGISTRY = 
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, SlashRef.MODID);

    public static final Supplier<MapCodec<MobGearLootModifier>> MOB_GEAR_DROP = 
            REGISTRY.register("mob_gear_drop", () -> MobGearLootModifier.CODEC);

    public static void init() {
        // Called to initialize the class and register the deferred register
    }
}
