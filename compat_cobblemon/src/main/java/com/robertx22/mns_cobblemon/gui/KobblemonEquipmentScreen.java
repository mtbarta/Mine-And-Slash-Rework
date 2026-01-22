package com.robertx22.mns_cobblemon.gui;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.robertx22.mine_and_slash.gui.bases.BaseScreen;
import com.robertx22.mine_and_slash.gui.bases.INamedScreen;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class KobblemonEquipmentScreen extends BaseScreen implements INamedScreen, MenuAccess<KobblemonContainer> {

    private final KobblemonContainer menu;
    private final PokemonEntity pokemon;

    public KobblemonEquipmentScreen(KobblemonContainer menu, Inventory playerInventory, Component title) {
        super(176, 166);
        this.menu = menu;
        this.pokemon = menu.getPokemon();
    }

    @Override
    public KobblemonContainer getMenu() {
        return menu;
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
        // Add buttons, stat displays here
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(gui, mouseX, mouseY, partialTick);

        // Draw background texture
        // gui.blit(TEXTURE, this.guiLeft, this.guiTop, 0, 0, this.imageWidth,
        // this.imageHeight);

        super.render(gui, mouseX, mouseY, partialTick);

        gui.drawCenteredString(this.font, pokemon.getDisplayName(), this.width / 2, this.guiTop + 10, 0xFFFFFF);

        // Render stats (example)
        gui.drawString(this.font, "Level: " + pokemon.getPokemon().getLevel(), this.guiLeft + 10, this.guiTop + 30,
                0xFFFFFF);
    }
}
