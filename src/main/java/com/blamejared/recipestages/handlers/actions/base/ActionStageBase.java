package com.blamejared.recipestages.handlers.actions.base;

import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRecipeBase;
import com.blamejared.recipestages.recipes.RecipeStage;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IShapedRecipe;

import java.util.*;

public abstract class ActionStageBase extends ActionRecipeBase {
    
    protected final String stage;
    
    public ActionStageBase(IRecipeManager manager, String stage) {
        super(manager);
        this.stage = stage;
    }
    
    protected void stageRecipes(List<Map.Entry<ResourceLocation, IRecipe<?>>> toChange) {
        for(Map.Entry<ResourceLocation, IRecipe<?>> entry : toChange) {
            String key = entry.getKey().toString();
            IRecipe<?> recipe = entry.getValue();
            boolean shapeless = !(recipe instanceof IShapedRecipe);
            if(recipe instanceof RecipeStage) {
                key = ((RecipeStage) entry.getValue()).getRecipe().getId().toString();
                recipe = ((RecipeStage) recipe).getRecipe();
                shapeless = !(((RecipeStage) entry.getValue()).getRecipe() instanceof IShapedRecipe);
            }
            ResourceLocation id = new ResourceLocation("recipestages", key.replaceAll(":", "_"));
            RecipeStage recipeStage = new RecipeStage(id, stage, (IRecipe<CraftingInventory>) recipe, shapeless);
            this.getManager().getRecipes().put(id, recipeStage);
        }
    }
    
}
