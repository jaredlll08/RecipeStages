package com.blamejared.recipestages.compat;


import com.blamejared.recipestages.handlers.Recipes;
import com.blamejared.recipestages.recipes.RecipeStage;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.recipe.*;
import net.minecraft.item.crafting.IRecipe;

import java.util.List;

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
        //        registry.addAdvancedGuiHandlers(new IAdvancedGuiHandler<GuiContainer>() {
        //
        //            @Nullable
        //            @Override
        //            public List<Rectangle> getGuiExtraAreas(GuiContainer guiContainer) {
        //                List<Rectangle> rects = new ArrayList<>();
        //                rects.add(new Rectangle(guiContainer.width-100,16,16,16));
        //                return rects;
        //            }
        //
        //            @Override
        //            public Class<GuiContainer> getGuiContainerClass() {
        //                return GuiContainer.class;
        //            }
        //        });
        ingredientRegistry = registry.getIngredientRegistry();
        registry.handleRecipes(RecipeStage.class, new StagedRecipeFactory(), VanillaRecipeCategoryUid.CRAFTING);
    }
    
    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        recipeRegistry = jeiRuntime.getRecipeRegistry();
        for(List<IRecipe> recipes : Recipes.recipes.values()) {
            for(IRecipe recipe : recipes) {
                IRecipeWrapper recipeWrapper = recipeRegistry.getRecipeWrapper(recipe, VanillaRecipeCategoryUid.CRAFTING);
                if(recipeWrapper != null) {
                    recipeRegistry.hideRecipe(recipeWrapper);
                }
            }
        }
    }
}