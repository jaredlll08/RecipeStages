package com.blamejared.recipestages.handlers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker.impl.managers.CTCraftingTableManager;
import com.blamejared.crafttweaker.impl.recipes.CTRecipeShaped;
import com.blamejared.recipestages.recipes.RecipeStage;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;


@ZenCodeType.Name("mods.recipestages.Recipes")
@ZenRegister
public class Recipes {
    
    @ZenCodeType.Method
    public static void addShaped(String stage, String recipeName, IItemStack output, IIngredient[][] ingredients, @ZenCodeType.Optional IRecipeManager.RecipeFunctionMatrix recipeFunction) {
        recipeName = CTCraftingTableManager.INSTANCE.validateRecipeName(recipeName);
        CraftTweakerAPI.apply(new ActionAddRecipe(CTCraftingTableManager.INSTANCE, new RecipeStage(new ResourceLocation("recipestages", recipeName), stage, new CTRecipeShaped(recipeName, output, ingredients, false, recipeFunction), false), "shaped"));
    }
}
