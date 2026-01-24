package com.robertx22.mine_and_slash.compat.mixin;

import com.robertx22.mine_and_slash.config.forge.compat.CompatConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class ItemDamage {

    public static void hurtAndBreak(ItemStack stack, int pAmount, ServerLevel serverLevel, ServerPlayer pEntity,
            Consumer<Item> pOnBroken) {
        int max = CompatConfig.get().itemDuraLossCap();

        if (pAmount > max) {
            pAmount = max;
        }

        if (pAmount < 1) {
            return;
        }

        if (!serverLevel.isClientSide()
                && !pEntity.getAbilities().instabuild) {
            if (stack.isDamageableItem()) {
                // 1.21: damageItem now takes Consumer<Item>
                pAmount = stack.getItem().damageItem(stack, pAmount, pEntity, pOnBroken);
                int newDamage = stack.getDamageValue() + pAmount;
                stack.setDamageValue(newDamage);

                if (newDamage >= stack.getMaxDamage()) {
                    Item item = stack.getItem();
                    pOnBroken.accept(item);
                    stack.shrink(1);
                    pEntity.awardStat(Stats.ITEM_BROKEN.get(item));

                    stack.setDamageValue(0);
                }

            }
        }
    }
}
