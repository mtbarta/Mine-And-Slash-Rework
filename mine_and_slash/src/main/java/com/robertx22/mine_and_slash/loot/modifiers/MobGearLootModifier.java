package com.robertx22.mine_and_slash.loot.modifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class MobGearLootModifier extends LootModifier {

    public static final MapCodec<MobGearLootModifier> CODEC = RecordCodecBuilder
            .mapCodec(inst -> LootModifier.codecStart(inst).and(inst.group(
                    Codec.FLOAT.fieldOf("base_chance").forGetter(m -> m.baseChance),
                    Codec.FLOAT.fieldOf("level_multiplier").forGetter(m -> m.levelMultiplier),
                    Codec.INT.fieldOf("min_level").forGetter(m -> m.minLevel))).apply(inst, MobGearLootModifier::new));

    private final float baseChance;
    private final float levelMultiplier;
    private final int minLevel;

    public MobGearLootModifier(LootItemCondition[] conditions, float baseChance, float levelMultiplier, int minLevel) {
        super(conditions);
        this.baseChance = baseChance;
        this.levelMultiplier = levelMultiplier;
        this.minLevel = minLevel;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        Entity thisEntity = context.getParamOrNull(LootContextParams.THIS_ENTITY);
        Entity killerEntity = context.getParamOrNull(LootContextParams.ATTACKING_ENTITY);

        if (!(thisEntity instanceof LivingEntity mob)) {
            return generatedLoot;
        }

        if (!(killerEntity instanceof ServerPlayer player)) {
            return generatedLoot;
        }

        // Get mob level from our mod's entity data
        var mobData = Load.Unit(mob);
        if (mobData == null) {
            return generatedLoot;
        }

        int mobLevel = mobData.getLevel();
        if (mobLevel < minLevel) {
            return generatedLoot;
        }

        // Calculate drop chance based on mob level
        float dropChance = baseChance + (mobLevel * levelMultiplier);

        if (context.getRandom().nextFloat() > dropChance) {
            return generatedLoot;
        }

        // Generate gear using our mod's blueprint system
        try {
            LootInfo info = LootInfo.ofMobKilled(player, mob);
            GearBlueprint blueprint = new GearBlueprint(info);
            ItemStack gear = blueprint.createStack();

            if (!gear.isEmpty()) {
                generatedLoot.add(gear);
            }
        } catch (Exception e) {
            // Log and continue - don't break loot generation
            e.printStackTrace();
        }

        return generatedLoot;
    }
}
