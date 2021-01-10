package com.blamejared.recipestages.events;

import com.blamejared.recipestages.RecipeStages;
import net.darkhax.gamestages.event.StagesSyncedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEventHandler {
    
    public ClientEventHandler() {
    
    }
    
    @SubscribeEvent
    public void onGamestageSync(StagesSyncedEvent event) {
//        RecipeStages.proxy.syncJEI(event.getEntityPlayer());
    }
    
}
