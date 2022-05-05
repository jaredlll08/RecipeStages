package com.blamejared.recipestages.handlers.actions;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.recipestages.RecipeStages;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ActionSetContainerStages implements IUndoableAction {
    
    private final String containerName;
    private final String[] stages;
    
    public ActionSetContainerStages(String containerName, String[] stages) {
        
        this.containerName = containerName;
        this.stages = stages;
    }
    
    @Override
    public void undo() {
        
        Set<String> strings = RecipeStages.CONTAINER_STAGES.computeIfAbsent(containerName, s -> new HashSet<>());
        Arrays.asList(stages).forEach(strings::remove);
    }
    
    @Override
    public String describeUndo() {
        
        return "Removing the stages \"" + Arrays.toString(stages) + "\" of container: \"" + containerName + "\"";
    }
    
    @Override
    public void apply() {
        
        Set<String> strings = RecipeStages.CONTAINER_STAGES.computeIfAbsent(containerName, s -> new HashSet<>());
        strings.addAll(Arrays.asList(stages));
        
    }
    
    @Override
    public String describe() {
        
        return "Set the stages of container: \"" + containerName + "\" to: " + Arrays.toString(stages);
    }
    
}
