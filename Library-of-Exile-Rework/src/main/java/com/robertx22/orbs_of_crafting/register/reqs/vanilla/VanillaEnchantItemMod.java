package com.robertx22.orbs_of_crafting.register.reqs.vanilla;

import com.robertx22.library_of_exile.localization.ExileTranslation;
import com.robertx22.library_of_exile.localization.TranslationBuilder;
import com.robertx22.library_of_exile.localization.TranslationType;
import com.robertx22.library_of_exile.main.Ref;
import com.robertx22.orbs_of_crafting.misc.StackHolder;
import com.robertx22.orbs_of_crafting.register.mods.base.ItemModification;
import com.robertx22.orbs_of_crafting.register.mods.base.ItemModificationResult;
import com.robertx22.orbs_of_crafting.register.mods.base.VanillaItemModSers;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

/**
 * Applies random enchantments at a specified level to an item.
 * In NeoForge 1.21, EnchantWithLevelsFunction requires HolderLookup.Provider
 * which
 * is not available at static initialization. This class defers the enchantment
 * application to runtime when the player/level is available.
 */
public class VanillaEnchantItemMod extends ItemModification {

    transient String desc;
    public int enchantLevel;

    public VanillaEnchantItemMod(String id, String desc, int enchantLevel) {
        super(VanillaItemModSers.VANILLA, id);
        this.desc = desc;
        this.enchantLevel = enchantLevel;
    }

    @Override
    public Class<?> getClassForSerialization() {
        return VanillaEnchantItemMod.class;
    }

    @Override
    public OutcomeType getOutcomeType() {
        return OutcomeType.NEUTRAL;
    }

    @Override
    public void applyINTERNAL(StackHolder stack, ItemModificationResult r) {
    }

    @Override
    public void applyMod(Player p, StackHolder stack, ItemModificationResult r) {
        if (p.level() instanceof ServerLevel serverLevel) {
            var registryAccess = serverLevel.registryAccess();
            var enchantRegistry = registryAccess.registryOrThrow(Registries.ENCHANTMENT);

            // Apply random enchantments at the specified level
            RandomSource random = p.getRandom();
            stack.stack = EnchantmentHelper.enchantItem(
                    random,
                    stack.stack,
                    enchantLevel,
                    registryAccess,
                    java.util.Optional.empty());
        }
    }

    @Override
    public MutableComponent getDescWithParams() {
        return getTranslation(TranslationType.DESCRIPTION).getTranslatedName();
    }

    @Override
    public TranslationBuilder createTranslationBuilder() {
        return TranslationBuilder.of(Ref.MODID)
                .desc(ExileTranslation.registry(this, desc));
    }
}
