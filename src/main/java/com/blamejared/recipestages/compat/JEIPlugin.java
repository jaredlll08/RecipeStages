package com.blamejared.recipestages.compat;


import com.blamejared.crafttweaker.impl.managers.CTCraftingTableManager;
import com.blamejared.recipestages.recipes.RecipeStage;
import mezz.jei.api.*;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.darkhax.gamestages.data.IStageData;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;

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
        CTCraftingTableManager.recipeManager.getAllRecipesFor(IRecipeType.CRAFTING).stream().filter(iCraftingRecipe -> iCraftingRecipe instanceof RecipeStage).map(iCraftingRecipe -> (RecipeStage) iCraftingRecipe).forEach(recipeStage -> {
            if(data.hasStage(recipeStage.getStage())) {
                JEIPlugin.runTime.getRecipeManager().unhideRecipe(recipeStage, VanillaRecipeCategoryUid.CRAFTING);
            } else {
                JEIPlugin.runTime.getRecipeManager().hideRecipe(recipeStage, VanillaRecipeCategoryUid.CRAFTING);
            }
        });
    }
}