package com.blamejared.recipestages.proxy;

import net.minecraft.entity.player.EntityPlayer;

public class CommonProxy {
    
    public EntityPlayer getClientPlayer() {
        return null;
    }
    
    public void registerEvents() {
    
    }
    
    public boolean isClient() {
        return false;
    }
    
    public void syncJEI(EntityPlayer player){
    
    }
}
