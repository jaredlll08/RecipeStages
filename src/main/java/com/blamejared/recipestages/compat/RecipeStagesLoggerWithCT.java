package com.blamejared.recipestages.compat;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;

public class RecipeStagesLoggerWithCT extends RecipeStagesLogger {
    
    @Override
    public void info(String message, Object... args) {
        
        CraftTweakerAPI.logInfo(message, args);
    }
    
    @Override
    public void error(String message, Object... args) {
        
        CraftTweakerAPI.logError(message, args);
    }
    
}
