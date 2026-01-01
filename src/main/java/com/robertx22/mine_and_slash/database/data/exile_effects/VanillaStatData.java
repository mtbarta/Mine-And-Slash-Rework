package com.robertx22.mine_and_slash.database.data.exile_effects;

import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;

import java.util.UUID;

public class VanillaStatData {

    float val;
    String uuid;
    String id;
    ModType type;

    public static VanillaStatData create(Attribute attri, float val, ModType type, UUID uuid) {
        VanillaStatData data = new VanillaStatData();
        data.id = BuiltInRegistries.ATTRIBUTE.getKey(attri)
                .toString();
        data.uuid = uuid.toString();
        data.type = type;
        data.val = val;
        return data;
    }

    public Attribute getAttribute() {
        return BuiltInRegistries.ATTRIBUTE.get(new ResourceLocation(id));
    }

    public void applyVanillaStats(LivingEntity en, int stacks) {

        UUID modId = UUID.fromString(uuid);
        AttributeModifier mod = new AttributeModifier(modId, id, (double) (val * stacks), type.operation);
        Attribute attri = getAttribute();
        Holder<Attribute> holderAttri = BuiltInRegistries.ATTRIBUTE.wrapAsHolder(attri);

        this.removeVanillaStats(en);

        if (en.getAttribute(holderAttri) != null) {
            AttributeInstance instance = en.getAttribute(holderAttri);
            if (instance.getModifier(modId) == null) {
                instance.addTransientModifier(mod);
            }
        }

    }

    public void removeVanillaStats(LivingEntity en) {
        UUID modId = UUID.fromString(uuid);
        Attribute attri = getAttribute();
        Holder<Attribute> holderAttri = BuiltInRegistries.ATTRIBUTE.wrapAsHolder(attri);

        if (en.getAttribute(holderAttri) != null) {
            AttributeInstance instance = en.getAttribute(holderAttri);
            if (instance.getModifier(modId) != null) {
                instance.removeModifier(modId);
            }
        }
    }
}
