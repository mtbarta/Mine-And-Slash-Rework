package com.robertx22.the_harvest.block_entity;

import com.robertx22.the_harvest.main.HarvestEntries;
import com.robertx22.the_harvest.structure.HarvestMapCap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class HarvestBE extends BlockEntity {

    public boolean gaveMap = false;
    public int x = -1;
    public int z = -1;
    public String currentWorldUUID = "";

    public boolean isActivated() {
        if (currentWorldUUID.isEmpty() || !currentWorldUUID
                .equals(HarvestMapCap.get(ServerLifecycleHooks.getCurrentServer().overworld()).data.data.uuid)) {
            return false;
        }
        return x != -1 || z != -1;
    }

    public void setGaveMap() {
        this.gaveMap = true;
        this.setChanged();
    }

    public HarvestBE(BlockPos pPos, BlockState pBlockState) {
        super(HarvestEntries.HARVEST_BE.get(), pPos, pBlockState);

    }

    @Override
    protected void saveAdditional(CompoundTag nbt, net.minecraft.core.HolderLookup.Provider provider) {
        super.saveAdditional(nbt, provider);
        nbt.putBoolean("gave", gaveMap);
        nbt.putInt("xp", x);
        nbt.putInt("zp", z);
        nbt.putString("uid", currentWorldUUID);

    }

    @Override
    public void loadAdditional(CompoundTag pTag, net.minecraft.core.HolderLookup.Provider provider) {
        super.loadAdditional(pTag, provider);
        this.gaveMap = pTag.getBoolean("gave");
        this.x = pTag.getInt("xp");
        this.z = pTag.getInt("zp");
        this.currentWorldUUID = pTag.getString("uid");

    }

}
