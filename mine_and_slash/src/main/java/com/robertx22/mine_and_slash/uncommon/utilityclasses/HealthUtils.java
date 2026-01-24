package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.config.forge.compat.CompatConfig;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import net.minecraft.resources.ResourceLocation;

public class HealthUtils {
    // In NeoForge 1.21, AttributeModifier uses ResourceLocation instead of UUID
    private static final ResourceLocation HEARTS_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mns",
            "hearts_modifier");

    static AttributeModifier getHeartsAttributeMod(float num) {
        return new AttributeModifier(
                HEARTS_MODIFIER_ID,
                num,
                AttributeModifier.Operation.ADD_VALUE);
    }

    public static void setHealth(LivingEntity entity, float health) {
        AttributeInstance healthAttribute = entity.getAttribute(Attributes.MAX_HEALTH);
        AttributeModifier mod = healthAttribute.getModifier(HEARTS_MODIFIER_ID);
        if (mod != null) {
            healthAttribute.removeModifier(mod.id());
            healthAttribute.addPermanentModifier(getHeartsAttributeMod(health));
        } else {
            healthAttribute.addPermanentModifier(getHeartsAttributeMod(health));
        }

    }

    public static void addHealth(LivingEntity entity, float health) {
        AttributeInstance healthAttribute = entity.getAttribute(Attributes.MAX_HEALTH);
        AttributeModifier mod = healthAttribute.getModifier(HEARTS_MODIFIER_ID);
        if (mod != null) {
            healthAttribute.removeModifier(mod.id());
            healthAttribute.addPermanentModifier(getHeartsAttributeMod((float) (mod.amount() + health)));
        } else {
            healthAttribute.addPermanentModifier(getHeartsAttributeMod(health));
        }

    }

    /*
     * public static void removeHeartsOnSpawnIfNotLiteMode(LivingEntity en) {
     * if (!CompatConfig.get().healthSystem().usesVanillaHearts()) {
     * 
     * }
     * }
     * 
     */

    public static void addHearts(LivingEntity en) {

        if (CompatConfig.get().healthSystem().usesVanillaHearts()) {

            var curmax = en.getMaxHealth();

            var data = Load.Unit(en);

            int cur = (int) data.getUnit().healthData().getValue();

            if (data.lastHealth != cur) {
                data.lastHealth = cur;
            }

            var mod = getHeartsAttributeMod(cur);

            var at = en.getAttribute(Attributes.MAX_HEALTH);

            if (en.getAttributes().hasModifier(Attributes.MAX_HEALTH, mod.id())) {
                at.removeModifier(mod.id());
            }
            data.heartsWithoutMnsHealth = (int) en.getMaxHealth();

            at.addPermanentModifier(mod);

            var aftermax = en.getMaxHealth();

            if (aftermax > curmax) {
                float toheal = aftermax - curmax;
                en.heal(toheal); // todo maybe use sethealth here instead
            }
        } else {
            var mod = getHeartsAttributeMod(0);
            var at = en.getAttribute(Attributes.MAX_HEALTH);
            if (en.getAttributes().hasModifier(Attributes.MAX_HEALTH, mod.id())) {
                at.removeModifier(mod.id());
            }
        }
    }

    public static void heal(LivingEntity en, float heal) {
        en.heal(heal);
    }

    public static float realToVanilla(LivingEntity en, float dmg) {
        if (CompatConfig.get().healthSystem().usesVanillaHearts()) {
            return dmg;
        }
        float multi = dmg / getMaxHealth(en);
        float max = en.getMaxHealth();
        float total = multi * max;
        return total;
    }

    public static float getMaxHealth(LivingEntity en) {

        if (CompatConfig.get().healthSystem().usesVanillaHearts()) {
            return en.getMaxHealth();
        }

        EntityData data = Load.Unit(en);

        if (en.level().isClientSide) {
            return data.getSyncedMaxHealth(); // for client, health needs to be synced
        }
        try {
            return data.getUnit().healthData().getValue();
        } catch (Exception e) {
            return 1;
        }

    }

    public static int getCurrentHealth(LivingEntity entity) {
        if (CompatConfig.get().healthSystem().usesVanillaHearts()) {
            return (int) entity.getHealth();
        }

        float multi = entity.getHealth() / entity.getMaxHealth();
        float max = getMaxHealth(entity);
        return (int) (max * multi);
    }

    public static int getCurrentMagicShield(LivingEntity entity) {
        return (int) Load.Unit(entity).getResources().getMagicShield();
    }

    public static int getCurrentHealthPlusMagicShield(LivingEntity entity) {
        return (int) (getCurrentHealth(entity) + Load.Unit(entity).getResources().getMagicShield());
    }

    public static int getMaxHealthPlusMagicShield(LivingEntity entity) {
        int num = (int) (getMaxHealth(entity) + Load.Unit(entity).getUnit().magicShieldData().getValue());
        if (num <= 0) {
            return 1;
        }
        return num;
    }

    public static float getHealthBarPercent(LivingEntity entity) {
        return Math.min(1.0f, getCurrentHealthPlusMagicShield(entity) * 1.0f / getMaxHealthPlusMagicShield(entity));
    }

}
