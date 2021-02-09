package com.blamejared.recipestages.proxy;

import com.blamejared.recipestages.events.ClientEventHandler;
import com.blamejared.recipestages.config.Configurations;
import com.blamejared.recipestages.handlers.Recipes;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.recipe.*;
import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.IRecipe;

import java.util.*;

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
        if(recipeRegistry != null) {
            if(player == null || GameStageHelper.getPlayerData(player) == null) {
                return;
            }
            Recipes.recipes.values().forEach(list -> {
                for(IRecipe recipe : list) {
                    IRecipeWrapper recipeWrapper = recipeRegistry.getRecipeWrapper(recipe, VanillaRecipeCategoryUid.CRAFTING);
                    if(recipeWrapper != null && Configurations.showRecipe == false) {
                        recipeRegistry.hideRecipe(recipeWrapper);
                    }
                }
            });
            List<IRecipe> recipes = new ArrayList<>();
            
            String guid = VanillaRecipeCategoryUid.CRAFTING;
            IRecipeRegistry reg = recipeRegistry;
            for(String key : Recipes.recipes.keySet()) {
                if(GameStageHelper.clientHasStage(player, key)) {
                    recipes.addAll(Recipes.recipes.get(key));
                }
            }
            for(IRecipe recipe : recipes) {
                IRecipeWrapper wrapper = reg.getRecipeWrapper(recipe, guid);
                reg.unhideRecipe(wrapper);
            }
        }
    }
}
