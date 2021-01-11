package com.blamejared.recipestages.handlers.actions;

import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.recipestages.RecipeStages;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ActionSetPackageStages implements IUndoableAction {
    
    private final String packageName;
    private final String[] stages;
    
    public ActionSetPackageStages(String packageName, String[] stages) {
        this.packageName = packageName;
        this.stages = stages;
    }
    
    @Override
    public void undo() {
        Set<String> strings = RecipeStages.packageStages.computeIfAbsent(packageName, s -> new HashSet<>());
        strings.removeAll(Arrays.asList(stages));
    }
    
    @Override
    public String describeUndo() {
        return "Removing the stages \"" + Arrays.toString(stages) + "\" of package: \"" + packageName + "\"";
    }
    
    @Override
    public void apply() {
        Set<String> strings = RecipeStages.packageStages.computeIfAbsent(packageName, s -> new HashSet<>());
        strings.addAll(Arrays.asList(stages));
        
    }
    
    @Override
    public String describe() {
        return "Set the stages of package: \"" + packageName + "\" to: " + Arrays.toString(stages);
    }
}
