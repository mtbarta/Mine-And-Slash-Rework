package com.robertx22.dungeon_realm.main;

import com.robertx22.dungeon_realm.block.CustomSpawnTpBlock;
import com.robertx22.dungeon_realm.block.MapDeviceBlock;
import com.robertx22.dungeon_realm.block.UberBossAltarBlock;
import com.robertx22.dungeon_realm.block_entity.MapDeviceBE;
import com.robertx22.dungeon_realm.item.DungeonMapItem;
import com.robertx22.dungeon_realm.item.TeleportBackItem;
import com.robertx22.library_of_exile.database.relic.relic_type.RelicItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DungeonEntries {
    // registars
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DungeonMain.MODID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(DungeonMain.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, DungeonMain.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DungeonMain.MODID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, DungeonMain.MODID);

    // blocks
    public static DeferredHolder<Block, MapDeviceBlock> MAP_DEVICE_BLOCK = BLOCKS.register("map_device", () -> new MapDeviceBlock());
    public static DeferredHolder<Block, CustomSpawnTpBlock> UBER_TELEPORT = BLOCKS.register("uber_teleport", () -> new CustomSpawnTpBlock(() -> DungeonMain.UBER_ARENA));
    public static DeferredHolder<Block, CustomSpawnTpBlock> BOSS_TELEPORT = BLOCKS.register("boss_teleport", () -> new CustomSpawnTpBlock(() -> DungeonMain.ARENA));
    public static DeferredHolder<Block, CustomSpawnTpBlock> REWARD_TELEPORT = BLOCKS.register("reward_teleport", () -> new CustomSpawnTpBlock(() -> DungeonMain.REWARD_ROOM));
    public static DeferredHolder<Block, UberBossAltarBlock> UBER_ALTAR = BLOCKS.register("uber_boss_altar", () -> new UberBossAltarBlock());

    // block entities
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<MapDeviceBE>> MAP_DEVICE_BE = BLOCK_ENTITIES.register("map_device", () -> BlockEntityType.Builder.of(MapDeviceBE::new, MAP_DEVICE_BLOCK.get()).build(null));


    // items
    public static DeferredHolder<Item, BlockItem> MAP_DEVICE_ITEM = ITEMS.register("map_device", () -> new BlockItem(MAP_DEVICE_BLOCK.get(), new Item.Properties().stacksTo(64)));
    public static DeferredHolder<Item, DungeonMapItem> DUNGEON_MAP_ITEM = ITEMS.register("dungeon_map", () -> new DungeonMapItem());
    public static DeferredHolder<Item, Item> UBER_FRAGMENT = ITEMS.register("uber_fragment", () -> new Item(new Item.Properties().stacksTo(64)));
    public static DeferredHolder<Item, TeleportBackItem> HOME_TP_BACK = ITEMS.register("home_pearl", () -> new TeleportBackItem());
    public static DeferredHolder<Item, Item> RELIC_KEY = ITEMS.register("relic_key", () -> new Item(new Item.Properties().stacksTo(1)));
    public static DeferredHolder<Item, Item> RELIC_ITEM = ITEMS.register("general_relic", () -> new RelicItem());


    public static void initDeferred(IEventBus bus) {
        ITEMS.register(bus);
        CREATIVE_TAB.register(bus);
        BLOCKS.register(bus);
        BLOCK_ENTITIES.register(bus);
        DungeonMenuTypes.register(bus);
    }

    public static void init() {

    }
}
