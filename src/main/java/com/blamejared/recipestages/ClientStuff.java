package com.blamejared.recipestages;

import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraftforge.common.ForgeHooks;

public class ClientStuff {
    
    public static boolean handleClient(CraftingContainer inv, String stage) {
        
        
        Player player = Minecraft.getInstance().player != null ? Minecraft.getInstance().player : ForgeHooks.getCraftingPlayer();
        if(player == null) {
            return true;
        }
        return GameStageHelper.getPlayerData(player).hasStage(stage);
    }
    
}
