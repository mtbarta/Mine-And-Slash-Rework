package com.robertx22.mine_and_slash.database.data.stat_compat;

import com.robertx22.library_of_exile.main.ExileLog;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.database.data.stats.datapacks.stats.AttributeStat;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.mixin_ducks.IDirty;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.uncommon.MathHelper;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.List;
import java.util.function.Consumer;

public class StatCompat implements JsonExileRegistry<StatCompat>, IAutoGson<StatCompat> {

    public static StatCompat SERIALIZER = new StatCompat();

    public String id = "";

    public String attribute_id = "";
    public String enchant_id = "";
    public StatScaling scaling = StatScaling.NONE;
    public String mns_stat_id = "";
    public float conversion = 0.5F;
    public int minimum_cap = 0;
    public int maximum_cap = 100;

    public int per_item_min = 0;
    public int per_item_max = 100;

    public ModType mod_type = ModType.PERCENT;

    public StatCompat(String id) {
        this.id = id + "_compat";
    }

    private StatCompat() {
    }

    public void editAndReg(Consumer<StatCompat> co) {
        co.accept(this);
        addToSerializables(MMORPG.SERIAZABLE_REGISTRATION_INFO);
    }

    private Attribute getAttribute() {
        return BuiltInRegistries.ATTRIBUTE.get(ResourceLocation.parse(attribute_id));
    }

    public boolean isAttributeCompat() {
        return !attribute_id.isEmpty();
    }

    public boolean isEnchantCompat() {
        return !enchant_id.isEmpty();
    }

    /**
     * Sets the enchant_id from a ResourceKey<Enchantment> constant.
     * This is the preferred way to set enchantment IDs in 1.21 as it's type-safe.
     */
    public void setEnchantKey(ResourceKey<Enchantment> key) {
        this.enchant_id = key.location().toString();
    }

    /**
     * Gets the ResourceKey<Enchantment> from the stored enchant_id string.
     * Returns null if enchant_id is empty.
     */
    public ResourceKey<Enchantment> getEnchantKey() {
        if (enchant_id.isEmpty()) {
            return null;
        }
        return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.parse(enchant_id));
    }

    // Updated for 1.21: Accept the Holder<Enchantment> from the caller instead of
    // looking it up
    public ExactStatData getEnchantCompatResult(List<ItemStack> stacks, int lvl, Holder<Enchantment> enchHolder) {
        if (ExileDB.Stats().get(mns_stat_id) instanceof AttributeStat) {
            return null;
        }

        if (enchHolder == null) {
            return null;
        }

        float value = 0;

        for (ItemStack stack : stacks) {
            int enchlvl = EnchantmentHelper.getItemEnchantmentLevel(enchHolder, stack);

            if (enchlvl < 1) {
                continue;
            }
            int val = (int) (enchlvl * conversion);
            value += MathHelper.clamp(val, per_item_min, per_item_max);
        }

        if (value != 0) {
            value = MathHelper.clamp(value, minimum_cap, maximum_cap);
            value = (int) scaling.scale(value, lvl);
            var data = ExactStatData.noScaling(value, mod_type, mns_stat_id);
            return data;
        } else {
            return null;
        }
    }

    public ExactStatData getResult(LivingEntity en, int lvl) {
        try {
            if (ExileDB.Stats().get(mns_stat_id) instanceof AttributeStat) {
                return null;
            }
            var at = getAttribute();

            if (at == null) {
                ExileLog.get().warn(this.attribute_id + " is a null attribute in Stat Compat");
                return null;
            }

            var holder = BuiltInRegistries.ATTRIBUTE.wrapAsHolder(at);
            int val = (int) (en.getAttributeValue(holder) * conversion);
            int value = MathHelper.clamp(val, minimum_cap, maximum_cap);

            if (value != 0) {
                value = (int) scaling.scale(value, lvl);
                var data = ExactStatData.noScaling(value, mod_type, mns_stat_id);
                return data;

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void onTick(Player en) {

        IDirty check = (IDirty) en.getAttributes();

        if (check.isAttribDirty()) {
            check.setAttribDirty(false);
            Load.player(en).cachedStats.STAT_COMPAT.setDirty();
        }
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.STAT_COMPAT;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return 1;
    }

    @Override
    public Class<StatCompat> getClassForSerialization() {
        return StatCompat.class;
    }
}
