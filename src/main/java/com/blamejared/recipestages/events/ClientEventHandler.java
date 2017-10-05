package com.blamejared.recipestages.events;

import com.blamejared.recipestages.handlers.Recipes;
import com.blamejared.recipestages.recipes.RecipeStage;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.recipe.*;
import net.darkhax.gamestages.capabilities.PlayerDataHandler;
import net.darkhax.gamestages.event.GameStageEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.blamejared.recipestages.compat.JEIPlugin.recipeRegistry;

public class ClientEventHandler {
    
    public ClientEventHandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void gameStageChange(GameStageEvent.Add event) {
        if(FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            String stage = event.getStageName();
            String guid = VanillaRecipeCategoryUid.CRAFTING;
            IRecipeRegistry reg = recipeRegistry;
            Recipes.recipes.stream().filter(rec -> rec instanceof RecipeStage && ((RecipeStage) rec).getTier().equalsIgnoreCase(stage)).forEach(rec -> {
                IRecipeWrapper wrapper = reg.getRecipeWrapper(rec, guid);
                reg.unhideRecipe(wrapper);
            });
        }
    }
    
    @SubscribeEvent
    public void gameStageChange(GameStageEvent.Remove event) {
        if(FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            String stage = event.getStageName();
            String guid = VanillaRecipeCategoryUid.CRAFTING;
            IRecipeRegistry reg = recipeRegistry;
            Recipes.recipes.stream().filter(rec -> rec instanceof RecipeStage && ((RecipeStage) rec).getTier().equalsIgnoreCase(stage)).forEach(rec -> {
                IRecipeWrapper wrapper = reg.getRecipeWrapper(rec, guid);
                reg.hideRecipe(wrapper);
            });
        }
    }
    
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if(FMLCommonHandler.instance().getEffectiveSide().isClient() && event.getEntity() instanceof EntityPlayer) {
            System.out.println(PlayerDataHandler.getStageData((EntityPlayer) event.getEntity()).getUnlockedStages());
            if(PlayerDataHandler.getStageData((EntityPlayer) event.getEntity()).getUnlockedStages().isEmpty()) {
                for(IRecipe recipe : Recipes.recipes) {
                    recipeRegistry.hideRecipe(recipeRegistry.getRecipeWrapper(recipe, VanillaRecipeCategoryUid.CRAFTING));
                }
            }
        }
    }
}
