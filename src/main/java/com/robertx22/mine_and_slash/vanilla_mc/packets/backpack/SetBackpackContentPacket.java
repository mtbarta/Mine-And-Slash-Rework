package com.robertx22.mine_and_slash.vanilla_mc.packets.backpack;

import java.util.List;

import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;

import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class SetBackpackContentPacket extends MyPacket<SetBackpackContentPacket> {

    // Like ClientboundContainerSetContentPacket, but sends stack sizes as varints

    public int containerId;
    public int stateId;
    public List<ItemStack> items;
    public ItemStack carriedItem;

    public SetBackpackContentPacket() {

    }

    public SetBackpackContentPacket(int containerId, int stateId, NonNullList<ItemStack> items, ItemStack carriedItem) {
        this.containerId = containerId;
        this.stateId = stateId;
        this.items = NonNullList.withSize(items.size(), ItemStack.EMPTY);

        for (int index = 0; index < items.size(); ++index) {
            this.items.set(index, items.get(index).copy());
        }

        this.carriedItem = carriedItem.copy();
    }

    @Override
    public ResourceLocation getIdentifier() {
        return ResourceLocation.fromNamespaceAndPath(SlashRef.MODID, "setbackpackcontent");
    }

    @Override
    public void loadFromData(RegistryFriendlyByteBuf tag) {
        containerId = tag.readUnsignedByte();
        stateId = tag.readVarInt();
        items = tag.readCollection(NonNullList::createWithCapacity,
                (buf) -> BackpackItemSerializer.readItem((RegistryFriendlyByteBuf) buf));
        carriedItem = ItemStack.OPTIONAL_STREAM_CODEC.decode(tag);
    }

    @Override
    public void saveToData(RegistryFriendlyByteBuf tag) {
        tag.writeByte(containerId);
        tag.writeVarInt(stateId);
        tag.writeCollection(items,
                (buf, item) -> BackpackItemSerializer.writeItem((RegistryFriendlyByteBuf) buf, item));
        ItemStack.OPTIONAL_STREAM_CODEC.encode(tag, carriedItem);
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        AbstractContainerMenu containerMenu = ClientOnly.getContainerMenu();

        if (containerMenu.containerId == containerId) {
            containerMenu.initializeContents(containerId, items, carriedItem);
        }
    }

    @Override
    public MyPacket<SetBackpackContentPacket> newInstance() {
        return new SetBackpackContentPacket();
    }
}
