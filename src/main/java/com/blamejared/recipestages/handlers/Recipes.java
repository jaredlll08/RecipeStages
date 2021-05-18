package com.blamejared.recipestages.handlers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.managers.CTCraftingTableManager;
import com.blamejared.crafttweaker.impl.recipes.*;
import com.blamejared.recipestages.handlers.actions.*;
import com.blamejared.recipestages.recipes.RecipeStage;
import net.minecraft.util.ResourceLocation;
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
    public static void addShaped(String stage, String recipeName, IItemStack output, IIngredient[][] ingredients, @ZenCodeType.Optional IRecipeManager.RecipeFunctionMatrix recipeFunction) {
        
        recipeName = CTCraftingTableManager.INSTANCE.validateRecipeName(recipeName);
        
        CTRecipeShaped innerRecipe = new CTRecipeShaped(recipeName, output, ingredients, false, recipeFunction);
        RecipeStage recipe = new RecipeStage(new ResourceLocation("recipestages", recipeName), stage, innerRecipe, false);
        CraftTweakerAPI.apply(new ActionAddRecipe(CTCraftingTableManager.INSTANCE, recipe, "shaped"));
    }
    
    @ZenCodeType.Method
    public static void addShapedMirrored(String stage, String recipeName, IItemStack output, IIngredient[][] ingredients, @ZenCodeType.Optional IRecipeManager.RecipeFunctionMatrix recipeFunction) {
        
        recipeName = CTCraftingTableManager.INSTANCE.validateRecipeName(recipeName);
        CTRecipeShaped innerRecipe = new CTRecipeShaped(recipeName, output, ingredients, true, recipeFunction);
        RecipeStage recipe = new RecipeStage(new ResourceLocation("recipestages", recipeName), stage, innerRecipe, false);
        CraftTweakerAPI.apply(new ActionAddRecipe(CTCraftingTableManager.INSTANCE, recipe, "mirroring shaped"));
    }
    
    @ZenCodeType.Method
    public static void addShapeless(String stage, String recipeName, IItemStack output, IIngredient[] ingredients, @ZenCodeType.Optional IRecipeManager.RecipeFunctionArray recipeFunction) {
        
        recipeName = CTCraftingTableManager.INSTANCE.validateRecipeName(recipeName);
        
        CTRecipeShapeless innerRecipe = new CTRecipeShapeless(recipeName, output, ingredients, recipeFunction);
        RecipeStage recipe = new RecipeStage(new ResourceLocation("recipestages", recipeName), stage, innerRecipe, true);
        CraftTweakerAPI.apply(new ActionAddRecipe(CTCraftingTableManager.INSTANCE, recipe, "shapeless"));
    }
    
    
    @ZenCodeType.Method
    public static void setRecipeStage(String stage, IIngredient output) {
        
        CraftTweakerAPI.apply(new ActionSetStageByOutput(CTCraftingTableManager.INSTANCE, stage, output));
    }
    
    @ZenCodeType.Method
    public static void setRecipeStage(String stage, ResourceLocation name) {
        
        CraftTweakerAPI.apply(new ActionSetStageByName(CTCraftingTableManager.INSTANCE, stage, name));
    }
    
    @ZenCodeType.Method
    public static void clearRecipeStage(IIngredient output) {
        
        CraftTweakerAPI.apply(new ActionClearStageByOutput(CTCraftingTableManager.INSTANCE, output));
    }
    
    @ZenCodeType.Method
    public static void clearRecipeStage(ResourceLocation name) {
        
        CraftTweakerAPI.apply(new ActionClearStageByName(CTCraftingTableManager.INSTANCE, name));
    }
    
    @ZenCodeType.Method
    public static void setRecipeStageByMod(String stage, String modid) {
        
        CraftTweakerAPI.apply(new ActionSetStageByMod(CTCraftingTableManager.INSTANCE, stage, modid));
    }
    
}
