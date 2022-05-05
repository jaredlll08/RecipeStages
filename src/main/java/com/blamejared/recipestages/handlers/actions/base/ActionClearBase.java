package com.blamejared.recipestages.handlers.actions.base;

import com.blamejared.crafttweaker.api.action.recipe.ActionRecipeBase;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.recipestages.recipes.RecipeStage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingRecipe;

import java.util.List;
import java.util.Map;

public abstract class ActionClearBase extends ActionRecipeBase<CraftingRecipe> {
    
    public ActionClearBase(IRecipeManager<CraftingRecipe> manager) {
        
        super(manager);
    }
    
    protected void clearRecipes(List<Map.Entry<ResourceLocation, CraftingRecipe>> toChange) {
        
        for(Map.Entry<ResourceLocation, CraftingRecipe> entry : toChange) {
            String key = entry.getKey().toString();
            CraftingRecipe recipe = entry.getValue();
            if(recipe instanceof RecipeStage) {
                key = ((RecipeStage) entry.getValue()).getRecipe().getId().toString();
                recipe = ((RecipeStage) recipe).getRecipe();
            }
            ResourceLocation id = new ResourceLocation("recipestages", key.replaceAll(":", "_"));
            this.getManager().getRecipes().put(id, recipe);
        }
    }
    
}
