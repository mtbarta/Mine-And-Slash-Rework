package com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.baubles;

import com.robertx22.mine_and_slash.a_libraries.curios.interfaces.IRing;
import com.robertx22.mine_and_slash.uncommon.IShapedRecipe;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.VanillaMaterial;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases.BaseBaublesItem;
import net.minecraft.data.recipes.ShapedRecipeBuilder;

import net.minecraft.world.item.Item;

public class ItemRing extends BaseBaublesItem implements IRing, IShapedRecipe {

    VanillaMaterial mat;

    private static Item.Properties createProperties(VanillaMaterial mat) {
        return new Item.Properties().durability(500 + mat.getChestplateDurability() * 2);
    }

    public ItemRing(VanillaMaterial mat) {
        super(createProperties(mat), "Ring");
        this.mat = mat;
    }

    @Override
    public ShapedRecipeBuilder getRecipe() {
        return shaped(this)
                .define('X', mat.mat.item)
                .pattern(" X ")
                .pattern("X X")
                .pattern(" X ")
                .unlockedBy("player_level", trigger());
    }
}
