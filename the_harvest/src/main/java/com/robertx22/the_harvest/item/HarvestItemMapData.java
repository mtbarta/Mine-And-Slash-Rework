package com.robertx22.the_harvest.item;

import com.robertx22.the_harvest.structure.HarvestMapCap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

public record HarvestItemMapData(int x, int z, boolean relic) {

    public HarvestItemMapData() {
        this(0, 0, false);
    }

    public ChunkPos getOrSetStartPos(Level world, ItemStack stack) {
        if (x == 0 && z == 0) {
            var start = HarvestMapCap.get(world).data.counter.getNextAndIncrement();
            var newData = new HarvestItemMapData(start.x, start.z, this.relic);
            HarvestItemNbt.HARVEST_MAP.saveTo(stack, newData);
            return new ChunkPos(start.x, start.z);
        }
        HarvestItemNbt.HARVEST_MAP.saveTo(stack, this);
        return new ChunkPos(x, z);
    }

    public boolean relic() {
        return this.relic;
    }
}
