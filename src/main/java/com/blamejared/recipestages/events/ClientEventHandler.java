package com.blamejared.recipestages.events;

import com.blamejared.recipestages.RecipeStages;
import net.darkhax.gamestages.event.StagesSyncedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.*;

public class ClientEventHandler {
    
    public ClientEventHandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onGamestageSync(StagesSyncedEvent event) {
        RecipeStages.proxy.syncJEI(event.getEntityPlayer());
    }
    
}
