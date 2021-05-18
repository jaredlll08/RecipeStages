package com.blamejared.recipestages.handlers.actions;

import com.blamejared.crafttweaker.api.exceptions.ScriptException;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.logger.ILogger;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.recipestages.handlers.actions.base.ActionStageBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.*;

public class ActionSetStageByOutput extends ActionStageBase {
    
    private final IIngredient output;
    
    public ActionSetStageByOutput(IRecipeManager manager, String stage, IIngredient output) {
        
        super(manager, stage);
        this.output = output;
    }
    
    public void apply() {
        
        List<Map.Entry<ResourceLocation, IRecipe<?>>> toChange = new ArrayList<>();
        for(Map.Entry<ResourceLocation, IRecipe<?>> entry : this.getManager().getRecipes().entrySet()) {
            ItemStack stack = entry.getValue().getResultItem();
            if(this.output.matches(new MCItemStackMutable(stack))) {
                toChange.add(entry);
            }
        }
        toChange.forEach(entry -> this.getManager().getRecipes().remove(entry.getKey()));
        stageRecipes(toChange);
    }
    
    @Override
    public String describe() {
        
        return "Setting the stage of  \"" + Registry.RECIPE_TYPE.getKey(this.getManager()
                .getRecipeType()) + "\" recipes with output: " + this.output + "\" to \"" + stage + "\"";
    }
    
    public boolean validate(ILogger logger) {
        
        if(this.output == null) {
            logger.throwingWarn("output cannot be null!", new ScriptException("output IItemStack cannot be null!"));
            return false;
        } else {
            return true;
        }
    }
    
}
