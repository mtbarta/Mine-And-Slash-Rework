package com.robertx22.mine_and_slash.a_libraries.jei;

import com.robertx22.mine_and_slash.database.data.profession.ProfessionRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.extensions.IRecipeCategoryExtension;
import org.jetbrains.annotations.Nullable;

public interface IProfessionCategoryExtension extends IRecipeCategoryExtension<ProfessionRecipe> {

    void setRecipe(IRecipeLayoutBuilder builder, ICraftingGridHelper craftingGridHelper, IFocusGroup focuses);

    default int getWidth() {
        return 0;
    }

    default int getHeight() {
        return 0;
    }
}
