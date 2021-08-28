package com.blamejared.recipestages;

import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.*;

public class ServerStuff {
    
    public static boolean handleServer(CraftingInventory inv, String stage) {
        
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if(server != null) {
            PlayerList manager = server.getPlayerList();
            Container container = inv.menu;
            if(container == null) {
                if(ForgeHooks.getCraftingPlayer() != null) {
                    return GameStageHelper.getPlayerData(ForgeHooks.getCraftingPlayer()).hasStage(stage);
                }
                return false;
            }
            ServerPlayerEntity foundPlayer = null;
            for(ServerPlayerEntity serverPlayerEntity : manager.getPlayers()) {
                if(serverPlayerEntity.containerMenu == container && container.stillValid(serverPlayerEntity) && container
                        .isSynched(serverPlayerEntity)) {
                    if(foundPlayer != null) {
                        return false;
                    }
                    
                    foundPlayer = serverPlayerEntity;
                }
            }
            if(foundPlayer != null) {
                return GameStageHelper.getPlayerData(foundPlayer).hasStage(stage);
            }
            
            if(RecipeStages.CONTAINER_STAGES.getOrDefault(inv.menu.getClass().getName(), new HashSet<>())
                    .contains(stage)) {
                return true;
            }
            
            Set<String> packageStages = new HashSet<>();
            for(String s : RecipeStages.PACKAGE_STAGES.keySet()) {
                if(inv.menu.getClass().getName().startsWith(s)) {
                    packageStages.addAll(RecipeStages.PACKAGE_STAGES.get(s));
                }
            }
            return packageStages.contains(stage);
        }
        
        return false;
    }
    
}
