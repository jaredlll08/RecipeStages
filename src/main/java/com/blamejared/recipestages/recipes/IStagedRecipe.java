package com.blamejared.recipestages.recipes;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingRecipe;

public interface IStagedRecipe {
    
    String getStage();
    ResourceLocation getId();
    
    CraftingRecipe getRecipe();
}
