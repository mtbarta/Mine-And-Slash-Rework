package com.robertx22.mine_and_slash.mmorpg.event_registers;

import com.robertx22.mine_and_slash.config.forge.ClientConfigs;
import com.robertx22.mine_and_slash.config.forge.overlay.OverlayType;
import com.robertx22.mine_and_slash.gui.overlays.EffectsOverlay;
import com.robertx22.mine_and_slash.gui.overlays.bar_overlays.types.RPGGuiOverlay;
import com.robertx22.mine_and_slash.gui.overlays.spell_cast_bar.SpellCastBarOverlay;
import com.robertx22.mine_and_slash.gui.overlays.spell_hotbar.SpellHotbarOverlay;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT, modid = SlashRef.MODID, bus = EventBusSubscriber.Bus.MOD)
public class GuiOverlays {
    @SubscribeEvent
    public static void registerOverlay(RegisterGuiLayersEvent event) {

        event.registerAbove(VanillaGuiLayers.CHAT,
                new ResourceLocation(SlashRef.MODID, "spell_hotbar"), new LayeredDraw.Layer() {
                    @Override
                    public void render(GuiGraphics guiGraphics, float partialTick) {
                        if (ClientConfigs.CLIENT.SPELL_HOTBAR_OVERLAY_TYPE
                                .get() == ClientConfigs.HorizontalOrVertical.HORIZONTAL) {
                            if (ClientConfigs.getConfig().shouldRenderOverlay(OverlayType.SPELL_HOTBAR_HORIZONTAL)) {
                                new SpellHotbarOverlay().onHudRender(guiGraphics,
                                        ClientConfigs.getConfig().getOverlayConfig(OverlayType.SPELL_HOTBAR_HORIZONTAL),
                                        OverlayType.SPELL_HOTBAR_HORIZONTAL);
                            }
                        } else {
                            if (ClientConfigs.getConfig().shouldRenderOverlay(OverlayType.SPELL_HOTBAR_VERTICAL)) {
                                new SpellHotbarOverlay().onHudRender(guiGraphics,
                                        ClientConfigs.getConfig().getOverlayConfig(OverlayType.SPELL_HOTBAR_VERTICAL),
                                        OverlayType.SPELL_HOTBAR_VERTICAL);
                            }
                        }
                    }
                });

        event.registerAbove(VanillaGuiLayers.CHAT, new ResourceLocation(SlashRef.MODID, "cast_bar"),
                new LayeredDraw.Layer() {
                    @Override
                    public void render(GuiGraphics guiGraphics, float partialTick) {
                        if (ClientConfigs.getConfig().shouldRenderOverlay(OverlayType.SPELL_CAST_BAR)) {
                            new SpellCastBarOverlay().onHudRender(guiGraphics,
                                    partialTick);
                        }
                    }
                });

        event.registerAbove(VanillaGuiLayers.CHAT, new ResourceLocation(SlashRef.MODID, "rpg_gui"),
                new LayeredDraw.Layer() {
                    @Override
                    public void render(GuiGraphics guiGraphics, float partialTick) {
                        new RPGGuiOverlay().onHudRender(guiGraphics);
                    }
                });

        event.registerAbove(VanillaGuiLayers.CHAT,
                new ResourceLocation(SlashRef.MODID, "status_effects"), new LayeredDraw.Layer() {
                    @Override
                    public void render(GuiGraphics guiGraphics, float partialTick) {
                        if (ClientConfigs.CLIENT.STATUS_EFFECTS_OVERLAY_TYPE
                                .get() == ClientConfigs.HorizontalOrVertical.HORIZONTAL) {
                            if (ClientConfigs.getConfig().shouldRenderOverlay(OverlayType.EFFECTS_HORIZONTAL)) {
                                EffectsOverlay.render(guiGraphics, true);
                            }
                        } else {
                            if (ClientConfigs.getConfig().shouldRenderOverlay(OverlayType.EFFECTS_VERTICAL)) {
                                EffectsOverlay.render(guiGraphics, false);
                            }
                        }
                    }
                });
    }
}
