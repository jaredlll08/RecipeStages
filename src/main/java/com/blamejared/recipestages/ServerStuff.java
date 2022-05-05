package com.blamejared.recipestages;

import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.HashSet;
import java.util.Set;

public class ServerStuff {
    
    public static boolean handleServer(CraftingContainer inv, String stage) {
        
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if(server != null) {
            PlayerList manager = server.getPlayerList();
            AbstractContainerMenu container = inv.menu;
            if(container == null) {
                if(ForgeHooks.getCraftingPlayer() != null) {
                    return GameStageHelper.getPlayerData(ForgeHooks.getCraftingPlayer()).hasStage(stage);
                }
                return false;
            }
            ServerPlayer foundPlayer = null;
            for(ServerPlayer serverPlayerEntity : manager.getPlayers()) {
                if(serverPlayerEntity.containerMenu == container && container.stillValid(serverPlayerEntity)) {
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
