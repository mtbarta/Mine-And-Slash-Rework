package com.robertx22.mine_and_slash.database.data.spells.components.actions;

import com.robertx22.mine_and_slash.database.data.spells.components.MapHolder;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.PlayerUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Arrays;
import java.util.Collection;

public class GiveArrowsIfNotInfinity extends SpellAction {

    public GiveArrowsIfNotInfinity() {
        super(Arrays.asList());
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {
        if (ctx.caster instanceof Player p) {
            // Get the Holder<Enchantment> from the level's registry (1.21 API change)
            Holder<Enchantment> infinityHolder = p.level()
                    .registryAccess()
                    .registryOrThrow(Registries.ENCHANTMENT)
                    .getHolder(Enchantments.INFINITY)
                    .orElse(null);

            if (infinityHolder != null) {
                int enchLevel = EnchantmentHelper.getItemEnchantmentLevel(infinityHolder,
                        ctx.caster.getMainHandItem());
                if (enchLevel < 1) {
                    if (p.getInventory().countItem(Items.ARROW) < 64) {
                        PlayerUtils.giveItem(new ItemStack(Items.ARROW, 64), p);
                    }
                }
            }
        }
    }

    public MapHolder create() {
        MapHolder d = new MapHolder();
        d.type = GUID();
        return d;
    }

    @Override
    public String GUID() {
        return "give_arrows_if_no_infi";
    }
}
