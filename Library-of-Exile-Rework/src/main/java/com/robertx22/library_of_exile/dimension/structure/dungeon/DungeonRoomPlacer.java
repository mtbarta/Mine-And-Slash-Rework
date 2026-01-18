package com.robertx22.library_of_exile.dimension.structure.dungeon;

import com.robertx22.library_of_exile.dimension.structure.MapStructure;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import com.robertx22.library_of_exile.main.ExileLog;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.List;

public class DungeonRoomPlacer {

    public static boolean generatePiece(LevelAccessor world, BlockPos position, RandomSource random, Rotation rota,
            ResourceLocation id) {

        StructureTemplate template = null;

        // StructureManager.get() expects the logical structure ID (e.g.,
        // "modid:mystructure")
        // It automatically looks in data/<namespace>/structures/<path>.nbt
        var templateOpt = world.getServer().getStructureManager().get(id);

        if (templateOpt.isPresent()) {
            template = templateOpt.get();
        } else {
            // Fallback: Manually load the structure if StructureManager fails
            try {
                String path = "structures/" + id.getPath() + ".nbt";
                ResourceLocation fileLoc = ResourceLocation.fromNamespaceAndPath(id.getNamespace(), path);
                var resource = world.getServer().getResourceManager().getResource(fileLoc);

                if (resource.isPresent()) {
                    try (var is = resource.get().open()) {
                        var tag = net.minecraft.nbt.NbtIo.readCompressed(is,
                                net.minecraft.nbt.NbtAccounter.unlimitedHeap());

                        // Apply DataFixer to update structure from older versions
                        int version = net.minecraft.nbt.NbtUtils.getDataVersion(tag, 500);
                        var fixer = world.getServer().getFixerUpper();
                        var fixedTag = net.minecraft.util.datafix.DataFixTypes.STRUCTURE.updateToCurrentVersion(fixer,
                                tag, version);

                        template = new StructureTemplate();
                        template.load(
                                world.registryAccess().lookupOrThrow(net.minecraft.core.registries.Registries.BLOCK),
                                fixedTag);
                    }
                } else {
                    ExileLog.get().warn("FATAL ERROR: Raw resource also not found at: " + fileLoc);
                }
            } catch (Exception e) {
                ExileLog.get().warn("Failed to manual fallback load: " + id + " Error: " + e.getMessage());
                e.printStackTrace();
            }
        }

        if (template == null) {
            ExileLog.get().warn("FATAL ERROR: Structure does not exist (and fallback failed): " + id);
            return false;
        }

        StructurePlaceSettings settings = new StructurePlaceSettings().setMirror(Mirror.NONE)
                .setRotation(rota)
                .setIgnoreEntities(false);
        List<StructureTemplate.StructureBlockInfo> commandBlocks = template.filterBlocks(BlockPos.ZERO,
                new StructurePlaceSettings(), Blocks.COMMAND_BLOCK, true);
        List<StructureTemplate.StructureBlockInfo> structureBlocks = template.filterBlocks(BlockPos.ZERO,
                new StructurePlaceSettings(), Blocks.STRUCTURE_BLOCK, true);

        final BlockPos finalPosition = position;

        commandBlocks
                .forEach((block) -> {
                    BlockPos worldPos = finalPosition.offset(block.pos());

                    var event = new ExileEvents.DungeonDataBlockPlaced(world, worldPos, block, id);
                    ExileEvents.DUNGEON_DATA_BLOCK_PLACED.callEvents(event);
                });

        structureBlocks
                .forEach((block) -> {
                    BlockPos worldPos = finalPosition.offset(block.pos());

                    var event = new ExileEvents.DungeonDataBlockPlaced(world, worldPos, block, id);
                    ExileEvents.DUNGEON_DATA_BLOCK_PLACED.callEvents(event);
                });

        settings.setBoundingBox(settings.getBoundingBox());

        if (template.getSize().getX() > 16 || template.getSize().getZ() > 16) {
            ExileLog.get().warn(
                    "FATAL ERROR: Structure is bigger than possible (" + id + ") " + template.getSize().toString());
            return false;
        }

        // next if the structure is to be rotated then it must also be offset, because
        // rotating a structure also moves it
        if (rota == Rotation.COUNTERCLOCKWISE_90) {
            // west: rotate CCW and push +Z
            settings.setRotation(Rotation.COUNTERCLOCKWISE_90);
            position = position.offset(0, 0, template.getSize().getZ() - 1);
        } else if (rota == Rotation.CLOCKWISE_90) {
            // east rotate CW and push +X
            settings.setRotation(Rotation.CLOCKWISE_90);
            position = position.offset(template.getSize().getX() - 1, 0, 0);
        } else if (rota == Rotation.CLOCKWISE_180) {
            // south: rotate 180 and push both +X and +Z
            settings.setRotation(Rotation.CLOCKWISE_180);
            position = position.offset(template.getSize().getX() - 1, 0, template.getSize().getZ() - 1);
        } else // if (nextRoom.rotation == Rotation.NONE)
        { // north: no rotation
            settings.setRotation(Rotation.NONE);
        }

        // Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE is experimental thing that
        // should reduce updatenighbor block lag in map generation
        var done = template.placeInWorld((ServerLevelAccessor) world, position, position, settings, random,
                Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE);

        return done;
    }

    public static boolean generateStructure(MapStructure struc, DungeonBuilder builder, LevelAccessor world,
            ChunkPos cpos) {

        builder.build();

        if (!builder.builtDungeon.hasRoomForChunk(struc, cpos)) {
            return false;
        }
        BuiltRoom room = builder.builtDungeon.getRoomForChunk(struc, cpos);
        if (room == null) {
            return false;
        }

        BlockPos position = cpos.getBlockAt(0, 50, 0);
        generatePiece(world, position, world.getRandom(), room.data.rotation, room.getStructure());
        return true;
    }

}
