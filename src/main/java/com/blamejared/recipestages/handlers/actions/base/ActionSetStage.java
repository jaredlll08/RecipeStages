package com.blamejared.recipestages.handlers.actions.base;

import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.recipestages.recipes.IStagedRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class ActionSetStage extends ActionStageBase {
    
    private final Predicate<CraftingRecipe> predicate;
    private Function<ActionStageBase, String> describeFunction;
    
    public ActionSetStage(IRecipeManager<CraftingRecipe> manager, String stage, Predicate<CraftingRecipe> predicate) {
        
        super(manager, stage);
        this.predicate = predicate;
        this.describeFunction = action -> "Setting the stage of '%s' recipes that match a custom predicate".formatted(BuiltInRegistries.RECIPE_TYPE.getKey(this.getManager()
                .getRecipeType()));
    }
    
    public ActionSetStage(IRecipeManager<CraftingRecipe> manager, String stage, Predicate<CraftingRecipe> predicate, Function<ActionStageBase, String> describeFunction) {
        
        super(manager, stage);
        this.predicate = predicate;
        this.describeFunction = describeFunction;
    }
    
    public void apply() {
        
        List<Map.Entry<ResourceLocation, CraftingRecipe>> toChange = new ArrayList<>();
        for(Map.Entry<ResourceLocation, CraftingRecipe> entry : this.getManager().getRecipes().entrySet()) {
            CraftingRecipe recipe = entry.getValue();
            if(recipe instanceof IStagedRecipe staged) {
                recipe = staged.getRecipe();
            }
            if(predicate.test(recipe)) {
                toChange.add(entry);
            }
        }
        
        toChange.forEach(entry -> this.getManager().getRecipeList().remove(entry.getKey()));
        
        stageRecipes(toChange);
    }
    
    @Override
    public String describe() {
        
        return describeFunction.apply(this);
    }
    
}
