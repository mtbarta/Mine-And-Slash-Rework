package com.robertx22.mns_cobblemon.gui;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.robertx22.mine_and_slash.gui.bases.INamedScreen;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class KobblemonEquipmentScreen extends AbstractContainerScreen<KobblemonContainer> implements INamedScreen {

    private final PokemonEntity pokemon;

    public KobblemonEquipmentScreen(KobblemonContainer menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.pokemon = menu.getPokemon();
        this.imageWidth = 176;
        this.imageHeight = 166;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public ResourceLocation iconLocation() {
        return SlashRef.guiId("main_hub/icons/chars");
    }

    @Override
    public Words screenName() {
        return Words.Stats;
    }

    @Override
    protected void init() {
        super.init();
        // Add additional buttons/widgets here if needed
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(gui, mouseX, mouseY, partialTick);
        super.render(gui, mouseX, mouseY, partialTick);
        this.renderTooltip(gui, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTick, int mouseX, int mouseY) {
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        gui.blit(InventoryScreen.INVENTORY_LOCATION, relX, relY, 0, 0, this.imageWidth, this.imageHeight);

        // Render Pokemon
        int paperDollX = relX + 51;
        int paperDollY = relY + 75;
        float mouseOffsetX = (float) (paperDollX - mouseX);
        float mouseOffsetY = (float) (paperDollY - 50 - mouseY);
        InventoryScreen.renderEntityInInventoryFollowsMouse(gui, paperDollX - 30, paperDollY - 30, paperDollX + 30,
                paperDollY + 30, 30, 0.0625F, mouseOffsetX, mouseOffsetY, this.pokemon);
    }

    @Override
    protected void renderLabels(GuiGraphics gui, int mouseX, int mouseY) {
        // Pokemon Name and Level can be displayed in a more discreet way or via
        // tooltips
        titleLabelX = (imageWidth - font.width(title)) / 2;
        super.renderLabels(gui, mouseX, mouseY);
    }
}
