package com.blamejared.recipestages.handlers.actions.base;

import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRecipeBase;
import com.blamejared.recipestages.recipes.RecipeStage;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;

public abstract class ActionClearBase extends ActionRecipeBase {
    
    public ActionClearBase(IRecipeManager manager) {
        super(manager);
    }
    
    protected void clearRecipes(List<Map.Entry<ResourceLocation, IRecipe<?>>> toChange) {
        for(Map.Entry<ResourceLocation, IRecipe<?>> entry : toChange) {
            String key = entry.getKey().toString();
            IRecipe<?> recipe = entry.getValue();
            if(recipe instanceof RecipeStage) {
                key = ((RecipeStage) entry.getValue()).getRecipe().getId().toString();
                recipe = ((RecipeStage) recipe).getRecipe();
            }
            ResourceLocation id = new ResourceLocation("recipestages", key.replaceAll(":", "_"));
            this.getManager().getRecipes().put(id, recipe);
        }
    }
    
}
