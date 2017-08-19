package com.blamejared.recipestages.handlers;

import com.blamejared.recipestages.recipes.RecipeStage;
import com.blamejared.recipestages.reference.Reference;
import crafttweaker.*;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.*;
import crafttweaker.api.recipes.*;
import crafttweaker.mc1120.recipes.*;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.*;
import stanhebben.zenscript.annotations.*;

@ZenClass("mods.recipestages.Recipes")
@ZenRegister
public class Recipes {
    
    @ZenMethod
    public static void addShaped(String name, String tier, IItemStack output, IIngredient[][] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        ShapedRecipe recipe = new ShapedRecipe(name, output, ingredients, function, action, false);
        IRecipe irecipe = RecipeConverter.convert(recipe, new ResourceLocation(Reference.MOD_ID, name));
        CraftTweakerAPI.apply(new ActionAddRecipe(new RecipeStage(tier, irecipe).setRegistryName(new ResourceLocation(Reference.MOD_ID, name))));
    }
    
    
    @ZenMethod
    public static void addShapeless(String name, String tier, IItemStack output, IIngredient[] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        ShapelessRecipe recipe = new ShapelessRecipe(name, output, ingredients, function, action);
        IRecipe irecipe = RecipeConverter.convert(recipe, new ResourceLocation(Reference.MOD_ID, name));
        CraftTweakerAPI.apply(new ActionAddRecipe(new RecipeStage(tier, irecipe).setRegistryName(new ResourceLocation(Reference.MOD_ID, name))));
    }
    
    private static class ActionAddRecipe implements IAction {
        
        private final IRecipe recipe;
        
        public ActionAddRecipe(IRecipe recipe) {
            this.recipe = recipe;
        }
        
        @Override
        public void apply() {
            ForgeRegistries.RECIPES.register(recipe);
        }
        
        @Override
        public String describe() {
            return "Adding recipe for " + recipe.getRecipeOutput().getDisplayName();
        }
        
    }
}
