package com.blamejared.recipestages.proxy;

import com.blamejared.recipestages.events.*;
import com.blamejared.recipestages.handlers.Recipes;
import com.blamejared.recipestages.recipes.RecipeStage;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.recipe.*;
import net.darkhax.gamestages.GameStageHelper;
import net.darkhax.gamestages.data.IStageData;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.FMLCommonHandler;

import static com.blamejared.recipestages.compat.JEIPlugin.recipeRegistry;

public class ClientProxy extends CommonProxy {
    
    
    @Override
    public EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().player;
    }
    
    @Override
    public void registerEvents() {
        super.registerEvents();
        new ClientEventHandler();
    }
    
    @Override
    public boolean isClient() {
        return true;
    }
    
    @Override
    public void syncJEI(EntityPlayer player) {
        super.syncJEI(player);
        if(FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            if(recipeRegistry != null) {
                if(player == null || GameStageHelper.getPlayerData(player) == null) {
                    return;
                }
                for(IRecipe recipe : Recipes.recipes) {
                    IRecipeWrapper recipeWrapper = recipeRegistry.getRecipeWrapper(recipe, VanillaRecipeCategoryUid.CRAFTING);
                    if(recipeWrapper != null) {
                        recipeRegistry.hideRecipe(recipeWrapper);
                    }
                }
                String guid = VanillaRecipeCategoryUid.CRAFTING;
                IRecipeRegistry reg = recipeRegistry;
                for(IRecipe recipe : Recipes.recipes) {
                    if(recipe instanceof RecipeStage) {
                        RecipeStage rec = (RecipeStage) recipe;
                        IStageData stageData = GameStageHelper.getPlayerData(player);
                        if(stageData != null && stageData.hasStage(rec.getTier())) {
                            IRecipeWrapper wrapper = reg.getRecipeWrapper(rec, guid);
                            reg.unhideRecipe(wrapper);
                        }
                    }
                }
            }
        }
    }
}
