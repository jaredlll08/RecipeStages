package com.blamejared.recipestages.handlers;

import com.blamejared.recipestages.recipes.RecipeStage;
import com.blamejared.recipestages.reference.Reference;
import crafttweaker.*;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.*;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.recipes.*;
import crafttweaker.mc1120.CraftTweaker;
import crafttweaker.mc1120.recipes.*;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import net.minecraft.item.crafting.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.*;
import stanhebben.zenscript.annotations.*;
import stanhebben.zenscript.annotations.Optional;

import java.util.*;


@ZenClass("mods.recipestages.Recipes")
@ZenRegister
public class Recipes {
    
    public static List<IRecipe> recipes = new LinkedList<>();
    public static ActionSetOutputStages actionSetOutputStages;
    private static TIntSet usedHashes = new TIntHashSet();
    
    @ZenMethod
    public static void addShaped(String name, String stage, IItemStack output, IIngredient[][] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        ShapedRecipe recipe = new ShapedRecipe(name, output, ingredients, function, action, false);
        IRecipe irecipe = RecipeConverter.convert(recipe, new ResourceLocation("crafttweaker", name));
        CraftTweaker.LATE_ACTIONS.add(new ActionAddRecipe(new RecipeStage(stage, irecipe, false, recipe.getWidth(), recipe.getHeight()).setRegistryName(new ResourceLocation("crafttweaker", name))));
    }
    
    @ZenMethod
    public static void addShapedMirrored(String name, String stage, IItemStack output, IIngredient[][] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        ShapedRecipe recipe = new ShapedRecipe(name, output, ingredients, function, action, true);
        IRecipe irecipe = RecipeConverter.convert(recipe, new ResourceLocation("crafttweaker", name));
        CraftTweaker.LATE_ACTIONS.add(new ActionAddRecipe(new RecipeStage(stage, irecipe, false, recipe.getWidth(), recipe.getHeight()).setRegistryName(new ResourceLocation("crafttweaker", name))));
    }
    
    
    @ZenMethod
    public static void addShapeless(String name, String stage, IItemStack output, IIngredient[] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        boolean valid = output != null;
        for(IIngredient ing : ingredients) {
            if(ing == null) {
                valid = false;
            }
        }
        if(!valid) {
            CraftTweakerAPI.logError("Null not allowed in shapeless recipes! Recipe for: " + output + " not created!");
            return;
        }
        ShapelessRecipe recipe = new ShapelessRecipe(name, output, ingredients, function, action);
        IRecipe irecipe = RecipeConverter.convert(recipe, new ResourceLocation("crafttweaker", name));
        CraftTweaker.LATE_ACTIONS.add(new ActionAddRecipe(new RecipeStage(stage, irecipe, true).setRegistryName(new ResourceLocation("crafttweaker", name))));
    }
    
    @ZenMethod
    public static void addShaped(String stage, IItemStack output, IIngredient[][] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        String name = calculateName(output, ingredients);
        ShapedRecipe recipe = new ShapedRecipe(name, output, ingredients, function, action, false);
        IRecipe irecipe = RecipeConverter.convert(recipe, new ResourceLocation("crafttweaker", name));
        CraftTweaker.LATE_ACTIONS.add(new ActionAddRecipe(new RecipeStage(stage, irecipe, false, recipe.getWidth(), recipe.getHeight()).setRegistryName(new ResourceLocation("crafttweaker", name))));
    }
    
    @ZenMethod
    public static void addShapedMirrored(String stage, IItemStack output, IIngredient[][] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        String name = calculateName(output, ingredients);
        ShapedRecipe recipe = new ShapedRecipe(name, output, ingredients, function, action, true);
        IRecipe irecipe = RecipeConverter.convert(recipe, new ResourceLocation("crafttweaker", name));
        CraftTweaker.LATE_ACTIONS.add(new ActionAddRecipe(new RecipeStage(stage, irecipe, false, recipe.getWidth(), recipe.getHeight()).setRegistryName(new ResourceLocation("crafttweaker", name))));
    }
    
    @ZenMethod
    public static void addShapeless(String stage, IItemStack output, IIngredient[] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        boolean valid = output != null;
        for(IIngredient ing : ingredients) {
            if(ing == null) {
                valid = false;
            }
        }
        if(!valid) {
            CraftTweakerAPI.logError("Null not allowed in shapeless recipes! Recipe for: " + output + " not created!");
            return;
        }
        String name = calculateNameShapeless(output, ingredients);
        ShapelessRecipe recipe = new ShapelessRecipe(name, output, ingredients, function, action);
        IRecipe irecipe = RecipeConverter.convert(recipe, new ResourceLocation("crafttweaker", name));
        CraftTweaker.LATE_ACTIONS.add(new ActionAddRecipe(new RecipeStage(stage, irecipe, true).setRegistryName(new ResourceLocation("crafttweaker", name))));
    }
    
    @ZenMethod
    public static void setRecipeStage(String stage, IIngredient output) {
        if (actionSetOutputStages == null) {
            actionSetOutputStages = new ActionSetOutputStages();
            CraftTweaker.LATE_ACTIONS.add(actionSetOutputStages);
        }
        actionSetOutputStages.addOutput(stage, output);
    }
    
    @ZenMethod
    public static void setRecipeStage(String stage, String recipeName) {
        IRecipe recipe = ForgeRegistries.RECIPES.getValue(new ResourceLocation(recipeName));
        CraftTweaker.LATE_ACTIONS.add(new ActionSetStage(Collections.singletonList(recipe), stage));
        
    }
    
    private static class ActionSetStage implements IAction {
        
        private final List<IRecipe> recipes;
        private final String stage;
        
        public ActionSetStage(List<IRecipe> recipe, String stage) {
            this.recipes = recipe;
            this.stage = stage;
        }
        
        @Override
        public void apply() {
            for(IRecipe irecipe : recipes) {
                replaceRecipe(stage, irecipe);
            }
        }
        
        @Override
        public String describe() {
            return "Setting the stage for recipes that output: " + recipes.get(0).getRecipeOutput().getDisplayName();
        }
    }

    private static class ActionSetOutputStages implements IAction {
        private final Map<String, List<IItemStack>> outputs = new HashMap<>();

        public void addOutput(String stage, IIngredient output) {
            List<IItemStack> outputsForStage = this.outputs.computeIfAbsent(stage, k -> new ArrayList<>());
            outputsForStage.addAll(output.getItems());
        }

        @Override
        public void apply() {
            for (IRecipe recipe : ForgeRegistries.RECIPES.getValues()) {
                IItemStack stack = CraftTweakerMC.getIItemStack(recipe.getRecipeOutput());
                if (stack != null) {
                    for (Map.Entry<String, List<IItemStack>> entry : outputs.entrySet()) {
                        for (IItemStack output : entry.getValue()) {
                            if (stack.matches(output)) {
                                replaceRecipe(entry.getKey(), recipe);
                                break;
                            }
                        }
                    }
                }
            }
            actionSetOutputStages = null;
        }

        @Override
        public String describe() {
            return "Setting the stages for recipes based on their output stack.";
        }
    }

    private static void replaceRecipe(String stage, IRecipe iRecipe) {
        ResourceLocation registryName = iRecipe.getRegistryName();
        if (registryName == null)
            return;

        int width = 0, height = 0;
        if(iRecipe instanceof IShapedRecipe) {
            width = ((IShapedRecipe) iRecipe).getRecipeWidth();
            height = ((IShapedRecipe) iRecipe).getRecipeHeight();
        } else if(iRecipe instanceof ShapedRecipe) {
            width = ((ShapedRecipe) iRecipe).getWidth();
            height = ((ShapedRecipe) iRecipe).getHeight();
        } else if(iRecipe instanceof ShapedRecipeAdvanced) {
            width = ((ShapedRecipe) ((ShapedRecipeAdvanced) iRecipe).getRecipe()).getWidth();
            height = ((ShapedRecipe) ((ShapedRecipeAdvanced) iRecipe).getRecipe()).getHeight();
        }

        boolean shapeless = (width == 0 && height == 0);
        IRecipe recipe = new RecipeStage(stage, iRecipe, shapeless, width, height);
        setRecipeRegistryName(recipe, registryName);
        ForgeRegistries.RECIPES.register(recipe);
        Recipes.recipes.add(recipe);
    }

    private static void setRecipeRegistryName(IRecipe recipe, ResourceLocation registryName) {
        Loader loader = Loader.instance();
        ModContainer activeModContainer = loader.activeModContainer();
        ModContainer modContainer = loader.getIndexedModList().get(registryName.getResourceDomain());
        if (modContainer != null) {
            loader.setActiveModContainer(modContainer);
        }
        try {
            recipe.setRegistryName(registryName);
        } catch (Throwable ignored) {}
        loader.setActiveModContainer(activeModContainer);
    }
    
    private static String calculateName(IIngredient output, IIngredient[][] ingredients) {
        StringBuilder sb = new StringBuilder();
        sb.append(saveToString(output));
        
        for(IIngredient[] ingredient : ingredients) {
            for(IIngredient iIngredient : ingredient) {
                sb.append(saveToString(iIngredient));
            }
        }
        
        int hash = sb.toString().hashCode();
        while(usedHashes.contains(hash))
            ++hash;
        usedHashes.add(hash);
        
        return "ct_shaped" + hash;
    }
    
    public static String calculateNameShapeless(IIngredient output, IIngredient[] ingredients) {
        StringBuilder sb = new StringBuilder();
        sb.append(saveToString(output));
        
        for(IIngredient ingredient : ingredients) {
            sb.append(saveToString(ingredient));
        }
        
        int hash = sb.toString().hashCode();
        while(usedHashes.contains(hash))
            ++hash;
        usedHashes.add(hash);
        
        return "ct_shapeless" + hash;
    }
    
    public static String saveToString(IIngredient ingredient) {
        if(ingredient == null) {
            return "_";
        } else {
            return ingredient.toString();
        }
    }
    
    public static String cleanRecipeName(String s) {
        if(s.contains(":"))
            CraftTweakerAPI.logWarning("String may not contain a \":\"");
        return s.replace(":", "_");
    }
    
    private static class ActionAddRecipe implements IAction {
        
        private final IRecipe recipe;
        
        public ActionAddRecipe(IRecipe recipe) {
            this.recipe = recipe;
        }
        
        @Override
        public void apply() {
            ForgeRegistries.RECIPES.register(recipe);
            recipes.add(recipe);
        }
        
        @Override
        public String describe() {
            return "Adding recipe for " + recipe.getRecipeOutput().getDisplayName();
        }
        
    }
    
    
}
