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
        // Held Item (Center-ish)
        this.addSlot(new SlotItemHandler(data.getInventory(), 0, 80, 20));

        // Battle Item (Left)
        this.addSlot(new SlotItemHandler(data.getInventory(), 1, 44, 20));

        // Training Item (Right)
        this.addSlot(new SlotItemHandler(data.getInventory(), 2, 116, 20));

        // Mega Stone (Top/Special) - visual placement TBD, putting below specific slot
        this.addSlot(new SlotItemHandler(data.getInventory(), 3, 80, 52));

        layoutPlayerInventory(playerInv, 8, 140);
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
