package com.robertx22.mine_and_slash.capability.player;

import com.robertx22.mine_and_slash.capability.player.data.Backpacks;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashAttachments;
import com.robertx22.library_of_exile.components.ICap;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class PlayerBackpackData implements ICap {


    public static final ResourceLocation RESOURCE = new ResourceLocation(SlashRef.MODID, "backpacks");

    public static PlayerBackpackData get(LivingEntity entity) {
        return entity.getData(SlashAttachments.PLAYER_BACKPACK_DATA);
    }

    transient Player player;
    private Backpacks data;

    public PlayerBackpackData(Player player) {
        this.player = player;
        this.data = new Backpacks(player);
    }

    public Backpacks getBackpacks() {
        return data;
    }


    @Override
    public CompoundTag serializeNBT() {

        CompoundTag nbt = new CompoundTag();

        for (Backpacks.BackpackType type : Backpacks.BackpackType.values()) {
            try {
                nbt.put(type.id, data.getInv(type).createTag());
            } catch (Exception e) {
                // throw new RuntimeException(e);
            }
        }

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

        for (Backpacks.BackpackType type : Backpacks.BackpackType.values()) {
            try {
                if (nbt.contains(type.id)) {
                    data.getInv(type).fromTag(nbt.getList(type.id, 10)); // todo
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void syncToClient(Player player) {
        // dont sync backpacks to client
        //  Packets.sendToClient(player, new SyncPlayerCapToClient(player, this.getCapIdForSyncing()));
    }

    public static final String ID = "backpack_data";
    @Override
    public String getCapIdForSyncing() {
        return ID;
    }

}
