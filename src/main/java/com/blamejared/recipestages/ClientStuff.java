package com.blamejared.recipestages;

import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.common.ForgeHooks;

import java.util.Optional;

public class ClientStuff {
    
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static boolean handleClient(Optional<AbstractContainerMenu> menu, String stage) {
        
        Player player = Minecraft.getInstance().player != null ? Minecraft.getInstance().player : ForgeHooks.getCraftingPlayer();
        if(player == null) {
            return true;
        }
        return GameStageHelper.getPlayerData(player).hasStage(stage);
    }
    
}
