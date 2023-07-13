package com.blamejared.recipestages.handlers.actions;

import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.recipestages.handlers.actions.base.ActionSetStage;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.CraftingRecipe;

public class ActionSetStageByMod extends ActionSetStage {
    
    public ActionSetStageByMod(IRecipeManager<CraftingRecipe> manager, String stage, String modid) {
        
        super(manager,
                stage,
                craftingRecipe -> craftingRecipe.getId().getNamespace().equals(modid),
                action -> "Setting the stage of '%s' recipes with modid: '%s' to '%s'".formatted(BuiltInRegistries.RECIPE_TYPE.getKey(action.getManager()
                        .getRecipeType()), modid, stage));
    }
    
}
