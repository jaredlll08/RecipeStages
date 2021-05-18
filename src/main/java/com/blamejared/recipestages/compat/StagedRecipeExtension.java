package com.blamejared.recipestages.compat;

import com.blamejared.recipestages.RecipeStages;
import com.blamejared.recipestages.recipes.RecipeStage;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiIngredientGroup;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICustomCraftingCategoryExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import java.util.List;

public class StagedRecipeExtension implements ICustomCraftingCategoryExtension {
    
    private final RecipeStage recipe;
    
    public StagedRecipeExtension(RecipeStage recipeStage) {
        
        this.recipe = recipeStage;
    }
    
    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IIngredients ingredients) {
        
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
        
        List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
        List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);
        
        if(!recipe.isShapeless()) {
            setInputs(recipeLayout.getItemStacks(), inputs, recipe.getWidth(), recipe.getHeight());
        } else {
            for(int i = 0; i < inputs.size(); i++) {
                recipeLayout.getItemStacks().set(i + 1, inputs.get(i));
            }
            recipeLayout.setShapeless();
        }
        recipeLayout.getItemStacks().set(0, outputs.get(0));
        
    }
    
    @Override
    public void setIngredients(IIngredients ingredients) {
        
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
    }
    
    @Override
    public void drawInfo(int recipeWidth, int recipeHeight, MatrixStack matrixStack, double mouseX, double mouseY) {
        
        if(RecipeStages.showJEILabel) {
            Minecraft.getInstance().font.draw(matrixStack, I18n.get("gui.rs.tip.stage", recipe.getStage()), 0, -11, 0x00000000);
        }
    }
    
    
    public <T> void setInputs(IGuiIngredientGroup<T> ingredientGroup, List<List<T>> inputs, int width, int height) {
        
        for(int i = 0; i < inputs.size(); i++) {
            List<T> recipeItem = inputs.get(i);
            int index = getCraftingIndex(i, width, height);
            
            setInput(ingredientGroup, index, recipeItem);
        }
    }
    
    private <T> void setInput(IGuiIngredientGroup<T> guiIngredients, int inputIndex, List<T> input) {
        
        guiIngredients.set(1 + inputIndex, input);
    }
    
    private int getCraftingIndex(int i, int width, int height) {
        
        int index;
        if(width == 1) {
            if(height == 3) {
                index = (i * 3) + 1;
            } else if(height == 2) {
                index = (i * 3) + 1;
            } else {
                index = 4;
            }
        } else if(height == 1) {
            index = i + 3;
        } else if(width == 2) {
            index = i;
            if(i > 1) {
                index++;
                if(i > 3) {
                    index++;
                }
            }
        } else if(height == 2) {
            index = i + 3;
        } else {
            index = i;
        }
        return index;
    }
    
}
