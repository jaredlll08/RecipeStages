package com.blamejared.recipestages.handlers.actions;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.recipestages.RecipeStages;
import org.apache.logging.log4j.Logger;

public class ActionSetJEILabel implements IUndoableAction {
    
    private final boolean value;
    
    public ActionSetJEILabel(boolean value) {
        
        this.value = value;
    }
    
    @Override
    public void undo() {
        
        RecipeStages.showJEILabel = !value;
    }
    
    @Override
    public String describeUndo() {
        
        return "Set show JEI Label to " + !value;
    }
    
    @Override
    public void apply() {
        
        RecipeStages.showJEILabel = value;
    }
    
    @Override
    public String describe() {
        
        return "Set show JEI Label to " + value;
    }
    
    @Override
    public String systemName() {
        
        return "RecipeStages";
    }
    
    @Override
    public boolean shouldApplyOn(IScriptLoadSource source, Logger logger) {
        
        return true;
    }
    
}
