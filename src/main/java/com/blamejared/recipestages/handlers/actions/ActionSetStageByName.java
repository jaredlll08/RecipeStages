package com.blamejared.recipestages.handlers.actions;

import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.recipestages.handlers.actions.base.ActionStageBase;
import com.blamejared.recipestages.recipes.IStagedRecipe;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingRecipe;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActionSetStageByName extends ActionStageBase {
    
    private final ResourceLocation name;
    
    public ActionSetStageByName(IRecipeManager<CraftingRecipe> manager, String stage, ResourceLocation name) {
        
        super(manager, stage);
        this.name = name;
    }
    
    public void apply() {
        
        List<Map.Entry<ResourceLocation, CraftingRecipe>> toChange = new ArrayList<>();
        for(Map.Entry<ResourceLocation, CraftingRecipe> entry : this.getManager().getRecipes().entrySet()) {
            if(name.equals(entry.getKey())) {
                toChange.add(entry);
                continue;
            }
            if(entry.getValue() instanceof IStagedRecipe stagedRecipe) {
                if(name.equals(stagedRecipe.getRecipe().getId())) {
                    toChange.add(entry);
                }
            }
        }
        
        toChange.forEach(entry -> this.getManager().getRecipeList().remove(entry.getKey()));
        
        stageRecipes(toChange);
    }
    
    @Override
    public String describe() {
        
        return "Setting the stage of \"" + Registry.RECIPE_TYPE.getKey(this.getManager()
                .getRecipeType()) + "\" recipes with name: " + this.name + "\" to \"" + stage + "\"";
    }
    
    public boolean validate(Logger logger) {
        
        boolean containsKey = this.getManager().getRecipes().containsKey(this.name) || this.getManager()
                .getRecipes()
                .containsKey(new ResourceLocation("recipestages", this.name.toString().replaceAll(":", "_")));
        if(!containsKey) {
            logger.warn("No recipe with type: \"" + Registry.RECIPE_TYPE.getKey(this.getManager()
                    .getRecipeType()) + "\" and name: \"" + this.name + "\"");
        }
        
        return containsKey;
    }
    
}
