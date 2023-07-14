package com.blamejared.recipestages;

import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ServerStuff {
    
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static boolean handleServer(Optional<AbstractContainerMenu> menu, String stage) {
        
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if(server != null) {
            PlayerList manager = server.getPlayerList();
            if(menu.isEmpty()) {
                if(ForgeHooks.getCraftingPlayer() != null) {
                    return GameStageHelper.getPlayerData(ForgeHooks.getCraftingPlayer()).hasStage(stage);
                }
                return false;
            }
            ServerPlayer foundPlayer = null;
            AbstractContainerMenu innerMenu = menu.get();
            for(ServerPlayer serverPlayerEntity : manager.getPlayers()) {
                if(serverPlayerEntity.containerMenu == innerMenu && innerMenu.stillValid(serverPlayerEntity)) {
                    if(foundPlayer != null) {
                        return false;
                    }
                    
                    foundPlayer = serverPlayerEntity;
                }
            }
            if(foundPlayer != null) {
                return GameStageHelper.getPlayerData(foundPlayer).hasStage(stage);
            }
            
            if(RecipeStages.CONTAINER_STAGES.getOrDefault(innerMenu.getClass().getName(), new HashSet<>())
                    .contains(stage)) {
                return true;
            }
            
            Set<String> packageStages = new HashSet<>();
            for(String s : RecipeStages.PACKAGE_STAGES.keySet()) {
                if(innerMenu.getClass().getName().startsWith(s)) {
                    packageStages.addAll(RecipeStages.PACKAGE_STAGES.get(s));
                }
            }
            return packageStages.contains(stage);
        }
        
        return false;
    }
    
}
