package com.robertx22.mine_and_slash.compat.mixin;

import com.robertx22.mine_and_slash.config.forge.compat.CompatConfig;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class ItemDamage {

    public static <T extends LivingEntity> void hurtAndBreak(ItemStack stack, int pAmount, T pEntity,
            Consumer<T> pOnBroken) {
        int max = CompatConfig.get().itemDuraLossCap();

        if (pAmount > max) {
            pAmount = max;
        }

        if (pAmount < 1) {
            return;
        }

        if (!pEntity.level().isClientSide
                && (!(pEntity instanceof Player) || !((Player) pEntity).getAbilities().instabuild)) {
            if (stack.isDamageableItem()) {
                // 1.21: damageItem now takes Consumer<Item> instead of a Runnable-style
                // callback
                pAmount = stack.getItem().damageItem(stack, pAmount, pEntity, item -> pOnBroken.accept(pEntity));
                int newDamage = stack.getDamageValue() + pAmount;
                stack.setDamageValue(newDamage);

                if (newDamage >= stack.getMaxDamage()) {
                    pOnBroken.accept(pEntity);
                    Item item = stack.getItem();
                    stack.shrink(1);
                    if (pEntity instanceof Player) {
                        ((Player) pEntity).awardStat(Stats.ITEM_BROKEN.get(item));
                    }

                    stack.setDamageValue(0);
                }

            }
        }
    }
}
