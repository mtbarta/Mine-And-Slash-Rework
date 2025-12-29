package com.robertx22.library_of_exile.recipe;


import net.minecraft.resources.ResourceLocation;
// import net.minecraftforge.common.crafting.ConditionalRecipe;
// import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;

import java.util.function.Consumer;

public class ConditionalRecipeData {
    // public ConditionalRecipe.Builder builder;
    public ResourceLocation id;

    public ConditionalRecipeData(ResourceLocation id) {
        // this.builder = builder;
        this.id = id;
    }

    public static ConditionalRecipeData ofModLoaded(String id, ResourceLocation loc, Consumer<net.minecraft.data.recipes.RecipeOutput> recipe) {
        // ConditionalRecipe.Builder sup = new ConditionalRecipe.Builder().addCondition(new ModLoadedCondition(id)).addRecipe(recipe);
        // return new ConditionalRecipeData(sup, loc);
        return new ConditionalRecipeData(loc);
    }
}
