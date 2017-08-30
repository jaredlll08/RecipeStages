package com.blamejared.recipestages.compat;

import com.blamejared.recipestages.recipes.RecipeStage;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.*;
import mezz.jei.gui.recipes.RecipeInfoIcon;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.*;


public class StagedRecipeWrapper implements IRecipeWrapper {
    
    private RecipeStage recipe;
    
    public StagedRecipeWrapper(RecipeStage recipe) {
        this.recipe = recipe;
    }
    
    @Override
    public void getIngredients(IIngredients ingredients) {
        List<ItemStack> inputs = new LinkedList<>();
        for(Ingredient ingredient : recipe.getIngredients()) {
            if(!ingredient.apply(ItemStack.EMPTY))
                inputs.add(ingredient.getMatchingStacks()[0]);
            else {
                inputs.add(ItemStack.EMPTY);
            }
        }
        ingredients.setInputs(ItemStack.class, inputs);
        ingredients.setOutput(ItemStack.class, recipe.getRecipeOutput());
    }
    
    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        minecraft.fontRenderer.drawString("Stage: " + recipe.getTier(),0,-11,0);
    }
    
    
}
