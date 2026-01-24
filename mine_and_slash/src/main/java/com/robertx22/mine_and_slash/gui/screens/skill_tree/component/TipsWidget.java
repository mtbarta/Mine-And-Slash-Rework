package com.robertx22.mine_and_slash.gui.screens.skill_tree.component;

import com.mojang.blaze3d.vertex.PoseStack;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class TipsWidget extends AbstractWidget {
    static ResourceLocation TIPS = SlashRef.id("textures/gui/skill_tree/tips.png");

    public TipsWidget(int pX, int pY, int pWidth, int pHeight, Component pMessage) {
        super(pX, pY, pWidth, pHeight, pMessage);
        setTooltip(Tooltip.create(pMessage));
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int i1, float v) {
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate(0, 0, 10);
        guiGraphics.blit(TIPS, getX(), getY(), getWidth(), getHeight(), 0, 0, 32, 32, 32, 32);
        pose.popPose();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
