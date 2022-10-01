package com.blamejared.recipestages.handlers.actions;

import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.recipestages.handlers.actions.base.ActionStageBase;
import com.blamejared.recipestages.recipes.RecipeStage;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActionSetStageByMod extends ActionStageBase {
    
    private final String modid;
    
    public ActionSetStageByMod(IRecipeManager<CraftingRecipe> manager, String stage, String modid) {
        
        super(manager, stage);
        this.modid = modid;
    }
    
    public void apply() {
        
        List<Map.Entry<ResourceLocation, CraftingRecipe>> toChange = new ArrayList<>();
        for(Map.Entry<ResourceLocation, CraftingRecipe> entry : this.getManager().getRecipes().entrySet()) {
            if(entry.getKey().getNamespace().equals(modid)) {
                toChange.add(entry);
            }
            if(entry.getValue() instanceof RecipeStage) {
    
                CraftingRecipe recipe = ((RecipeStage) entry.getValue()).getRecipe();
                
                ResourceLocation id = recipe.getId();
                if(id.getNamespace().equals(modid)) {
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
                .getRecipeType()) + "\" recipes with modid: " + this.modid + "\" to \"" + stage + "\"";
    }
    
}
