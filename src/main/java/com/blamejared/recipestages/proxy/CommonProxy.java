package com.blamejared.recipestages.proxy;

import com.blamejared.recipestages.events.CommonEventHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
    
    public EntityPlayer getClientPlayer() {
        return null;
    }
    
    public void registerEvents() {
        MinecraftForge.EVENT_BUS.register(new CommonEventHandler());
    }
    
    public boolean isClient() {
        return false;
    }
    
    public void syncJEI(EntityPlayer player){
    
    }
}
