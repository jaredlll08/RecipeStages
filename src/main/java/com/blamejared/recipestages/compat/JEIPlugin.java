package com.blamejared.recipestages.compat;


import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.recipestages.recipes.RecipeStage;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.darkhax.gamestages.data.IStageData;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    
    public static IJeiRuntime runTime;
    
    @Override
    public ResourceLocation getPluginUid() {
        
        return new ResourceLocation("recipestages:main");
    }
    
    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
        
        registration.getCraftingCategory().addCategoryExtension(RecipeStage.class, StagedRecipeExtension::new);
    }
    
    
    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        
        runTime = jeiRuntime;
        
    }
    
    public static void sync(IStageData data) {
        
        Minecraft.getInstance().level.getRecipeManager()
                .getAllRecipesFor(RecipeType.CRAFTING)
                .stream()
                .filter(iCraftingRecipe -> iCraftingRecipe instanceof RecipeStage)
                .map(iCraftingRecipe -> (RecipeStage) iCraftingRecipe)
                .collect(() -> new HashMap<Boolean, List<RecipeStage>>(), (hashMap, recipeStage) -> hashMap.computeIfAbsent(data.hasStage(recipeStage.getStage()), i -> new ArrayList<>())
                        .add(recipeStage), HashMap::putAll)
                .forEach((hasStage, recipes) -> {
                    if(hasStage) {
                        JEIPlugin.runTime.getRecipeManager()
                                .unhideRecipes(RecipeTypes.CRAFTING, GenericUtil.uncheck(recipes));
                    } else {
                        JEIPlugin.runTime.getRecipeManager()
                                .hideRecipes(RecipeTypes.CRAFTING, GenericUtil.uncheck(recipes));
                    }
                });
    }
    
}