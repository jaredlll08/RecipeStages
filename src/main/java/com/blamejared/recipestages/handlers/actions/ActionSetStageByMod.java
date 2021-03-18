package com.blamejared.recipestages.handlers.actions;

import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.recipestages.handlers.actions.base.ActionStageBase;
import com.blamejared.recipestages.recipes.RecipeStage;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.*;

public class ActionSetStageByMod extends ActionStageBase {
    
    private final String modid;
    
    public ActionSetStageByMod(IRecipeManager manager, String stage, String modid) {
        super(manager, stage);
        this.modid = modid;
    }
    
    public void apply() {
        List<Map.Entry<ResourceLocation, IRecipe<?>>> toChange = new ArrayList<>();
        for(Map.Entry<ResourceLocation, IRecipe<?>> entry : this.getManager().getRecipes().entrySet()) {
            if(entry.getKey().getNamespace().equals(modid)) {
                toChange.add(entry);
            }
            if(entry.getValue() instanceof RecipeStage) {
                
                IRecipe<CraftingInventory> recipe = ((RecipeStage) entry.getValue()).getRecipe();
                
                ResourceLocation id = recipe.getId();
                if(id.getNamespace().equals(modid)) {
                    toChange.add(entry);
                }
            }
        }
        toChange.forEach(entry -> this.getManager().getRecipes().remove(entry.getKey()));
        stageRecipes(toChange);
    }
    
    @Override
    public String describe() {
        return "Setting the stage of  \"" + Registry.RECIPE_TYPE.getKey(this.getManager().getRecipeType()) + "\" recipes with modid: " + this.modid + "\" to \"" + stage + "\"";
    }
}
