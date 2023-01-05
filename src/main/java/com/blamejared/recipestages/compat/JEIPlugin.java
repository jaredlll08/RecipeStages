package com.blamejared.recipestages.compat;


import com.blamejared.recipestages.RecipeStagesUtil;
import com.blamejared.recipestages.recipes.IStagedRecipe;
import com.blamejared.recipestages.recipes.RecipeStage;
import com.blamejared.recipestages.recipes.ShapedRecipeStage;
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
        registration.getCraftingCategory().addCategoryExtension(ShapedRecipeStage.class, ShapedStagedRecipeExtension::new);
    }
    
    
    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        
        runTime = jeiRuntime;
        
    }
    
    public static void sync(IStageData data) {
        
        if(Minecraft.getInstance().level == null) {
            return;
        }
        Minecraft.getInstance().level.getRecipeManager()
                .getAllRecipesFor(RecipeType.CRAFTING)
                .stream()
                .filter(iCraftingRecipe -> iCraftingRecipe instanceof IStagedRecipe)
                .map(iCraftingRecipe -> (IStagedRecipe) iCraftingRecipe)
                .collect(() -> new HashMap<Boolean, List<IStagedRecipe>>(), (hashMap, recipeStage) -> hashMap.computeIfAbsent(data.hasStage(recipeStage.getStage()), i -> new ArrayList<>())
                        .add(recipeStage), HashMap::putAll)
                .forEach((hasStage, recipes) -> {
                    if(hasStage) {
                        JEIPlugin.runTime.getRecipeManager()
                                .unhideRecipes(RecipeTypes.CRAFTING, RecipeStagesUtil.cast(recipes));
                    } else {
                        JEIPlugin.runTime.getRecipeManager()
                                .hideRecipes(RecipeTypes.CRAFTING, RecipeStagesUtil.cast(recipes));
                    }
                });
    }
    
}