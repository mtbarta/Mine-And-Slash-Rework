package com.robertx22.mine_and_slash.database.data.profession.screen;

import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.tooltip.ExileTooltipUtils;
import com.robertx22.mine_and_slash.a_libraries.jei.LockRecipePacket;
import com.robertx22.mine_and_slash.database.data.profession.ProfessionRecipe;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;

import java.util.ArrayList;
import java.util.List;

public class RecipeButton extends ImageButton {

    public static int XS = 18;
    public static int YS = 19;

    Minecraft mc = Minecraft.getInstance();

    ProfessionRecipe recipe;

    public RecipeButton(CraftingStationScreen screen, ProfessionRecipe recipe, int xPos, int yPos) {
        super(xPos, yPos, XS, YS, new WidgetSprites(SlashRef.guiId(""), SlashRef.guiId("")), (button) -> {
            Packets.sendToServer(new LockRecipePacket(recipe.GUID()));
            screen.refreshRequiredMats(recipe);
        });
        this.recipe = recipe;
    }

    @Override
    public void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        setModTooltip();
        gui.renderFakeItem(recipe.toResultStackForJei(), getX(), getY());

        // ResourceLocation tex = SlashRef.guiId("craftbutton");
        // gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        // gui.blit(tex, getX(), getY(), 0, (pbe.getSyncedData().craftingState ==
        // Crafting_State.ACTIVE || pbe.getSyncedData().craftingState ==
        // Crafting_State.IDLE) ? 0 : 19, 18, 19);
    }

    public void setModTooltip() {

        List<MutableComponent> list = new ArrayList<>();
        for (Component l : recipe.toResultStackForJei().getTooltipLines(Item.TooltipContext.of(mc.level), mc.player,
                TooltipFlag.NORMAL)) {
            list.add((MutableComponent) l);
        }

        this.setTooltip(
                Tooltip.create(ExileTooltipUtils.joinMutableComps(list.listIterator(), Component.literal("\n"))));
    }

}