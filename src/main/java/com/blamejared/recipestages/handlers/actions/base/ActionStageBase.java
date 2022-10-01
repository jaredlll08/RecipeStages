package com.blamejared.recipestages.handlers.actions.base;

import com.blamejared.crafttweaker.api.action.recipe.ActionRecipeBase;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.recipestages.recipes.RecipeStage;
import com.blamejared.recipestages.recipes.ShapedRecipeStage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraftforge.common.crafting.IShapedRecipe;

import java.util.List;
import java.util.Map;

public abstract class ActionStageBase extends ActionRecipeBase<CraftingRecipe> {
    
    protected final String stage;
    
    public ActionStageBase(IRecipeManager<CraftingRecipe> manager, String stage) {
        
        super(manager);
        this.stage = stage;
    }
    
    protected void stageRecipes(List<Map.Entry<ResourceLocation, CraftingRecipe>> toChange) {
        
        for(Map.Entry<ResourceLocation, CraftingRecipe> entry : toChange) {
            String key = entry.getKey().toString();
            CraftingRecipe recipe = entry.getValue();
            boolean shapeless = !(recipe instanceof IShapedRecipe);
            if(recipe instanceof RecipeStage) {
                key = ((RecipeStage) entry.getValue()).getRecipe().getId().toString();
                recipe = ((RecipeStage) recipe).getRecipe();
                shapeless = !(((RecipeStage) entry.getValue()).getRecipe() instanceof IShapedRecipe);
            }
            ResourceLocation id = new ResourceLocation("recipestages", key.replaceAll(":", "_"));
            CraftingRecipe recipeStage;
            if(recipe instanceof IShapedRecipe<?>) {
                recipeStage = new ShapedRecipeStage(id, stage, recipe);
            } else {
                recipeStage = new RecipeStage(id, stage, recipe, shapeless);
            }
            this.getManager().getRecipeList().add(id, recipeStage);
            
        }
    }
    
}
