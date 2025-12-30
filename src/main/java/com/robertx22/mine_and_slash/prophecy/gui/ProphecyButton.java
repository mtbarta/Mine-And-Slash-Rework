package com.robertx22.mine_and_slash.prophecy.gui;

import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.prophecy.AcceptProphecyPacket;
import com.robertx22.mine_and_slash.prophecy.ProphecyData;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.TextUTIL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.ResourceLocation;

public class ProphecyButton extends ImageButton {
    static ResourceLocation DEFAULT_ID = new ResourceLocation(SlashRef.MODID, "textures/gui/prophecy/icon.png");

    ProphecyData data;
    ResourceLocation iconTexture;

    public ProphecyButton(ProphecyData data, boolean canTake, int x, int y) {
        super(x, y, 16, 16,
                new WidgetSprites(DEFAULT_ID, DEFAULT_ID),
                (action) -> {
                    if (canTake) {
                        Packets.sendToServer(new AcceptProphecyPacket(data.uuid));
                        Minecraft.getInstance().setScreen(null);
                    }
                });
        this.data = data;
        this.iconTexture = getIconForProphecyType(data);
    }

    private ResourceLocation getIconForProphecyType(ProphecyData data) {
        String iconPath = "textures/gui/prophecy/" + data.start + ".png";

        return new ResourceLocation(SlashRef.MODID, iconPath);
    }

    @Override
    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        pGuiGraphics.blit(iconTexture, getX(), getY(), 0, 0, 16, 16, 16, 16);

        this.setTooltip(Tooltip.create(TextUTIL.mergeList(data.getTooltip())));
    }
}