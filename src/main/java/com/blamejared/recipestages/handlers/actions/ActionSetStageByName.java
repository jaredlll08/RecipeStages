package com.blamejared.recipestages.handlers.actions;

import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.recipestages.handlers.actions.base.ActionSetStage;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingRecipe;

public class ActionSetStageByName extends ActionSetStage {
    
    public ActionSetStageByName(IRecipeManager<CraftingRecipe> manager, String stage, ResourceLocation name) {
        
        super(manager,
                stage,
                craftingRecipe -> craftingRecipe.getId().equals(name),
                action -> "Setting the stage of '%s' recipes with name: '%s' to '%s'".formatted(BuiltInRegistries.RECIPE_TYPE.getKey(action.getManager()
                        .getRecipeType()), name, stage));
    }
    
}
