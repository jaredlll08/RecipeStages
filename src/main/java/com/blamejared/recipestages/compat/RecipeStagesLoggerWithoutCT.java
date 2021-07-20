package com.blamejared.recipestages.compat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RecipeStagesLoggerWithoutCT extends RecipeStagesLogger {
    
    public static final Logger LOGGER = LogManager.getLogger("Recipe Stages");
    
    @Override
    public void error(String info, Object... o) {
        
        LOGGER.error(String.format(info, o));
    }
    
    @Override
    public void info(String info, Object... o) {
        
        LOGGER.info(String.format(info, o));
    }
    
}
