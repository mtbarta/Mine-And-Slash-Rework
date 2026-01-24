package com.robertx22.mns_cobblemon.gui;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.robertx22.mns_cobblemon.core.KobblemonGearSlots;
import com.robertx22.mns_cobblemon.items.KobblemonItems;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;
import com.robertx22.mns_cobblemon.capability.KobblemonData;
import com.robertx22.mns_cobblemon.MnSCobblemonCompat;

public class KobblemonContainer extends AbstractContainerMenu {

    public static MenuType<KobblemonContainer> TYPE;

    private final PokemonEntity pokemon;

    public KobblemonContainer(int id, Inventory playerInv, PokemonEntity pokemon) {
        super(TYPE, id);
        this.pokemon = pokemon;

        // Fetch data
        KobblemonData data = pokemon.getData(MnSCobblemonCompat.KOBBLEMON_DATA);

        // Add slots
        // Using positions similar to vanilla armor slots
        // Slot 0: Top (Helmet position-ish)
        this.addSlot(new SlotItemHandler(data.getInventory(), 0, 8, 8));

        // Slot 1: Mid-Top (Chestplate position-ish)
        this.addSlot(new SlotItemHandler(data.getInventory(), 1, 8, 26));

        // Slot 2: Mid-Bottom (Leggings position-ish)
        this.addSlot(new SlotItemHandler(data.getInventory(), 2, 8, 44));

        // Slot 3: Bottom (Boots position-ish)
        this.addSlot(new SlotItemHandler(data.getInventory(), 3, 8, 62));

        layoutPlayerInventory(playerInv, 8, 84);
    }

    private void layoutPlayerInventory(Inventory playerInv, int x, int y) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInv, col + row * 9 + 9, x + col * 18, y + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInv, col, x + col * 18, y + 58));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return pokemon.isAlive() && pokemon.distanceTo(player) < 8.0f;
    }

    public PokemonEntity getPokemon() {
        return pokemon;
    }
}
