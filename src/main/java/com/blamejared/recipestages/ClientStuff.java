package com.blamejared.recipestages;

import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.client.Minecraft;

public class ClientStuff {
    public static  boolean handleClient(String stage){
        if(Minecraft.getInstance().player == null) {
            return true;
        }
        return GameStageHelper.getPlayerData(Minecraft.getInstance().player).hasStage(stage);
    }
}
