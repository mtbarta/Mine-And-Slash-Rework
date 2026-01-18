package com.robertx22.dungeon_realm.item.relic;

import com.robertx22.dungeon_realm.main.DungeonMain;
import com.robertx22.library_of_exile.database.init.LibDatabase;
import com.robertx22.library_of_exile.database.relic.relic_rarity.RelicRarity;
import com.robertx22.library_of_exile.database.relic.relic_type.RelicType;

import java.util.ArrayList;
import java.util.List;

public class RelicItemData {

    public List<RelicAffixData> affixes = new ArrayList<>();

    public String rar = "common";

    public String type = DungeonMain.MODID;

    public RelicRarity getRarity() {
        return LibDatabase.RelicRarities().get(rar);
    }

    public RelicType getType() {
        return LibDatabase.RelicTypes().get(type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RelicItemData that = (RelicItemData) o;
        return java.util.Objects.equals(affixes, that.affixes) &&
                java.util.Objects.equals(rar, that.rar) &&
                java.util.Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(affixes, rar, type);
    }

}
