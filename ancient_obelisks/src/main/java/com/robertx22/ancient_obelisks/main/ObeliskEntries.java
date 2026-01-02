package com.robertx22.ancient_obelisks.main;

import com.robertx22.ancient_obelisks.block.ObeliskBlock;
import com.robertx22.ancient_obelisks.block.ObeliskMobSpawnerBlock;
import com.robertx22.ancient_obelisks.block.ObeliskRewardBlock;
import com.robertx22.ancient_obelisks.block_entity.ObeliskBE;
import com.robertx22.ancient_obelisks.block_entity.ObeliskMobSpawnerBE;
import com.robertx22.ancient_obelisks.block_entity.ObeliskRewardBE;
import com.robertx22.ancient_obelisks.item.EssenceItem;
import com.robertx22.ancient_obelisks.item.ObeliskMapItem;
import com.robertx22.library_of_exile.database.relic.relic_type.RelicItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ObeliskEntries {
    // registars
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ObelisksMain.MODID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ObelisksMain.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
            .create(Registries.BLOCK_ENTITY_TYPE, ObelisksMain.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TAB = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, ObelisksMain.MODID);

    // blocks
    public static DeferredHolder<Block, ObeliskMobSpawnerBlock> SPAWNER_BLOCK = BLOCKS.register("obelisk_spawner",
            () -> new ObeliskMobSpawnerBlock());
    public static DeferredHolder<Block, ObeliskBlock> OBELISK_BLOCK = BLOCKS.register("obelisk",
            () -> new ObeliskBlock());
    public static DeferredHolder<Block, ObeliskRewardBlock> OBELISK_REWARD_BLOCK = BLOCKS.register("obelisk_reward",
            () -> new ObeliskRewardBlock());

    // block entities
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<ObeliskMobSpawnerBE>> SPAWNER_BE = BLOCK_ENTITIES
            .register("obelisk_spawner",
                    () -> BlockEntityType.Builder.of(ObeliskMobSpawnerBE::new, SPAWNER_BLOCK.get()).build(null));
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<ObeliskBE>> OBELISK_BE = BLOCK_ENTITIES
            .register("obelisk", () -> BlockEntityType.Builder.of(ObeliskBE::new, OBELISK_BLOCK.get()).build(null));
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<ObeliskRewardBE>> OBELISK_REWARD_BE = BLOCK_ENTITIES
            .register("obelisk_reward",
                    () -> BlockEntityType.Builder.of(ObeliskRewardBE::new, OBELISK_REWARD_BLOCK.get()).build(null));

    // items
    public static DeferredHolder<Item, BlockItem> OBELISK_ITEM = ITEMS.register("obelisk",
            () -> new BlockItem(OBELISK_BLOCK.get(), new Item.Properties().stacksTo(64)));
    public static DeferredHolder<Item, ObeliskMapItem> OBELISK_MAP_ITEM = ITEMS.register("obelisk_map",
            () -> new ObeliskMapItem());
    // public static RegistryObject<HomeItem> HOME_TP_BACK = ITEMS.register("home",
    // () -> new HomeItem());

    public static DeferredHolder<Item, EssenceItem> WRATH = ITEMS.register("ancient_wrath",
            () -> new EssenceItem("Wrath"));
    public static DeferredHolder<Item, EssenceItem> ENVY = ITEMS.register("ancient_envy",
            () -> new EssenceItem("Envy"));
    public static DeferredHolder<Item, EssenceItem> GREED = ITEMS.register("ancient_greed",
            () -> new EssenceItem("Greed"));

    public static DeferredHolder<Item, Item> RELIC = ITEMS.register("relic", () -> new RelicItem());

    public static void initDeferred(IEventBus bus) {
        ITEMS.register(bus);
        CREATIVE_TAB.register(bus);
        BLOCKS.register(bus);
        BLOCK_ENTITIES.register(bus);
    }

    public static void init() {

    }
}
