package com.blamejared.recipestages;

import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraftforge.common.ForgeHooks;

public class ClientStuff {
    
    public static boolean handleClient(CraftingInventory inv, String stage) {
        
        
        PlayerEntity player = Minecraft.getInstance().player != null ? Minecraft.getInstance().player : ForgeHooks.getCraftingPlayer();
        if(player == null) {
            return true;
        }
        return GameStageHelper.getPlayerData(player).hasStage(stage);
    }
    
}
