package com.blamejared.recipestages.compat;


import com.blamejared.recipestages.handlers.Recipes;
import com.blamejared.recipestages.recipes.RecipeStage;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.recipe.*;
import mezz.jei.recipes.RecipeRegistry;
import net.minecraft.item.crafting.IRecipe;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {
    
    public static IRecipeRegistry recipeRegistry;
    public static IJeiHelpers jeiHelpers;
    public static IIngredientRegistry ingredientRegistry;
    
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        jeiHelpers = registry.getJeiHelpers();
    }
    
    @Override
    public void register(IModRegistry registry) {
        ingredientRegistry = registry.getIngredientRegistry();
        registry.handleRecipes(RecipeStage.class, new StagedRecipeFactory(), VanillaRecipeCategoryUid.CRAFTING);
    }
    
    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        recipeRegistry = jeiRuntime.getRecipeRegistry();
        for(IRecipe recipe : Recipes.recipes) {
            recipeRegistry.hideRecipe(recipeRegistry.getRecipeWrapper(recipe, VanillaRecipeCategoryUid.CRAFTING));
        }
    }
}