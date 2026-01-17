package com.robertx22.mine_and_slash.vanilla_mc.packets;

import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import com.robertx22.mine_and_slash.capability.player.PlayerData;
import com.robertx22.mine_and_slash.database.data.game_balance_config.PlayerPointsType;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.datapacks.stats.CoreStat;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class AllocateStatPacket extends MyPacket<AllocateStatPacket> {

    public String stat;
    AllocateStatPacket.ACTION action;

    public enum ACTION {
        ALLOCATE, REMOVE
    }

    public AllocateStatPacket() {

    }

    public AllocateStatPacket(Stat stat, ACTION act) {
        this.stat = stat.GUID();
        this.action = act;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return ResourceLocation.fromNamespaceAndPath(SlashRef.MODID, "stat_alloc");
    }

    @Override
    public void loadFromData(RegistryFriendlyByteBuf tag) {
        stat = tag.readUtf(30);
        action = tag.readEnum(AllocateStatPacket.ACTION.class);

    }

    @Override
    public void saveToData(RegistryFriendlyByteBuf tag) {
        tag.writeUtf(stat, 30);
        tag.writeEnum(action);

    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        System.out.println("[AllocateStatPacket] Server received packet - stat=" + stat + ", action=" + action);

        Load.Unit(ctx.getPlayer()).setEquipsChanged();

        PlayerData cap = Load.player(ctx.getPlayer());
        System.out.println("[AllocateStatPacket] PlayerData loaded: " + (cap != null));

        if (action == ACTION.ALLOCATE) {
            // Debug: Detailed logging to trace free points calculation
            var entityData = Load.Unit(ctx.getPlayer());
            int playerLevel = entityData != null ? entityData.getLevel() : -1;
            var config = PlayerPointsType.STATS.getConfig();
            int basePoints = config != null ? config.base_points : -1;
            float pointsPerLvl = config != null ? config.points_per_lvl : -1;
            int maxTotal = config != null ? config.max_total_points : -1;
            int spent = PlayerPointsType.STATS.getPointsInUse(ctx.getPlayer());
            int calculatedTotal = basePoints + (int) (playerLevel * pointsPerLvl);
            System.out.println("[AllocateStatPacket] DEBUG: playerLevel=" + playerLevel
                    + ", basePoints=" + basePoints
                    + ", pointsPerLvl=" + pointsPerLvl
                    + ", calculatedTotal=" + calculatedTotal
                    + ", maxTotal=" + maxTotal
                    + ", spent=" + spent);

            int freePoints = PlayerPointsType.STATS.getFreePoints(ctx.getPlayer());
            System.out.println("[AllocateStatPacket] Free points: " + freePoints);
            if (freePoints > 0) {
                var statObj = ExileDB.Stats().get(stat);
                System.out.println("[AllocateStatPacket] Stat from DB: "
                        + (statObj != null ? statObj.getClass().getSimpleName() : "null") + ", isCorestat="
                        + (statObj instanceof CoreStat));
                if (statObj instanceof CoreStat) {
                    int oldValue = cap.statPoints.map.getOrDefault(stat, 0);
                    cap.statPoints.map.put(stat, 1 + oldValue);
                    System.out.println(
                            "[AllocateStatPacket] Allocated! " + stat + ": " + oldValue + " -> " + (oldValue + 1));
                }
            }
        } else {
            if (PlayerPointsType.STATS.getResetPoints(ctx.getPlayer()) > 0) {
                if (ExileDB.Stats().get(stat) instanceof CoreStat) {
                    int current = cap.statPoints.map.getOrDefault(stat, 0);
                    if (current > 0) {
                        cap.statPoints.map.put(stat, current - 1);
                        System.out.println(
                                "[AllocateStatPacket] Removed! " + stat + ": " + current + " -> " + (current - 1));
                    }
                }
            }
        }
        Load.Unit(ctx.getPlayer()).setEquipsChanged();
        Load.player(ctx.getPlayer()).cachedStats.setAllDirty();
        cap.playerDataSync.setDirty();
        System.out.println("[AllocateStatPacket] Marked dirty for sync");
    }

    @Override
    public MyPacket<AllocateStatPacket> newInstance() {
        return new AllocateStatPacket();
    }
}
