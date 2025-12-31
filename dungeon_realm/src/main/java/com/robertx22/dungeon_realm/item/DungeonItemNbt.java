package com.robertx22.dungeon_realm.item;

import com.robertx22.dungeon_realm.item.relic.RelicItemData;
import com.robertx22.dungeon_realm.main.DungeonMain;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;

public class DungeonItemNbt {

    public static ItemstackDataSaver<DungeonItemMapData> DUNGEON_MAP = ItemstackDataSaver.createWithGson(
            DungeonMain.MODID + "_dungeon_map", DungeonItemMapData.class, () -> new DungeonItemMapData());
    public static ItemstackDataSaver<RelicItemData> RELIC = ItemstackDataSaver.createWithGson(
            DungeonMain.MODID + "_relic",
            RelicItemData.class, () -> new RelicItemData());

}
