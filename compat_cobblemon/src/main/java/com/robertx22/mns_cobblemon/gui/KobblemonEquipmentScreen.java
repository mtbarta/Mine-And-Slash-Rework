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

        // Render Weapon Slot Background
        // Standard slot size is 18x18
        // Slot position in container is 80, 26
        // We can steal a slot texture from the background or draw a box.
        // Usually, modded GUIs draw a slot texture.
        // Let's use `gui.blit` to draw a slot from the inventory texture over the
        // position.
        // Inventory texture slot is usually at 7, 7 (for a single slot) or we pick an
        // empty one.
        // The creative inventory texture has tabs.
        // Actually, AbstractContainerScreen doesn't usually draw slots for us unless
        // they are part of the BG texture.

        int slotX = relX + 79; // -1 for border
        int slotY = relY + 25; // -1 for border
        // Drawing a simple slot box using blit from the same texture
        // standard slot texture is at 7, 83 in container.png (example)
        // Let's just assume we want it to look like a slot.
        // Using a safe fallback or just drawing a rectangle.

        // Actually, better to just blit a slot from the texture.
        // 1.21 uses gui graphics.
        // Let's just try to blit a 18x18 square from a known slot location on the
        // texture.
        // The hotbar slots are at 0-8 * 18, so we can grab one.
        // INVENTORY_LOCATION is "textures/gui/container/inventory.png"
        // Hotbar slots start at y=166-24?
        // Let's grab the slot at 7, 7 (top left slot of the 3x3 grid)? No that's empty.
        // The texture has slots at 7, 17?
        // Let's look at vanilla ContainerScreen logic or just draw a colored box for
        // now to be safe/visible,
        // OR rely on the fact that `Slot` objects are rendered by `super.render` but
        // the BACKGROUND isn't.

        // Render a slot background at 80, 26
        // Inventory screen texture:
        // Main inventory slots start at roughly 8, 84
        // Let's copy the slot at 8, 84 (first slot of main inventory)
        gui.blit(InventoryScreen.INVENTORY_LOCATION, relX + 79, relY + 25, 7, 83, 18, 18);
    }

    @Override
    protected void renderLabels(GuiGraphics gui, int mouseX, int mouseY) {
        // Pokemon Name and Level can be displayed in a more discreet way or via
        // tooltips
        titleLabelX = (imageWidth - font.width(title)) / 2;
        super.renderLabels(gui, mouseX, mouseY);
    }
}
