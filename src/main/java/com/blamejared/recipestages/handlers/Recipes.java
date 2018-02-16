package com.blamejared.recipestages.handlers;

import com.blamejared.recipestages.RecipeStages;
import com.blamejared.recipestages.recipes.RecipeStage;
import crafttweaker.*;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.*;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.recipes.*;
import crafttweaker.mc1120.recipes.*;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.*;

import java.util.*;
import java.util.regex.*;


@ZenClass("mods.recipestages.Recipes")
@ZenRegister
public class Recipes {
    
    public static List<IRecipe> recipes = new LinkedList<>();
    public static ActionSetOutputStages actionSetOutputStages;
    public static ActionSetRegexStages actionSetRegexStages;
    public static ActionSetNameStages actionSetNameStages;
    public static ActionSetModidStages actionSetModidStages;
    
    
    private static TIntSet usedHashes = new TIntHashSet();
    
    public static Map<String, String[]> crafterStages = new HashMap<>();
    public static Map<String, String[]> packageStages = new HashMap<>();
    
    public static boolean printContainers = false;
    
    @ZenMethod
    public static void setPrintContainers(boolean print) {
        CraftTweakerAPI.apply(new ActionSetPrinting(print));
    }
    
    @ZenMethod
    public static void setContainerStage(String container, String[] stage) {
        CraftTweakerAPI.apply(new ActionSetCrafter(container, stage));
    }
    
    @ZenMethod
    public static void setPackageStage(String pack, String[] stage) {
        CraftTweakerAPI.apply(new ActionSetPack(pack, stage));
    }
    
    @ZenMethod
    public static void addShaped(String name, String stage, IItemStack output, IIngredient[][] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        ShapedRecipe recipe = new ShapedRecipe(name, output, ingredients, function, action, false);
        IRecipe irecipe = RecipeConverter.convert(recipe, new ResourceLocation("crafttweaker", name));
        RecipeStages.LATE_ADDITIONS.add(new ActionAddRecipe(recipe, new RecipeStage(stage, irecipe, false, recipe.getWidth(), recipe.getHeight()).setRegistryName(new ResourceLocation("crafttweaker", name))));
    }
    
    @ZenMethod
    public static void addShapedMirrored(String name, String stage, IItemStack output, IIngredient[][] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        ShapedRecipe recipe = new ShapedRecipe(name, output, ingredients, function, action, true);
        IRecipe irecipe = RecipeConverter.convert(recipe, new ResourceLocation("crafttweaker", name));
        RecipeStages.LATE_ADDITIONS.add(new ActionAddRecipe(recipe, new RecipeStage(stage, irecipe, false, recipe.getWidth(), recipe.getHeight()).setRegistryName(new ResourceLocation("crafttweaker", name))));
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
        RecipeStages.LATE_ADDITIONS.add(new ActionAddRecipe(recipe, new RecipeStage(stage, irecipe, true).setRegistryName(new ResourceLocation("crafttweaker", name))));
    }
    
    @ZenMethod
    public static void addShaped(String stage, IItemStack output, IIngredient[][] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        String name = calculateName(output, ingredients);
        ShapedRecipe recipe = new ShapedRecipe(name, output, ingredients, function, action, false);
        IRecipe irecipe = RecipeConverter.convert(recipe, new ResourceLocation("crafttweaker", name));
        RecipeStages.LATE_ADDITIONS.add(new ActionAddRecipe(recipe, new RecipeStage(stage, irecipe, false, recipe.getWidth(), recipe.getHeight()).setRegistryName(new ResourceLocation("crafttweaker", name))));
    }
    
    @ZenMethod
    public static void addShapedMirrored(String stage, IItemStack output, IIngredient[][] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action) {
        String name = calculateName(output, ingredients);
        ShapedRecipe recipe = new ShapedRecipe(name, output, ingredients, function, action, true);
        IRecipe irecipe = RecipeConverter.convert(recipe, new ResourceLocation("crafttweaker", name));
        RecipeStages.LATE_ADDITIONS.add(new ActionAddRecipe(recipe, new RecipeStage(stage, irecipe, false, recipe.getWidth(), recipe.getHeight()).setRegistryName(new ResourceLocation("crafttweaker", name))));
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
        RecipeStages.LATE_ADDITIONS.add(new ActionAddRecipe(recipe, new RecipeStage(stage, irecipe, true).setRegistryName(new ResourceLocation("crafttweaker", name))));
    }
    
    @ZenMethod
    public static void setRecipeStage(String stage, IIngredient output) {
        if(actionSetOutputStages == null) {
            actionSetOutputStages = new ActionSetOutputStages();
            RecipeStages.LATE_REMOVALS.add(actionSetOutputStages);
        }
        actionSetOutputStages.addOutput(stage, output);
    }
    
    @ZenMethod
    public static void setRecipeStage(String stage, String recipeName) {
        if(actionSetNameStages == null) {
            actionSetNameStages = new ActionSetNameStages();
            RecipeStages.LATE_REMOVALS.add(actionSetNameStages);
        }
        actionSetNameStages.addName(stage, recipeName);
    }
    
    @ZenMethod
    public static void setRecipeStageByRegex(String stage, String regexString) {
        if(actionSetRegexStages == null) {
            actionSetRegexStages = new ActionSetRegexStages();
            RecipeStages.LATE_REMOVALS.add(actionSetRegexStages);
        }
        actionSetRegexStages.addRegex(stage, regexString);
    }
    
    @ZenMethod
    public static void setRecipeStageByMod(String stage, String modid) {
        if(actionSetModidStages == null) {
            actionSetModidStages = new ActionSetModidStages();
            RecipeStages.LATE_REMOVALS.add(actionSetModidStages);
        }
        actionSetModidStages.addModid(stage, modid);
    }
    
    private static class ActionSetPrinting implements IAction {
        
        private final boolean print;
        
        public ActionSetPrinting(boolean print) {
            this.print = print;
        }
        
        @Override
        public void apply() {
            Recipes.printContainers = print;
        }
        
        @Override
        public String describe() {
            return "Setting print containers to: " + print;
        }
    }
    
    private static class ActionSetCrafter implements IAction {
        
        private final String container;
        private final String[] stage;
        
        public ActionSetCrafter(String container, String[] stage) {
            this.container = container;
            this.stage = stage;
        }
        
        @Override
        public void apply() {
            crafterStages.put(container, stage);
        }
        
        @Override
        public String describe() {
            return "Setting stage of: " + container + " to: " + String.join(", ", stage);
        }
    }
    
    private static class ActionSetPack implements IAction {
        
        private final String pack;
        private final String[] stage;
        
        public ActionSetPack(String container, String[] stage) {
            this.pack = container;
            this.stage = stage;
        }
        
        @Override
        public void apply() {
            packageStages.put(pack, stage);
        }
        
        @Override
        public String describe() {
            return "Setting stage of: " + pack + " to: " + String.join(", ", stage);
        }
        
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
            CraftTweakerAPI.logInfo("Adding: " + output + " to stage: \"" + stage + "\"");
        }
        
        @Override
        public void apply() {
            for(IRecipe recipe : ForgeRegistries.RECIPES.getValues()) {
                IItemStack stack = CraftTweakerMC.getIItemStack(recipe.getRecipeOutput());
                if(stack != null) {
                    for(Map.Entry<String, List<IItemStack>> entry : outputs.entrySet()) {
                        for(IItemStack output : entry.getValue()) {
                            if(output.matches(stack)) {
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
            return "Setting the stages for recipes based on their output stack, " + outputs.size() + " stages";
        }
    }
    
    private static class ActionSetRegexStages implements IAction {
        
        private final Map<String, List<String>> outputs = new HashMap<>();
        
        public void addRegex(String stage, String regex) {
            List<String> outputsForStage = this.outputs.computeIfAbsent(stage, k -> new ArrayList<>());
            outputsForStage.add(regex);
        }
        
        @Override
        public void apply() {
            
            for(Map.Entry<ResourceLocation, IRecipe> ent : ForgeRegistries.RECIPES.getEntries()) {
                for(Map.Entry<String, List<String>> entry : outputs.entrySet()) {
                    for(String s : entry.getValue()) {
                        Pattern pattern = Pattern.compile(s);
                        Matcher m = pattern.matcher(ent.getKey().toString());
                        if(m.matches()) {
                            IRecipe recipe = ent.getValue();
                            new ActionSetStage(Collections.singletonList(recipe), entry.getKey()).apply();
                        }
                    }
                    
                }
            }
            actionSetRegexStages = null;
        }
        
        @Override
        public String describe() {
            return "Setting the stages for recipes based on regex, " + outputs.size();
        }
    }
    
    private static class ActionSetNameStages implements IAction {
        
        private final Map<String, List<String>> outputs = new HashMap<>();
        
        public void addName(String stage, String name) {
            List<String> outputsForStage = this.outputs.computeIfAbsent(stage, k -> new ArrayList<>());
            outputsForStage.add(name);
        }
        
        @Override
        public void apply() {
            
            for(Map.Entry<ResourceLocation, IRecipe> ent : ForgeRegistries.RECIPES.getEntries()) {
                for(Map.Entry<String, List<String>> entry : outputs.entrySet()) {
                    for(String s : entry.getValue()) {
                        if(s.equalsIgnoreCase(ent.getKey().toString())) {
                            IRecipe recipe = ent.getValue();
                            new ActionSetStage(Collections.singletonList(recipe), entry.getKey()).apply();
                        }
                    }
                    
                }
            }
            actionSetNameStages = null;
        }
        
        @Override
        public String describe() {
            return "Setting the stages for recipes based on regex, " + outputs.size();
        }
    }
    
    private static class ActionSetModidStages implements IAction {
        
        private final Map<String, List<String>> outputs = new HashMap<>();
        
        public void addModid(String stage, String modid) {
            List<String> outputsForStage = this.outputs.computeIfAbsent(stage, k -> new ArrayList<>());
            outputsForStage.add(modid);
        }
        
        @Override
        public void apply() {
            
            for(Map.Entry<ResourceLocation, IRecipe> ent : ForgeRegistries.RECIPES.getEntries()) {
                for(Map.Entry<String, List<String>> entry : outputs.entrySet()) {
                    for(String s : entry.getValue()) {
                        if(s.equalsIgnoreCase(ent.getKey().getResourceDomain())) {
                            IRecipe recipe = ent.getValue();
                            new ActionSetStage(Collections.singletonList(recipe), entry.getKey()).apply();
                        }
                    }
                    
                }
            }
            actionSetNameStages = null;
        }
        
        @Override
        public String describe() {
            return "Setting the stages for recipes based on modid, " + outputs.size();
        }
    }
    
    private static void replaceRecipe(String stage, IRecipe iRecipe) {
        ResourceLocation registryName = iRecipe.getRegistryName();
        if(registryName == null)
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
        if(modContainer != null) {
            loader.setActiveModContainer(modContainer);
        }
        recipe.setRegistryName(registryName);
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
        private final ICraftingRecipe craftingRecipe;
        
        public ActionAddRecipe(ICraftingRecipe craftingRecipe, IRecipe recipe) {
            this.recipe = recipe;
            this.craftingRecipe = craftingRecipe;
        }
        
        @Override
        public void apply() {
            ForgeRegistries.RECIPES.register(recipe);
            recipes.add(recipe);
            if(craftingRecipe.hasTransformers()) {
                MCRecipeManager.transformerRecipes.add(craftingRecipe);
            }
        }
        
        @Override
        public String describe() {
            return "Adding recipe for " + recipe.getRecipeOutput().getDisplayName();
        }
        
    }
    
    
}
