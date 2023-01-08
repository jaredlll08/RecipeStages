package com.blamejared.recipestages.recipes;

import com.blamejared.recipestages.RecipeStages;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.IShapedRecipe;

public class ShapedRecipeStage implements IShapedRecipe<CraftingContainer>, IStagedRecipe {
    
    private final ResourceLocation id;
    private final String stage;
    private final CraftingRecipe recipe;
    
    private final int width;
    private final int height;
    
    public ShapedRecipeStage(ResourceLocation id, String stage, CraftingRecipe recipe) {
        
        this.id = id;
        this.stage = stage;
        this.recipe = recipe;
        this.width = ((IShapedRecipe<CraftingContainer>) recipe).getRecipeWidth();
        this.height = ((IShapedRecipe<CraftingContainer>) recipe).getRecipeHeight();
    }
    
    @Override
    public ResourceLocation getId() {
        
        return id;
    }
    
    @Override
    public RecipeSerializer<?> getSerializer() {
        
        return RecipeStages.SHAPED_STAGE_SERIALIZER;
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
        
        return "ShapedRecipeStage{" +
                "id=" + id +
                ", stage='" + stage + '\'' +
                ", recipe=" + recipe +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
    
    public int getWidth() {
        
        return width;
    }
    
    public int getHeight() {
        
        return height;
    }
    
    @Override
    public int getRecipeWidth() {
        
        return getWidth();
    }
    
    @Override
    public int getRecipeHeight() {
        
        return getHeight();
    }
    
}