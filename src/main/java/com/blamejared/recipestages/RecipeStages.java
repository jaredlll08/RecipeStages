package com.blamejared.recipestages;

import com.blamejared.recipestages.proxy.CommonProxy;
import crafttweaker.*;
import net.darkhax.bookshelf.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.*;

import java.util.*;

import static com.blamejared.recipestages.reference.Reference.*;

@Mod(modid = MOD_ID, name = MOD_NAME, version = VERSION, dependencies = "required-after:crafttweaker@[4.1.20,);after:gamestages@[2.0.90,)")
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
    
    }
    
    @EventHandler
    @SideOnly(Side.CLIENT)
    public void onFMLLoadComplete(FMLLoadCompleteEvent event) {
        // Add a resource reload listener to keep up to sync with JEI.
        ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(listener -> {
            
            if(Loader.isModLoaded("jei") && GameUtils.isClient()) {
                proxy.syncJEI(PlayerUtils.getClientPlayer());
            }
        });
    }
}
