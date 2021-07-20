package com.blamejared.recipestages.events;

import com.blamejared.recipestages.compat.JEIPlugin;
import net.darkhax.gamestages.data.GameStageSaveHandler;
import net.darkhax.gamestages.data.IStageData;
import net.darkhax.gamestages.event.StagesSyncedEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "recipestages", value = Dist.CLIENT)
public class ClientEventHandler {
    
    @SubscribeEvent
    public static void onGamestageSync(StagesSyncedEvent event) {
        
        JEIPlugin.sync(event.getData());
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void recipes(RecipesUpdatedEvent event) {
        
        IStageData data = GameStageSaveHandler.getClientData();
        if(data == null) {
            return;
        }
        JEIPlugin.sync(data);
    }
    
    
}
