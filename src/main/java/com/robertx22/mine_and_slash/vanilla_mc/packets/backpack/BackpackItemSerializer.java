package com.robertx22.mine_and_slash.vanilla_mc.packets.backpack;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class BackpackItemSerializer {
    public static void writeItem(RegistryFriendlyByteBuf buf, ItemStack stack) {
        writeItemStack(buf, stack, true);
    }

    public static void writeItemStack(RegistryFriendlyByteBuf buf, ItemStack stack, boolean limitedTag) {
        if (stack.isEmpty()) {
            buf.writeBoolean(false);
        } else {
            buf.writeBoolean(true);
            buf.writeVarInt(stack.getCount());
            ItemStack.STREAM_CODEC.encode(buf, stack.copyWithCount(1));
        }
    }

    public static ItemStack readItem(RegistryFriendlyByteBuf buf) {
        if (!buf.readBoolean()) {
            return ItemStack.EMPTY;
        } else {
            int count = buf.readVarInt();
            ItemStack stack = ItemStack.STREAM_CODEC.decode(buf);
            stack.setCount(count);
            return stack;
        }
    }
}
