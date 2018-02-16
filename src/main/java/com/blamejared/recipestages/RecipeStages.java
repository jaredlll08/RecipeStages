package com.blamejared.recipestages;

import com.blamejared.recipestages.proxy.CommonProxy;
import crafttweaker.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;

import java.util.*;

import static com.blamejared.recipestages.reference.Reference.*;

@Mod(modid = MOD_ID, name = MOD_NAME, version = VERSION, dependencies = "required-after:crafttweaker;")
public class RecipeStages {
    
    
    public static final List<IAction> LATE_ADDITIONS = new LinkedList<>();
    public static final List<IAction> LATE_REMOVALS = new LinkedList<>();
    
    @SidedProxy(clientSide = "com.blamejared.recipestages.proxy.ClientProxy", serverSide = "com.blamejared.recipestages.proxy.CommonProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.registerEvents();
    }
    
    @EventHandler
    public void onFMLPostInitialization(FMLPostInitializationEvent event) {
        try {
            LATE_REMOVALS.forEach(CraftTweakerAPI::apply);
            LATE_ADDITIONS.forEach(CraftTweakerAPI::apply);
        } catch(Exception e) {
            e.printStackTrace();
            CraftTweakerAPI.logError("Problems while loading " + MOD_NAME + " scripts!", e);
        }
    }
}
