package com.blamejared.recipestages;

import com.blamejared.recipestages.events.ClientEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Mod("recipestages")
public class RecipeStages {
    
    
    public static boolean printContainers = false;
    public static boolean showJEILabel = true;
    public static final Map<String, Set<String>> packageStages = new HashMap<>();
    public static final Map<String, Set<String>> containerStages = new HashMap<>();
    
    public RecipeStages() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
    }
    
    private void doClientStuff(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }
    
}
