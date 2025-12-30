package com.robertx22.mine_and_slash.capability.player.container;

import com.robertx22.mine_and_slash.capability.player.data.Backpacks;
import com.robertx22.mine_and_slash.vanilla_mc.packets.backpack.OpenBackpackPacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.TextUTIL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;

public class BackpackButton extends ImageButton {

    public static int SX = 16;
    public static int SY = 16;

    Minecraft mc = Minecraft.getInstance();

    public Backpacks.BackpackType type;

    public BackpackButton(Backpacks.BackpackType type, int xPos, int yPos) {
        super(xPos, yPos, SX, SY,
                new WidgetSprites(new ResourceLocation("empty"), new ResourceLocation("empty")),
                (button) -> {
                    Packets.sendToServer(new OpenBackpackPacket(type));
                });
        this.type = type;

    }

    @Override
    public void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        setModTooltip();
        ResourceLocation tex = type.getIcon();
        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        gui.blit(tex, getX(), getY(), SX, SX, SX, SX, SX, SX);

    }

    @Override
    public void onClick(double pMouseX, double pMouseY) {
        BackpackScreen.iMouseX = Minecraft.getInstance().mouseHandler.xpos();
        BackpackScreen.iMouseY = Minecraft.getInstance().mouseHandler.ypos();
        super.onClick(pMouseX, pMouseY);
    }

    public void setModTooltip() {
        this.setTooltip(Tooltip.create(TextUTIL.mergeList(Arrays.asList(
                this.type.name.locName()))));

    }

}