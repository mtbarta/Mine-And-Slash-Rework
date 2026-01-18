package com.robertx22.ancient_obelisks.main;

import com.robertx22.ancient_obelisks.configs.ObeliskConfig;
import com.robertx22.ancient_obelisks.structure.ObeliskMapData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ObeliskMobTierStats {

    // In NeoForge 1.21, AttributeModifier uses ResourceLocation instead of UUID
    public static ResourceLocation DMG = ResourceLocation.fromNamespaceAndPath(ObelisksMain.MODID, "mob_tier_dmg");
    public static ResourceLocation HP = ResourceLocation.fromNamespaceAndPath(ObelisksMain.MODID, "mob_tier_hp");

    public static AttributeModifier hpMod(int tier) {
        float hp = ObeliskConfig.get().MOB_HP_PER_TIER.get().floatValue() * tier;

        // NeoForge 1.21: constructor is (ResourceLocation id, double amount, Operation
        // operation)
        AttributeModifier mod = new AttributeModifier(
                HP,
                (double) hp,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        return mod;
    }

    public static AttributeModifier dmgMod(int tier) {
        float dmg = ObeliskConfig.get().MOB_DMG_PER_TIER.get().floatValue() * tier;

        // NeoForge 1.21: constructor is (ResourceLocation id, double amount, Operation
        // operation)
        AttributeModifier mod = new AttributeModifier(
                DMG,
                (double) dmg,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        return mod;
    }

    public static void tryApply(LivingEntity en, ObeliskMapData data) {

        try {
            int tier = data.item.tier;

            if (tier > 0) {
                AttributeInstance maxHealthAttribute = en.getAttribute(Attributes.MAX_HEALTH);
                // NeoForge 1.21: getModifier takes ResourceLocation
                if (maxHealthAttribute != null && maxHealthAttribute.getModifier(HP) == null) {
                    maxHealthAttribute.addPermanentModifier(hpMod(tier));
                    en.setHealth((int) maxHealthAttribute.getValue());
                }

                AttributeInstance attackDamageAttribute = en.getAttribute(Attributes.ATTACK_DAMAGE);
                // NeoForge 1.21: getModifier takes ResourceLocation
                if (attackDamageAttribute != null && attackDamageAttribute.getModifier(DMG) == null) {
                    attackDamageAttribute.addPermanentModifier(dmgMod(tier));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
