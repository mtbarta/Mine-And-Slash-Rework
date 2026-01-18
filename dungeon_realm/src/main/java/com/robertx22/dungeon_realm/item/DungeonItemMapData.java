package com.robertx22.dungeon_realm.item;

import com.robertx22.dungeon_realm.structure.DungeonMapCapability;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

// todo
public class DungeonItemMapData {

    public int x = 0;
    public int z = 0;

    public int bonus_contents = 1;

    public boolean uber = false;
    public String dungeon;

    public ChunkPos getOrSetStartPos(Level world, ItemStack stack) {

        if (x == 0 && z == 0) {
            var start = DungeonMapCapability.get(world).data.counter.getNextAndIncrement();
            x = start.x;
            z = start.z;
        }
        DungeonItemNbt.DUNGEON_MAP.saveTo(stack, this);
        return new ChunkPos(x, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DungeonItemMapData that = (DungeonItemMapData) o;
        return x == that.x &&
                z == that.z &&
                bonus_contents == that.bonus_contents &&
                uber == that.uber &&
                java.util.Objects.equals(dungeon, that.dungeon);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(x, z, bonus_contents, uber, dungeon);
    }

}
