package com.blamejared.recipestages;

import com.blamejared.recipestages.proxy.CommonProxy;
import crafttweaker.*;
import crafttweaker.mc1120.CraftTweaker;
import crafttweaker.mc1120.furnace.MCFurnaceManager;
import crafttweaker.mc1120.recipes.MCRecipeManager;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;


import java.util.*;

import static com.blamejared.recipestages.reference.Reference.*;

@Mod(modid = MOD_ID, name = MOD_NAME, version = VERSION, dependencies = "required-after:crafttweaker;")
public class RecipeStages {
    
    
    @SidedProxy(clientSide = "com.blamejared.recipestages.proxy.ClientProxy", serverSide = "com.blamejared.recipestages.proxy.CommonProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.registerEvents();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
    
    @EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
    }
}
