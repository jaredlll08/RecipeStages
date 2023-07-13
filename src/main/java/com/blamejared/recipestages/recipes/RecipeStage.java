package com.blamejared.recipestages.recipes;

import com.blamejared.recipestages.RecipeStages;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.IShapedRecipe;

public class RecipeStage implements IStagedRecipe {
    
    private final ResourceLocation id;
    private final String stage;
    private final CraftingRecipe recipe;
    
    private final boolean shapeless;
    
    private int width = 0;
    private int height = 0;
    
    public RecipeStage(ResourceLocation id, String stage, CraftingRecipe recipe, boolean shapeless) {
        
        this.id = id;
        this.stage = stage;
        this.recipe = recipe;
        this.shapeless = shapeless;
        // maintains prior compat even though we have ShapedRecipeStage now
        if(recipe instanceof IShapedRecipe) {
            this.width = ((IShapedRecipe<CraftingContainer>) recipe).getRecipeWidth();
            this.height = ((IShapedRecipe<CraftingContainer>) recipe).getRecipeHeight();
        }
    }
    
    @Override
    public ResourceLocation getId() {
        
        return id;
    }
    
    @Override
    public RecipeSerializer<?> getSerializer() {
        
        return RecipeStages.STAGE_SERIALIZER;
    }
    
    @Override
    public CraftingRecipe getRecipe() {
        
        return recipe;
    }
    
    @Override
    public String getStage() {
        
        return stage;
    }
    
    @Override
    public String toString() {
        
        return "RecipeStage{" +
                "id=" + id +
                ", stage='" + stage + '\'' +
                ", recipe=" + recipe +
                ", shapeless=" + shapeless +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
    
    public boolean isShapeless() {
        
        return shapeless;
    }
    
    public int getWidth() {
        
        return width;
    }
    
    public int getHeight() {
        
        return height;
    }
    
}