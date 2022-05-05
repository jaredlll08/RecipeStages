package com.blamejared.recipestages.handlers.actions;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.recipestages.RecipeStages;

public class ActionSetPrintContainers implements IUndoableAction {
    
    private final boolean value;
    
    public ActionSetPrintContainers(boolean value) {
        
        this.value = value;
    }
    
    @Override
    public void undo() {
        
        RecipeStages.printContainer = !value;
    }
    
    @Override
    public String describeUndo() {
        
        return "Set printing container names to " + !value;
    }
    
    @Override
    public void apply() {
        
        RecipeStages.printContainer = value;
    }
    
    @Override
    public String describe() {
        
        return "Set printing container names to " + value;
    }
    
    @Override
    public boolean shouldApplyOn(IScriptLoadSource source) {
        
        return true;
    }
    
}
