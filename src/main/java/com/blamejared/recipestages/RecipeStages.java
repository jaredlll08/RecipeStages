package com.blamejared.recipestages;

import com.blamejared.recipestages.proxy.CommonProxy;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;


import static com.blamejared.recipestages.reference.Reference.*;

@Mod(modid = MOD_ID, name = MOD_NAME, version = VERSION)
public class RecipeStages {
    
    @SidedProxy(clientSide = "com.blamejared.recipestages.proxy.ClientProxy", serverSide = "com.blamejared.recipestages.proxy.CommonProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
    
    @EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
    }
}
