package com.blamejared.recipestages.handlers.actions;

import com.blamejared.crafttweaker.api.logger.ILogger;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.recipestages.handlers.actions.base.ActionStageBase;
import com.blamejared.recipestages.recipes.RecipeStage;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.*;

public class ActionSetStageByName extends ActionStageBase {
    
    private final ResourceLocation name;
    
    public ActionSetStageByName(IRecipeManager manager, String stage, ResourceLocation name) {
        
        super(manager, stage);
        this.name = name;
    }
    
    public void apply() {
        
        List<Map.Entry<ResourceLocation, IRecipe<?>>> toChange = new ArrayList<>();
        for(Map.Entry<ResourceLocation, IRecipe<?>> entry : this.getManager().getRecipes().entrySet()) {
            if(name.equals(entry.getKey())) {
                toChange.add(entry);
                continue;
            }
            if(entry.getValue() instanceof RecipeStage) {
                if(name.equals(((RecipeStage) entry.getValue()).getRecipe().getId())) {
                    toChange.add(entry);
                }
            }
        }
        
        toChange.forEach(entry -> this.getManager().getRecipes().remove(entry.getKey()));
        
        stageRecipes(toChange);
    }
    
    @Override
    public String describe() {
        
        return "Setting the stage of  \"" + Registry.RECIPE_TYPE.getKey(this.getManager()
                .getRecipeType()) + "\" recipes with name: " + this.name + "\" to \"" + stage + "\"";
    }
    
    public boolean validate(ILogger logger) {
        
        boolean containsKey = this.getManager().getRecipes().containsKey(this.name) || this.getManager()
                .getRecipes()
                .containsKey(new ResourceLocation("recipestages", this.name.toString().replaceAll(":", "_")));
        if(!containsKey) {
            logger.warning("No recipe with type: \"" + Registry.RECIPE_TYPE.getKey(this.getManager()
                    .getRecipeType()) + "\" and name: \"" + this.name + "\"");
        }
        
        return containsKey;
    }
    
}
