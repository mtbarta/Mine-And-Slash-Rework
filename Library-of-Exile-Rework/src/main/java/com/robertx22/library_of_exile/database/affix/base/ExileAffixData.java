package com.robertx22.library_of_exile.database.affix.base;

import com.robertx22.library_of_exile.database.affix.types.ExileMobAffix;
import com.robertx22.library_of_exile.database.init.LibDatabase;

import java.util.UUID;

public class ExileAffixData {

    public int perc;
    public String affix;
    public String uuid; // todo do i need uuids?

    public ExileAffixData(String affix, int perc) {
        this.perc = perc;
        this.affix = affix;
        this.uuid = UUID.randomUUID().toString();
    }

    public ExileMobAffix getAffix() {
        return LibDatabase.MobAffixes().get(affix);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ExileAffixData that = (ExileAffixData) o;
        return perc == that.perc &&
                java.util.Objects.equals(affix, that.affix) &&
                java.util.Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(perc, affix, uuid);
    }
}
