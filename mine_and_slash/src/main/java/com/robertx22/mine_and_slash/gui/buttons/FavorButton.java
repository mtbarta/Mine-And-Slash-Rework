package com.robertx22.mine_and_slash.gui.buttons;

import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.utils.TextUTIL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class FavorButton extends ImageButton {

    public static int FAVOR_BUTTON_SIZE_X = 34;
    public static int FAVOR_BUTTON_SIZE_Y = 34;

    Minecraft mc = Minecraft.getInstance();

    public FavorButton(int xPos, int yPos) {
        super(xPos, yPos, FAVOR_BUTTON_SIZE_X, FAVOR_BUTTON_SIZE_Y,
                new WidgetSprites(ResourceLocation.fromNamespaceAndPath("minecraft", "empty"),
                        ResourceLocation.fromNamespaceAndPath("minecraft", "empty")),
                (button) -> {
                }, Component.empty());

    }

    @Override
    public void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        var playerData = Load.player(mc.player);
        if (playerData == null) {
            return;
        }
        setModTooltip(playerData);
        ResourceLocation tex = playerData.favor.getTexture();
        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        gui.blit(tex, getX(), getY(), FAVOR_BUTTON_SIZE_X, FAVOR_BUTTON_SIZE_X, FAVOR_BUTTON_SIZE_X,
                FAVOR_BUTTON_SIZE_X, FAVOR_BUTTON_SIZE_X, FAVOR_BUTTON_SIZE_X);

    }

    public void setModTooltip(com.robertx22.mine_and_slash.capability.player.PlayerData playerData) {
        this.setTooltip(Tooltip.create(TextUTIL.mergeList(playerData.favor.getTooltip())));

    }

}