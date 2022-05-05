package com.blamejared.recipestages.handlers.actions;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.MCItemStackMutable;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.recipestages.handlers.actions.base.ActionStageBase;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import org.apache.logging.log4j.Logger;

import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActionSetStageByOutput extends ActionStageBase {
    
    private final IIngredient output;
    
    public ActionSetStageByOutput(IRecipeManager<CraftingRecipe> manager, String stage, IIngredient output) {
        
        super(manager, stage);
        this.output = output;
    }
    
    public void apply() {
        
        List<Map.Entry<ResourceLocation, CraftingRecipe>> toChange = new ArrayList<>();
        for(Map.Entry<ResourceLocation, CraftingRecipe> entry : this.getManager().getRecipes().entrySet()) {
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
    
    public boolean validate(Logger logger) {
        
        if(this.output == null) {
            logger.warn("output cannot be null!", new ScriptException("output IItemStack cannot be null!"));
            return false;
        } else {
            return true;
        }
    }
    
}
