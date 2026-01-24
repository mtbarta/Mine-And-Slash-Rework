package com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.baubles;

import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorItem;
import java.util.Map;
import net.minecraft.core.Holder;

public class CheckArmorApi {
    public int check(ArmorMaterial mat) {
        net.minecraft.world.item.ArmorItem item = new net.minecraft.world.item.ArmorItem(
                net.minecraft.core.Holder.direct(mat), net.minecraft.world.item.ArmorItem.Type.CHESTPLATE,
                new net.minecraft.world.item.Item.Properties());
        return item.getMaxDamage(item.getDefaultInstance());
    }
}
