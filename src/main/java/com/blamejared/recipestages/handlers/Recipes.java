package com.blamejared.recipestages.handlers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.MirrorAxis;
import com.blamejared.crafttweaker.api.recipe.fun.RecipeFunction1D;
import com.blamejared.crafttweaker.api.recipe.fun.RecipeFunction2D;
import com.blamejared.crafttweaker.api.recipe.manager.CraftingTableRecipeManager;
import com.blamejared.crafttweaker.api.recipe.type.CTShapedRecipe;
import com.blamejared.crafttweaker.api.recipe.type.CTShapelessRecipe;
import com.blamejared.recipestages.handlers.actions.*;
import com.blamejared.recipestages.recipes.RecipeStage;
import com.blamejared.recipestages.recipes.ShapedRecipeStage;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name("mods.recipestages.Recipes")
@ZenRegister
public class Recipes {
    
    @ZenCodeType.Method
    public static void showJEILabel(boolean showLabel) {
        
        CraftTweakerAPI.apply(new ActionSetJEILabel(showLabel));
    }
    
    @ZenCodeType.Method
    public static void setPrintContainers(boolean printContainers) {
        
        
        CraftTweakerAPI.apply(new ActionSetPrintContainers(printContainers));
    }
    
    @ZenCodeType.Method
    public static void setPackageStages(String packageName, String... stages) {
        
        CraftTweakerAPI.apply(new ActionSetPackageStages(packageName, stages));
    }
    
    @ZenCodeType.Method
    public static void setContainerStages(String containerName, String... stages) {
        
        CraftTweakerAPI.apply(new ActionSetContainerStages(containerName, stages));
    }
    
    @ZenCodeType.Method
    public static void addShaped(String stage, String recipeName, IItemStack output, IIngredient[][] ingredients, @ZenCodeType.Optional RecipeFunction2D recipeFunction) {
        
        recipeName = CraftingTableRecipeManager.INSTANCE.fixRecipeName(recipeName);
        
        CTShapedRecipe innerRecipe = new CTShapedRecipe(recipeName, output, ingredients, MirrorAxis.NONE, recipeFunction);
        
        ShapedRecipeStage recipe = new ShapedRecipeStage(new ResourceLocation("recipestages", recipeName), stage, innerRecipe);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(CraftingTableRecipeManager.INSTANCE, recipe, "shaped"));
    }
    
    @ZenCodeType.Method
    public static void addShapedMirrored(String stage, String recipeName, MirrorAxis mirrorAxis, IItemStack output, IIngredient[][] ingredients, @ZenCodeType.Optional RecipeFunction2D recipeFunction) {
        
        recipeName = CraftingTableRecipeManager.INSTANCE.fixRecipeName(recipeName);
        CTShapedRecipe innerRecipe = new CTShapedRecipe(recipeName, output, ingredients, mirrorAxis, recipeFunction);
        ShapedRecipeStage recipe = new ShapedRecipeStage(new ResourceLocation("recipestages", recipeName), stage, innerRecipe);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(CraftingTableRecipeManager.INSTANCE, recipe, "mirroring shaped"));
    }
    
    @ZenCodeType.Method
    public static void addShapeless(String stage, String recipeName, IItemStack output, IIngredient[] ingredients, @ZenCodeType.Optional RecipeFunction1D recipeFunction) {
        
        recipeName = CraftingTableRecipeManager.INSTANCE.fixRecipeName(recipeName);
        
        CTShapelessRecipe innerRecipe = new CTShapelessRecipe(recipeName, output, ingredients, recipeFunction);
        RecipeStage recipe = new RecipeStage(new ResourceLocation("recipestages", recipeName), stage, innerRecipe, true);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(CraftingTableRecipeManager.INSTANCE, recipe, "shapeless"));
    }
    
    
    @ZenCodeType.Method
    public static void setRecipeStage(String stage, IIngredient output) {
        
        CraftTweakerAPI.apply(new ActionSetStageByOutput(CraftingTableRecipeManager.INSTANCE, stage, output));
    }
    
    @ZenCodeType.Method
    public static void setRecipeStage(String stage, ResourceLocation name) {
        
        CraftTweakerAPI.apply(new ActionSetStageByName(CraftingTableRecipeManager.INSTANCE, stage, name));
    }
    
    @ZenCodeType.Method
    public static void clearRecipeStage(IIngredient output) {
        
        CraftTweakerAPI.apply(new ActionClearStageByOutput(CraftingTableRecipeManager.INSTANCE, output));
    }
    
    @ZenCodeType.Method
    public static void clearRecipeStage(ResourceLocation name) {
        
        CraftTweakerAPI.apply(new ActionClearStageByName(CraftingTableRecipeManager.INSTANCE, name));
    }
    
    @ZenCodeType.Method
    public static void setRecipeStageByMod(String stage, String modid) {
        
        CraftTweakerAPI.apply(new ActionSetStageByMod(CraftingTableRecipeManager.INSTANCE, stage, modid));
    }
    
}
