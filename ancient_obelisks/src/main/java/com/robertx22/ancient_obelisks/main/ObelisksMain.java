package com.robertx22.ancient_obelisks.main;

import com.robertx22.ancient_obelisks.configs.ObeliskConfig;
import com.robertx22.ancient_obelisks.database.ObeliskDatabase;
import com.robertx22.ancient_obelisks.item.ObeliskItemNbt;
import com.robertx22.ancient_obelisks.registry.ObeliskAttachments;
import com.robertx22.ancient_obelisks.registry.ObeliskDataComponents;
import com.robertx22.ancient_obelisks.item.ObeliskMapItem;
import com.robertx22.ancient_obelisks.structure.ObeliskMapCapability;
import com.robertx22.ancient_obelisks.structure.ObeliskMapData;
import com.robertx22.ancient_obelisks.structure.ObeliskMapStructure;
import com.robertx22.ancient_obelisks.structure.ObeliskWorldData;
import com.robertx22.library_of_exile.config.map_dimension.MapDimensionConfigDefaults;
import com.robertx22.library_of_exile.config.map_dimension.MapRegisterBuilder;
import com.robertx22.library_of_exile.database.affix.base.ExileAffixData;
import com.robertx22.library_of_exile.database.affix.base.GrabMobAffixesEvent;
import com.robertx22.library_of_exile.database.init.LibDatabase;
import com.robertx22.library_of_exile.database.init.PredeterminedResult;
import com.robertx22.library_of_exile.database.mob_list.MobList;
import com.robertx22.library_of_exile.dimension.MapChunkGenEvent;
import com.robertx22.library_of_exile.dimension.MapContentType;
import com.robertx22.library_of_exile.dimension.MapDimensionInfo;
import com.robertx22.library_of_exile.dimension.MapDimensions;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import com.robertx22.library_of_exile.main.ApiForgeEvents;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.helpers.OrderedModConstructor;
import com.robertx22.library_of_exile.registry.register_info.ModRequiredRegisterInfo;
import com.robertx22.library_of_exile.registry.util.ExileRegistryUtil;
import com.robertx22.library_of_exile.unidentified.IdentifiableItems;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Container;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import net.neoforged.neoforge.data.event.GatherDataEvent;
// AttachCapabilitiesEvent removed
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import net.minecraft.core.registries.BuiltInRegistries;

import java.util.*;
import javax.annotation.Nonnull;

@Mod("ancient_obelisks")
public class ObelisksMain {
    public static boolean RUN_DEV_TOOLS = false;

    public static String MODID = "ancient_obelisks";
    public static String DIMENSION_ID = "ancient_obelisks:obelisk";
    public static ResourceLocation DIMENSION_KEY = ResourceLocation.parse(DIMENSION_ID);

    public static ModRequiredRegisterInfo REGISTER_INFO = new ModRequiredRegisterInfo(MODID);

    public static ResourceLocation id(String id) {
        return ResourceLocation.fromNamespaceAndPath(MODID, id);
    }

    // other
    public static ObeliskMapStructure OBELISK_MAP_STRUCTURE = new ObeliskMapStructure();
    public static MapDimensionInfo MAP = new MapDimensionInfo(
            DIMENSION_KEY,
            OBELISK_MAP_STRUCTURE,
            MapContentType.SIDE_CONTENT,
            Arrays.asList(),
            new ObeliskMobValidator(),
            new MapDimensionConfigDefaults(3, 2)) {
        @Override
        public void clearMapDataOnFolderWipe(MinecraftServer minecraftServer) {
            ObeliskMapCapability.get(minecraftServer.overworld()).data = new ObeliskWorldData();
        }
    };

    public static void debugMsg(Player p, String s) {
        if (p.isCreative()) {
            p.sendSystemMessage(Component.literal("[Debug Info]:" + s));
        }
    }

    public ObelisksMain(IEventBus bus) {
        // Set the mod event bus first so IModBusEvent events are registered correctly
        ApiForgeEvents.setModEventBus(bus);
        
        OrderedModConstructor.register(new ObeliskModConstructor(ObelisksMain.MODID), bus);

        ObeliskAttachments.register(bus);
        ObeliskDataComponents.REGISTRY.register(bus);

        // DistExecutor removed
        bus.addListener(this::clientSetup);

        new MapRegisterBuilder(MAP)
                // In NeoForge 1.21, chunkGenerator requires IEventBus as first arg
                .chunkGenerator(bus, new EventConsumer<MapChunkGenEvent>() {
                    @Override
                    public void accept(MapChunkGenEvent event) {
                        if (event.mapId.equals("obelisk")) {
                            OBELISK_MAP_STRUCTURE.generateInChunk(event.world, event.manager, event.chunk.getPos());
                        }
                    }
                }, id("obelisk_chunk_gen"))
                .build();

        if (RUN_DEV_TOOLS) {
            ExileRegistryUtil.setCurrentRegistarMod(ObelisksMain.MODID);

            ApiForgeEvents.registerForgeEvent(PlayerEvent.PlayerLoggedInEvent.class, event -> {
                ObeliskDatabase.generateJsons();
            });
        }

        ApiForgeEvents.registerForgeEvent(GatherDataEvent.class, event -> {
            var output = event.getGenerator().getPackOutput();
            var chestsLootTables = new LootTableProvider.SubProviderEntry(
                    ObeliskLootTables.ObeliskLootTableProvider::new, LootContextParamSets.CHEST);
            var internalProvider = new LootTableProvider(output, Set.of(), List.of(chestsLootTables),
                    event.getLookupProvider());

            event.getGenerator().addProvider(true, new net.minecraft.data.DataProvider() {
                @Override
                public java.util.concurrent.CompletableFuture<?> run(@Nonnull net.minecraft.data.CachedOutput pOutput) {
                    return internalProvider.run(pOutput);
                }

                @Override
                public String getName() {
                    return "Loot Tables (" + MODID + ")";
                }
            });

            if (RUN_DEV_TOOLS) {
                // todo this doesnt seem to gen here? ObeliskDatabase.generateJsons();
            }
        });

        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.SERVER,
                ObeliskConfig.SPEC, "ancient_obelisks.toml");
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.SERVER,
                MAP.config.spec, "ancient_obelisks_dimension.toml");

        bus.addListener(this::commonSetupEvent);

        ExileEvents.GRAB_MOB_AFFIXES.register(new EventConsumer<GrabMobAffixesEvent>() {
            @Override
            public void accept(GrabMobAffixesEvent e) {
                var map = MapDimensions.getInfo(e.en.level());
                if (map != null && map.dimensionId.equals(DIMENSION_KEY)) {
                    var mapdata = ObeliskMapCapability.get(e.en.level()).data.data.getData(OBELISK_MAP_STRUCTURE,
                            e.en.blockPosition());
                    if (mapdata != null) {
                        for (int i = 0; i < mapdata.currentWave + 1; i++) {
                            for (ExileAffixData affix : mapdata.item.getAffixesForWave(i)) {
                                if (affix.getAffix().affects.is(e.en)) {
                                    e.allAffixes.add(affix);
                                }
                            }
                        }
                    }
                }
            }
        });

        ApiForgeEvents.registerForgeEvent(FinalizeSpawnEvent.class, event -> {
            var en = event.getEntity();
            if (!en.level().isClientSide) {
                ifMapData(event.getEntity().level(), event.getEntity().blockPosition()).ifPresent(x -> {
                    ObeliskMobTierStats.tryApply(en, x);
                });
            }
        });

        ObeliskEntries.CREATIVE_TAB.register(MODID, () -> new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 2)
                .icon(() -> ObeliskEntries.OBELISK_ITEM.get().getDefaultInstance())
                .title(ObeliskWords.CREATIVE_TAB.get().withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.BOLD))
                .displayItems(new CreativeModeTab.DisplayItemsGenerator() {
                    @Override
                    public void accept(CreativeModeTab.ItemDisplayParameters param, CreativeModeTab.Output output) {
                        for (Item item : BuiltInRegistries.ITEM) {
                            if (BuiltInRegistries.ITEM.getKey(item).getNamespace().equals(ObelisksMain.MODID)) {
                                output.accept(item);
                            }
                        }
                    }
                })
                .build());

        ObeliskCommands.init();
        ObeliskRewardLogic.init();

        ExileEvents.ON_CHEST_LOOTED.register(new EventConsumer<ExileEvents.OnChestLooted>() {
            @Override
            public void accept(ExileEvents.OnChestLooted e) {
                try {

                    float chance = (float) (ObeliskConfig.get().OBELISK_SPAWN_CHANCE_ON_CHEST_LOOT.get()
                            * ObeliskConfig.get().getDimChanceMulti(e.player.level()));
                    if (RandomUtils.roll(chance)) {
                        if (!MapDimensions.isMap(e.player.level())) {
                            var empty = mygetEmptySlotsRandomized(e.inventory, new Random());
                            if (!empty.isEmpty()) {
                                int index = RandomUtils.randomFromList(empty);
                                var map = ObeliskMapItem
                                        .blankMap(ObeliskEntries.OBELISK_MAP_ITEM.get().getDefaultInstance(), false);
                                e.inventory.setItem(index, map);
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        IdentifiableItems.register(ObeliskEntries.OBELISK_MAP_ITEM.getId(), new IdentifiableItems.Config() {
            @Override
            public boolean isUnidentified(ItemStack stack) {
                return !ObeliskItemNbt.OBELISK_MAP.has(stack);
            }

            @Override
            public void identify(Player player, ItemStack stack) {
                ObeliskMapItem.blankMap(stack, false);
            }
        });

        System.out.println("Ancient Obelisks loaded.");

    }

    private static List<Integer> mygetEmptySlotsRandomized(Container inventory, Random rand) {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < inventory.getContainerSize(); ++i) {
            if (inventory.getItem(i)
                    .isEmpty()) {
                list.add(i);
            }
        }

        Collections.shuffle(list, rand);
        return list;
    }

    public static Optional<ObeliskMapData> ifMapData(Level level, BlockPos pos) {
        if (level.isClientSide) {
            return Optional.empty();
        }
        var map = MapDimensions.getInfo(level);
        if (map != null && map.dimensionId.equals(DIMENSION_KEY)) {
            var mapdata = ObeliskMapCapability.get(level).data.data.getData(OBELISK_MAP_STRUCTURE, pos);
            if (mapdata != null) {
                return Optional.of(mapdata);
            }
        }
        return Optional.empty();
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        ObeliskClient.init();
    }

    public void commonSetupEvent(FMLCommonSetupEvent event) {

        ComponentInit.reg();

        // AttachCapabilitiesEvent listeners removed

    }

    public static PredeterminedResult<MobList> MOB_SPAWNS = new PredeterminedResult<MobList>() {
        @Override
        public ExileRegistryType getRegistryType() {
            return LibDatabase.MOB_LIST;
        }

        @Override
        public MobList getPredeterminedRandomINTERNAL(Random random, Level level, ChunkPos pos) {
            var dungeon = OBELISK_MAP_STRUCTURE.getObelisk(pos);
            return LibDatabase.MobLists().getFilterWrapped(x -> dungeon.mob_list_tag_check.matches(x).can)
                    .random(random.nextDouble());
        }
    };
}
