package com.robertx22.the_harvest.main;

import com.robertx22.library_of_exile.database.relic.relic_type.RelicItem;
import com.robertx22.the_harvest.block.HarvestBlock;
import com.robertx22.the_harvest.block.HarvestMobSpawnerBlock;
import com.robertx22.the_harvest.block_entity.HarvestBE;
import com.robertx22.the_harvest.block_entity.HarvestSpawnerBE;
import com.robertx22.the_harvest.item.HarvestMapItem;
import com.robertx22.the_harvest.item.MatItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.fml.common.Mod;

public class HarvestEntries {
    // registars
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(HarvestMain.MODID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(HarvestMain.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, HarvestMain.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HarvestMain.MODID);

    // blocks
    public static DeferredHolder<Block, HarvestMobSpawnerBlock> SPAWNER_BLOCK = BLOCKS.register("harvest_spawner", () -> new HarvestMobSpawnerBlock());
    public static DeferredHolder<Block, HarvestBlock> HARVEST_BLOCK = BLOCKS.register("harvest", () -> new HarvestBlock());

    // block entities
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<HarvestSpawnerBE>> SPAWNER_BE = BLOCK_ENTITIES.register("harvest_spawner", () -> BlockEntityType.Builder.of(HarvestSpawnerBE::new, SPAWNER_BLOCK.get()).build(null));
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<HarvestBE>> HARVEST_BE = BLOCK_ENTITIES.register("harvest", () -> BlockEntityType.Builder.of(HarvestBE::new, HARVEST_BLOCK.get()).build(null));


    // items
    public static DeferredHolder<Item, BlockItem> HARVEST_ITEM = ITEMS.register("harvest", () -> new BlockItem(HARVEST_BLOCK.get(), new Item.Properties().stacksTo(64)));
    public static DeferredHolder<Item, HarvestMapItem> HARVEST_MAP_ITEM = ITEMS.register("harvest_map", () -> new HarvestMapItem());


    public static DeferredHolder<Item, MatItem> BLUE = ITEMS.register("lucid", () -> new MatItem("Lucid"));
    public static DeferredHolder<Item, MatItem> GREEN = ITEMS.register("chaotic", () -> new MatItem("Chaotic"));
    public static DeferredHolder<Item, MatItem> PURPLE = ITEMS.register("primal", () -> new MatItem("Primal"));

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
