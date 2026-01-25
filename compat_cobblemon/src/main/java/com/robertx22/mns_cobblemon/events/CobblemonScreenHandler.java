package com.robertx22.mns_cobblemon.events;

import com.cobblemon.mod.common.client.gui.summary.Summary;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mns_cobblemon.core.stats.PokemonStatSync;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

import java.lang.reflect.Method;
import java.util.List;

import static com.robertx22.mns_cobblemon.MnSCobblemonCompat.MODID;

@EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class CobblemonScreenHandler {

    @SubscribeEvent
    public static void onScreenRender(ScreenEvent.Render.Post event) {
        Screen screen = event.getScreen();
        // Check if this is the Cobblemon Summary screen
        if (screen instanceof Summary) {
            GuiGraphics guiGraphics = event.getGuiGraphics();

            try {
                Pokemon pokemon = getPokemonFromScreen(screen);
                if (pokemon != null) {
                    List<ExactStatData> stats = PokemonStatSync.getStatsForPokemon(pokemon);

                    int y = 30;
                    guiGraphics.drawString(net.minecraft.client.Minecraft.getInstance().font, "Mine and Slash Stats:",
                            10, y, 0xFFAA00);
                    y += 12;

                    for (ExactStatData statData : stats) {
                        List<MutableComponent> tooltips = statData.GetTooltipString();
                        for (MutableComponent tooltip : tooltips) {
                            guiGraphics.drawString(net.minecraft.client.Minecraft.getInstance().font, tooltip, 10, y,
                                    0xFFFFFF);
                            y += 10;
                        }
                    }
                } else {
                    inspectScreen(screen);
                }
            } catch (Exception e) {
                // Ignore Reflection errors in production loop
            }
        }
    }

    private static Pokemon getPokemonFromScreen(Screen screen) {
        // Try known methods
        try {
            Method getPokemon = screen.getClass().getMethod("getPokemon");
            return (Pokemon) getPokemon.invoke(screen);
        } catch (Exception ignored) {
        }

        // Try Fields
        try {
            java.lang.reflect.Field pokemonField = screen.getClass().getDeclaredField("pokemon");
            pokemonField.setAccessible(true);
            return (Pokemon) pokemonField.get(screen);
        } catch (Exception ignored) {
        }

        return null;
    }

    private static boolean inspected = false;

    private static void inspectScreen(Screen screen) {
        if (inspected)
            return;
        inspected = true;
        System.out.println("Inspecting Cobblemon Summary Screen: " + screen.getClass().getName());
        for (Method method : screen.getClass().getMethods()) {
            if (method.getReturnType().getSimpleName().contains("Pokemon")
                    || method.getName().toLowerCase().contains("pokemon")) {
                System.out.println(
                        "Found potential method: " + method.getName() + " -> " + method.getReturnType().getName());
            }
        }
        for (java.lang.reflect.Field field : screen.getClass().getDeclaredFields()) {
            if (field.getType().getSimpleName().contains("Pokemon")
                    || field.getName().toLowerCase().contains("pokemon")) {
                System.out.println("Found potential field: " + field.getName() + " -> " + field.getType().getName());
            }
        }
    }
}
