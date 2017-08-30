package com.blamejared.recipestages.compat;


import com.blamejared.recipestages.recipes.RecipeStage;
import mezz.jei.api.recipe.*;

public class StagedRecipeFactory implements IRecipeWrapperFactory<RecipeStage> {
    
    @Override
    public IRecipeWrapper getRecipeWrapper(RecipeStage recipe) {
        return new StagedRecipeWrapper(recipe);
    }
}
