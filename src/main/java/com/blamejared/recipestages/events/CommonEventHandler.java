package com.blamejared.recipestages.events;

import com.blamejared.recipestages.RecipeStages;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.events.ActionApplyEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.blamejared.recipestages.reference.Reference.MOD_NAME;

public class CommonEventHandler {
    
    @SubscribeEvent
    public void onActionApply(ActionApplyEvent.Post event) {
        try {
            RecipeStages.LATE_REMOVALS.forEach(CraftTweakerAPI::apply);
            RecipeStages.LATE_ADDITIONS.forEach(CraftTweakerAPI::apply);
        } catch(Exception e) {
            e.printStackTrace();
            CraftTweakerAPI.logError("Problems while loading " + MOD_NAME + " scripts!", e);
        }
    }
}
