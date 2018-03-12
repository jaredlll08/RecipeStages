package com.blamejared.recipestages.events;

import com.blamejared.recipestages.RecipeStages;
import com.blamejared.recipestages.handlers.Recipes;
import com.blamejared.recipestages.recipes.RecipeStage;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.recipe.*;
import net.darkhax.bookshelf.util.GameUtils;
import net.darkhax.gamestages.capabilities.PlayerDataHandler;
import net.darkhax.gamestages.event.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.*;

import static com.blamejared.recipestages.compat.JEIPlugin.recipeRegistry;

public class ClientEventHandler {
    
    public ClientEventHandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onGamestageSync(StageDataEvent.SyncRecieved event) {
        
        if(GameUtils.isClient()) {
            RecipeStages.proxy.syncJEI(event.getPlayer());
        }
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientSync(GameStageEvent.ClientSync event) {
        
        if(GameUtils.isClient()) {
            
            RecipeStages.proxy.syncJEI(event.getPlayer());
        }
    }
    
    
}
