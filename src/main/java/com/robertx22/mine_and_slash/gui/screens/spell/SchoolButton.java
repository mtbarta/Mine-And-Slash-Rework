package com.robertx22.mine_and_slash.gui.screens.spell;

import com.robertx22.library_of_exile.utils.TextUTIL;
import com.robertx22.mine_and_slash.database.data.spell_school.SpellSchool;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class SchoolButton extends ImageButton {

    static int SIZE = 25;

    SpellSchool school;
    SpellSchoolScreen scren;

    public SchoolButton(SpellSchoolScreen scren, int xPos, int yPos) {
        super(xPos, yPos, SIZE, SIZE,
                new WidgetSprites(SlashRef.guiId(""), SlashRef.guiId("")),
                (button) -> {
                });
        this.scren = scren;
    }

    @Override
    public void onPress() {
        if (this.school != null) {
            scren.setCurrent(school);
        }
    }

    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if (school != null) {
            pGuiGraphics.blit(this.school.getIconLoc(), this.getX(), this.getY(), 0, 0, SIZE, SIZE, SIZE, SIZE);

            if (this.isHoveredOrFocused()) {
                List<Component> tooltip = new ArrayList<>();
                tooltip.add(school.locName().withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.BOLD));
                this.setTooltip(Tooltip.create(TextUTIL.mergeList(tooltip)));
            }
        }
    }

}