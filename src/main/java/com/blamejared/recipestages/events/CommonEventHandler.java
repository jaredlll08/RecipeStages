package com.blamejared.recipestages.events;

import com.blamejared.recipestages.compat.JEIPlugin;
import com.blamejared.recipestages.handlers.Recipes;
import com.blamejared.recipestages.recipes.RecipeStage;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.recipe.*;
import net.darkhax.gamestages.event.GameStageEvent;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommonEventHandler {
    
    public CommonEventHandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void gameStageChange(GameStageEvent.Add event) {
//        String stage = event.getStageName();
//        String guid = VanillaRecipeCategoryUid.CRAFTING;
//        IRecipeRegistry reg = JEIPlugin.recipeRegistry;
//        for(IRecipe recipe : Recipes.recipes) {
//            if(recipe instanceof RecipeStage) {
//                RecipeStage rec = (RecipeStage) recipe;
//                if(rec.getTier().equalsIgnoreCase(stage)) {
//                    IRecipeWrapper wrapper = reg.getRecipeWrapper(recipe, guid);
//                    reg.unhideRecipe(wrapper);
//                }
//            }
//        }
    }
    
    @SubscribeEvent
    public void gameStageChange(GameStageEvent.Remove event) {
//        String stage = event.getStageName();
//        String guid = VanillaRecipeCategoryUid.CRAFTING;
//        IRecipeRegistry reg = JEIPlugin.recipeRegistry;
//        for(IRecipe recipe : Recipes.recipes) {
//            if(recipe instanceof RecipeStage) {
//                RecipeStage rec = (RecipeStage) recipe;
//                if(rec.getTier().equalsIgnoreCase(stage))
//                    reg.hideRecipe(reg.getRecipeWrapper(recipe, guid));
//            }
//        }
    }
    
    @SubscribeEvent
    public void onGameStageClientSync(GameStageEvent.ClientSync event) {
    
    }
}
