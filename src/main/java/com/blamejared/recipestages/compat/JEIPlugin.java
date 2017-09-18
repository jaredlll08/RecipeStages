package com.blamejared.recipestages.compat;


import com.blamejared.recipestages.handlers.Recipes;
import com.blamejared.recipestages.recipes.RecipeStage;
import mezz.jei.api.*;
import mezz.jei.api.recipe.*;
import mezz.jei.recipes.RecipeRegistry;
import net.minecraft.item.crafting.IRecipe;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {
    
    public static IRecipeRegistry recipeRegistry;
    
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IJeiHelpers helpers = registry.getJeiHelpers();
//        registry.addRecipeCategories(new StagedRecipeCategory(helpers.getGuiHelper()));
    }
    
    @Override
    public void register(IModRegistry registry) {
        registry.handleRecipes(RecipeStage.class, new StagedRecipeFactory(), VanillaRecipeCategoryUid.CRAFTING);
    }
    
    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        recipeRegistry = jeiRuntime.getRecipeRegistry();
//        for(Object o : recipeRegistry.getRecipeWrappers(recipeRegistry.getRecipeCategory(VanillaRecipeCategoryUid.CRAFTING))) {
//            recipeRegistry.hideRecipe((IRecipeWrapper) o);
//        }
    }
}