package com.robertx22.mine_and_slash.itemstack;

public class DroppedItemData {
    public boolean forcedDrop = false;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        DroppedItemData other = (DroppedItemData) obj;
        return forcedDrop == other.forcedDrop;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(forcedDrop);
    }
}
