package com.robertx22.mine_and_slash.itemstack;

import com.robertx22.mine_and_slash.uncommon.MathHelper;

public class PotentialData {

    public int potential = 0;

    public void add(int num) {
        this.potential = MathHelper.clamp(potential + num, 0, 1000000);
    }

    public void spend(int num) {
        this.potential = MathHelper.clamp(potential - num, 0, 1000000);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PotentialData that = (PotentialData) o;
        return potential == that.potential;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(potential);
    }

}
