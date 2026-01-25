package com.robertx22.mns_cobblemon.events;

import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.events.pokemon.interaction.PokemonInteractionGUICreationEvent;
import com.cobblemon.mod.common.client.gui.interact.wheel.InteractWheelOption;
import com.robertx22.mns_cobblemon.network.PacketOpenKobblemonGui;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Vector3f;

import java.util.UUID;

import static com.robertx22.mns_cobblemon.MnSCobblemonCompat.MODID;

/**
 * Handles adding the "Equipment" option to Cobblemon's Pokemon interaction
 * wheel.
 * This allows players to access Pokemon equipment via sneak+right-click.
 */
public class PokemonInteractionHandler {

    // Icon for the equipment button - using Cobblemon's held item icon
    private static final ResourceLocation EQUIPMENT_ICON = ResourceLocation.fromNamespaceAndPath(
            "cobblemon", "textures/gui/interact/interact_wheel_icon_held_item.png");

    /**
     * Registers the interaction wheel handler with Cobblemon's event system.
     * Should be called during client-side mod initialization.
     */
    public static void register() {
        CobblemonEvents.POKEMON_INTERACTION_GUI_CREATION.subscribe(
                PokemonInteractionHandler::onPokemonInteractionGUI);
    }

    /**
     * Called when Cobblemon creates the interaction wheel GUI.
     * Adds our "Equipment" option to the wheel.
     */
    private static void onPokemonInteractionGUI(PokemonInteractionGUICreationEvent event) {
        UUID pokemonId = event.getPokemonID();

        // Create the equipment wheel option
        InteractWheelOption equipmentOption = new InteractWheelOption(
                EQUIPMENT_ICON, // Icon resource
                null, // Secondary icon (none)
                true, // Enabled
                "Pokemon Equipment", // Tooltip text
                () -> new Vector3f(1.0f, 1.0f, 1.0f), // Color (white)
                () -> {
                    // OnPress: Send packet to server to open equipment GUI
                    openEquipmentScreen(pokemonId);
                    return kotlin.Unit.INSTANCE;
                });

        // Add to the next available slot in the wheel
        event.addFillingOption(equipmentOption);

        // Create the minion recruit option
        InteractWheelOption recruitOption = new InteractWheelOption(
                ResourceLocation.fromNamespaceAndPath("minecraft", "textures/item/lead.png"), // Use lead icon for now
                null,
                true,
                "Recruit/Dismiss Minion",
                () -> new Vector3f(1.0f, 1.0f, 1.0f),
                () -> {
                    toggleMinion(pokemonId);
                    return kotlin.Unit.INSTANCE;
                });
        event.addFillingOption(recruitOption);
    }

    /**
     * Sends a packet to the server to open the equipment screen for the given
     * Pokemon.
     */
    private static void openEquipmentScreen(UUID pokemonId) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level != null && mc.player != null) {
            // Search for the Pokemon entity within a reasonable range of the player
            var searchBox = mc.player.getBoundingBox().inflate(20.0);
            var entities = mc.level.getEntities(mc.player, searchBox,
                    entity -> entity.getUUID().equals(pokemonId));

            if (!entities.isEmpty()) {
                PacketDistributor.sendToServer(new PacketOpenKobblemonGui(entities.get(0).getId()));
            }
        }
    }

    private static void toggleMinion(UUID pokemonId) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level != null && mc.player != null) {
            var searchBox = mc.player.getBoundingBox().inflate(20.0);
            var entities = mc.level.getEntities(mc.player, searchBox,
                    entity -> entity.getUUID().equals(pokemonId));

            if (!entities.isEmpty()) {
                PacketDistributor.sendToServer(
                        new com.robertx22.mns_cobblemon.network.PacketRecruitMinion(entities.get(0).getId()));
            }
        }
    }
}
