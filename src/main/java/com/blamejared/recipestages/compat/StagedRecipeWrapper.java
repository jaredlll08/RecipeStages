package com.blamejared.recipestages.compat;

import com.blamejared.recipestages.recipes.RecipeStage;
import crafttweaker.api.recipes.*;
import crafttweaker.mc1120.recipes.*;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.*;
import mezz.jei.api.recipe.wrapper.*;
import mezz.jei.plugins.vanilla.crafting.*;
import mezz.jei.recipes.BrokenCraftingRecipeException;
import mezz.jei.util.ErrorUtil;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.config.HoverChecker;
import net.minecraftforge.oredict.*;

import java.util.*;

import static com.blamejared.recipestages.compat.JEIPlugin.jeiHelpers;


public class StagedRecipeWrapper implements IRecipeWrapper {
    
    private RecipeStage recipe;
    
    public StagedRecipeWrapper(RecipeStage recipe) {
        this.recipe = recipe;
    }
    
    @Override
    public void getIngredients(IIngredients ingredients) {
        ItemStack recipeOutput = recipe.getRecipeOutput();
        IStackHelper stackHelper = jeiHelpers.getStackHelper();
        
        try {
            List<List<ItemStack>> inputLists = stackHelper.expandRecipeItemStackInputs(recipe.getIngredients());
            ingredients.setInputLists(ItemStack.class, inputLists);
            ingredients.setOutput(ItemStack.class, recipeOutput);
        } catch(RuntimeException e) {
            String info = ErrorUtil.getInfoFromBrokenCraftingRecipe(recipe, recipe.getIngredients(), recipeOutput);
            throw new BrokenCraftingRecipeException(info, e);
        }
    }
    
    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        ResourceLocation registryName = recipe.getRegistryName();
        minecraft.fontRenderer.drawString("Stage: " + recipe.getTier(), 0, -11, 0);
    }
    
    public RecipeStage getRecipe() {
        return recipe;
    }
    
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        //        ResourceLocation registryName = recipe.getRegistryName();
        //        if(registryName != null) {
        //            return recipeInfoIcon.getTooltipStrings(registryName, mouseX, mouseY);
        //        }
        return Collections.emptyList();
    }
}
