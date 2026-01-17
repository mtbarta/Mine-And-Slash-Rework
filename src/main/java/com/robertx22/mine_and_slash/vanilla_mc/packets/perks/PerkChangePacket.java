package com.robertx22.mine_and_slash.vanilla_mc.packets.perks;

import com.robertx22.library_of_exile.main.ExileLog;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import com.robertx22.mine_and_slash.capability.player.PlayerData;
import com.robertx22.mine_and_slash.database.data.game_balance_config.PlayerPointsType;
import com.robertx22.mine_and_slash.database.data.talent_tree.TalentTree;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.saveclasses.PointData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class PerkChangePacket extends MyPacket<PerkChangePacket> {

    public String school;
    public int x;
    public int y;
    ACTION action;

    public enum ACTION {
        ALLOCATE, REMOVE
    }

    public PerkChangePacket() {

    }

    public PerkChangePacket(TalentTree school, PointData point, ACTION action) {
        this.school = school.identifier;
        this.x = point.x;
        this.y = point.y;
        this.action = action;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return ResourceLocation.fromNamespaceAndPath(SlashRef.MODID, "perk_change");
    }

    @Override
    public void loadFromData(RegistryFriendlyByteBuf tag) {
        school = tag.readUtf(30);
        x = tag.readInt();
        y = tag.readInt();
        action = tag.readEnum(ACTION.class);

    }

    @Override
    public void saveToData(RegistryFriendlyByteBuf tag) {
        tag.writeUtf(school, 30);
        tag.writeInt(x);
        tag.writeInt(y);
        tag.writeEnum(action);

    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        PlayerData playerData = Load.player(ctx.getPlayer());
        TalentTree sc = ExileDB.TalentTrees().get(school);

        PlayerPointsType type = sc.getSchool_type().getPointType();

        if (sc == null) {
            ExileLog.get().warn("school is null: " + this.school);
            return;
        }

        System.out.println(
                "[PerkChangePacket] Received: Action=" + action + ", School=" + school + ", Points=" + x + "," + y);
        PointData point = new PointData(x, y);
        if (action == ACTION.ALLOCATE) {
            boolean can = playerData.talents.canAllocate(sc, point, Load.Unit(ctx.getPlayer()), ctx.getPlayer());
            System.out.println("[PerkChangePacket] Can allocate? " + can);
            if (can) {
                playerData.talents.allocate(ctx.getPlayer(), sc, new PointData(x, y));
                System.out.println("[PerkChangePacket] Allocated.");
            }
        } else if (action == ACTION.REMOVE) {
            boolean can = playerData.talents.canRemove(ctx.getPlayer(), sc, point);
            System.out.println("[PerkChangePacket] Can remove? " + can);
            if (can) {
                playerData.talents.remove(sc.getSchool_type(), new PointData(x, y));
                type.reduceResetPoints(ctx.getPlayer(), 1);
                System.out.println("[PerkChangePacket] Removed.");
            }
        }

        Load.Unit(ctx.getPlayer()).setEquipsChanged();

        playerData.playerDataSync.setDirty();

    }

    @Override
    public MyPacket<PerkChangePacket> newInstance() {
        return new PerkChangePacket();
    }
}