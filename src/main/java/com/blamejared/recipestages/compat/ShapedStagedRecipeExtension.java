package com.blamejared.recipestages.compat;

import com.blamejared.recipestages.RecipeStages;
import com.blamejared.recipestages.recipes.ShapedRecipeStage;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Arrays;
import java.util.List;

public class ShapedStagedRecipeExtension implements ICraftingCategoryExtension {
    
    private final ShapedRecipeStage recipe;
    
    public ShapedStagedRecipeExtension(ShapedRecipeStage recipeStage) {
        
        this.recipe = recipeStage;
    }
    
    
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ICraftingGridHelper craftingGridHelper, IFocusGroup focuses) {
        
        IIngredientType<ItemStack> item = VanillaTypes.ITEM_STACK;
        
        craftingGridHelper.setOutputs(builder, item, List.of(recipe.getResultItem()));
        int width = recipe.getWidth();
        int height = recipe.getHeight();
        craftingGridHelper.setInputs(builder, item, recipe.getIngredients()
                .stream()
                .map(Ingredient::getItems)
                .map(Arrays::asList)
                .toList(), width, height);
    }
    
    
    @Override
    public void drawInfo(int recipeWidth, int recipeHeight, PoseStack matrixStack, double mouseX, double mouseY) {
        
        if(RecipeStages.showJEILabel) {
            Minecraft.getInstance().font.draw(matrixStack, I18n.get("gui.rs.tip.stage", recipe.getStage()), 0, -11, 0x00000000);
        }
    }
    
}
