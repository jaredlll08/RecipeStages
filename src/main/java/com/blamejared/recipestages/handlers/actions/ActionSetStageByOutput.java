package com.blamejared.recipestages.handlers.actions;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.recipestages.handlers.actions.base.ActionSetStage;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.CraftingRecipe;

public class ActionSetStageByOutput extends ActionSetStage {
    
    public ActionSetStageByOutput(IRecipeManager<CraftingRecipe> manager, String stage, IIngredient output) {
        
        super(manager,
                stage,
                craftingRecipe -> output.matches(IItemStack.of(craftingRecipe.getResultItem())),
                action -> "Setting the stage of '%s' recipes with output: '%s' to '%s'".formatted(
                        Registry.RECIPE_TYPE.getKey(action.getManager().getRecipeType()),
                        output,
                        stage));
    }
    
}
