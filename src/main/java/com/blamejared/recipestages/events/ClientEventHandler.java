package com.blamejared.recipestages.events;

import com.blamejared.recipestages.handlers.Recipes;
import com.blamejared.recipestages.recipes.RecipeStage;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.recipe.*;
import net.darkhax.gamestages.capabilities.PlayerDataHandler;
import net.darkhax.gamestages.event.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collection;

import static com.blamejared.recipestages.compat.JEIPlugin.recipeRegistry;

public class ClientEventHandler {
    
    public ClientEventHandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void gameStageChange(GameStageEvent.Added event) {
        if(FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            String stage = event.getStageName();
            String guid = VanillaRecipeCategoryUid.CRAFTING;
            IRecipeRegistry reg = recipeRegistry;
            for(IRecipe recipe : Recipes.recipes) {
                if(recipe instanceof RecipeStage) {
                    RecipeStage rec = (RecipeStage) recipe;
                    if(rec.getTier().equalsIgnoreCase(stage)) {
                        IRecipeWrapper wrapper = reg.getRecipeWrapper(rec, guid);
                        reg.unhideRecipe(wrapper);
                    }
                }
            }
            //            for(IRecipe recipe : Recipes.recipes) {
            //                recipeRegistry.hideRecipe(recipeRegistry.getRecipeWrapper(recipe, VanillaRecipeCategoryUid.CRAFTING));
            //            }
            //            PlayerDataHandler.IStageData data = PlayerDataHandler.getStageData(event.getPlayer());
            //            for(IRecipe recipe : Recipes.recipes) {
            //                if(data.getUnlockedStages().contains(((RecipeStage) recipe).getTier()) || event.getStageName().equalsIgnoreCase(((RecipeStage) recipe).getTier())) {
            //                    recipeRegistry.unhideRecipe(recipeRegistry.getRecipeWrapper(recipe, VanillaRecipeCategoryUid.CRAFTING));
            //                }
            //
            //            }
        }
    }
    
    @SubscribeEvent
    public void gameStageChange(GameStageEvent.Removed event) {
        if(FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            String stage = event.getStageName();
            String guid = VanillaRecipeCategoryUid.CRAFTING;
            IRecipeRegistry reg = recipeRegistry;
            for(IRecipe recipe : Recipes.recipes) {
                if(recipe instanceof RecipeStage) {
                    RecipeStage rec = (RecipeStage) recipe;
                    if(rec.getTier().equalsIgnoreCase(stage)) {
                        IRecipeWrapper wrapper = reg.getRecipeWrapper(rec, guid);
                        reg.hideRecipe(wrapper);
                    }
                }
            }
            //            for(IRecipe recipe : Recipes.recipes) {
            //                recipeRegistry.hideRecipe(recipeRegistry.getRecipeWrapper(recipe, VanillaRecipeCategoryUid.CRAFTING));
            //            }
            //            PlayerDataHandler.IStageData data = PlayerDataHandler.getStageData(event.getPlayer());
            //            for(IRecipe recipe : Recipes.recipes) {
            //                if(data.getUnlockedStages().contains(((RecipeStage) recipe).getTier()) && !event.getStageName().equalsIgnoreCase(((RecipeStage) recipe).getTier())) {
            //                    recipeRegistry.unhideRecipe(recipeRegistry.getRecipeWrapper(recipe, VanillaRecipeCategoryUid.CRAFTING));
            //                }
            //
            //            }
        }
    }
    
    //    @SubscribeEvent
    //    public void onStageData(StageDataEvent.SyncRecieved event) {
    //        for(IRecipe recipe : Recipes.recipes) {
    //            recipeRegistry.hideRecipe(recipeRegistry.getRecipeWrapper(recipe, VanillaRecipeCategoryUid.CRAFTING));
    //        }
    //        PlayerDataHandler.IStageData data = PlayerDataHandler.getStageData(event.getPlayer());
    //        for(IRecipe recipe : Recipes.recipes) {
    //            if(data.getUnlockedStages().contains(((RecipeStage) recipe).getTier()))
    //                recipeRegistry.unhideRecipe(recipeRegistry.getRecipeWrapper(recipe, VanillaRecipeCategoryUid.CRAFTING));
    //        }
    //    }
}
