package com.blamejared.recipestages.compat;

public abstract class RecipeStagesLogger {
    
    public static RecipeStagesLogger instance;
    
    public abstract void error(String info, Object... o);
    
    public abstract void info(String info, Object... o);
    
}
