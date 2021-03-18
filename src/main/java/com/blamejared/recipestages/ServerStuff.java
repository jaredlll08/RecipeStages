package com.blamejared.recipestages;

import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.*;

public class ServerStuff {
    public static boolean handleServer(CraftingInventory inv, String stage){
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if(server != null) {
            PlayerList manager = server.getPlayerList();
            Container container = inv.eventHandler;
            if(container == null) {
                return false;
            }
            ServerPlayerEntity foundPlayer = null;
            for(ServerPlayerEntity serverPlayerEntity : manager.getPlayers()) {
                ServerPlayerEntity entityPlayerMP = (ServerPlayerEntity) serverPlayerEntity;
                if(entityPlayerMP.openContainer == container && container.canInteractWith(entityPlayerMP) && container.getCanCraft(entityPlayerMP)) {
                    if(foundPlayer != null) {
                        return false;
                    }
                    
                    foundPlayer = entityPlayerMP;
                }
            }
            if(foundPlayer != null) {
                return GameStageHelper.getPlayerData(foundPlayer).hasStage(stage);
            }
            
            Set<String> crafterStages = RecipeStages.containerStages.getOrDefault(inv.eventHandler.getClass().getName(), new HashSet<>());
            if(crafterStages.contains(stage)) {
                return true;
            }
            
            Set<String> packageStages = new HashSet<>();
            for(String s : RecipeStages.packageStages.keySet()) {
                if(inv.eventHandler.getClass().getName().startsWith(s)){
                    packageStages.addAll(RecipeStages.packageStages.get(s));
                }
            }
            return packageStages.contains(stage);
        }
        
        return false;
    }
}
