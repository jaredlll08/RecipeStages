package com.blamejared.recipestages.compat;


import com.blamejared.recipestages.recipes.RecipeStage;
import mezz.jei.api.recipe.*;

public class StagedRecipeFactory implements IRecipeWrapperFactory<RecipeStage> {
    
    @Override
    public IRecipeWrapper getRecipeWrapper(RecipeStage recipe) {
        if(recipe.isShapeless()){
            return new StagedRecipeWrapperShapeless(JEIPlugin.jeiHelpers, recipe);
        }else{
            return new StagedRecipeWrapperShaped(JEIPlugin.jeiHelpers, recipe);
        }
    }
}
