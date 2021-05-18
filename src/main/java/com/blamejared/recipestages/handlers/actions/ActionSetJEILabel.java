package com.blamejared.recipestages.handlers.actions;

import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.recipestages.RecipeStages;
import net.minecraftforge.fml.LogicalSide;

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
    public boolean shouldApplyOn(LogicalSide side) {
        
        return true;
    }
    
}
