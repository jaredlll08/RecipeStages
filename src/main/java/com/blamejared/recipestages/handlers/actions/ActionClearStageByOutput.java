package com.blamejared.recipestages.handlers.actions;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.MCItemStackMutable;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.recipestages.handlers.actions.base.ActionClearBase;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import org.apache.logging.log4j.Logger;

import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActionClearStageByOutput extends ActionClearBase {
    
    private final IIngredient output;
    
    public ActionClearStageByOutput(IRecipeManager<CraftingRecipe> manager, IIngredient output) {
        
        super(manager);
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
        
        toChange.forEach(entry -> this.getManager().getRecipeList().remove(entry.getKey()));
        clearRecipes(toChange);
    }
    
    @Override
    public String describe() {
        
        return "Clearing the stage of  \"" + Registry.RECIPE_TYPE.getKey(this.getManager()
                .getRecipeType()) + "\" recipes with output: " + this.output + "\"";
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
