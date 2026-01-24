package com.robertx22.mine_and_slash.mixins;

import com.robertx22.mine_and_slash.compat.mixin.ItemDamage;
import com.robertx22.mine_and_slash.config.forge.compat.CompatConfig;
import com.robertx22.mine_and_slash.mixin_methods.TooltipMethod;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Consumer;

@Mixin(value = ItemStack.class, priority = Integer.MAX_VALUE)
public abstract class ItemStackMixin {
    public ItemStackMixin() {
    }

    @Inject(method = "hurtAndBreak", at = @At(value = "HEAD"), cancellable = true)
    public void hookLoot(int pAmount, ServerLevel serverLevel, ServerPlayer pEntity, Consumer<Item> pOnBroken,
            CallbackInfo ci) {

        try {

            ItemStack stack = (ItemStack) (Object) this;

            if (CompatConfig.get().capItemDuraLoss()) {
                ci.cancel();
                ItemDamage.hurtAndBreak(stack, pAmount, serverLevel, pEntity, pOnBroken);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Inject(method = "getRarity", at = @At(value = "HEAD"), cancellable = true)
    public void hookLoot(CallbackInfoReturnable<Rarity> cir) {

        try {

            ItemStack stack = (ItemStack) (Object) this;
            // todo
            if (StackSaving.GEARS.has(stack)) {
                var rar = StackSaving.GEARS.loadFrom(stack).getRarity().getVanillaRarity();
                if (rar != null) {
                    cir.setReturnValue(rar);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // copied from TooltipCallback fabric event
    @Inject(method = { "getTooltipLines" }, at = { @At("RETURN") })
    private void getTooltip(Item.TooltipContext context, Player entity, TooltipFlag tooltipContext,
            CallbackInfoReturnable<List<Component>> list) {
        ItemStack stack = (ItemStack) (Object) this;
        TooltipMethod.getTooltip(stack, entity, tooltipContext, list);
    }

}
