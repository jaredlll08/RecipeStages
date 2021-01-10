package com.blamejared.recipestages.compat;


import com.blamejared.recipestages.recipes.RecipeStage;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import mezz.jei.plugins.vanilla.crafting.CraftingRecipeCategory;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation("recipestages:main");
    }
    
    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
        registration.getCraftingCategory().addCategoryExtension(RecipeStage.class, StagedRecipeExtension::new);
        System.out.println("Registered");
        System.out.println(">>>");
    }
    
}