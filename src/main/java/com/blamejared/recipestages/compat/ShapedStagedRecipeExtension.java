package com.blamejared.recipestages.compat;

import com.blamejared.recipestages.RecipeStages;
import com.blamejared.recipestages.recipes.ShapedRecipeStage;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import mezz.jei.library.util.RecipeUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
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
        
        craftingGridHelper.createAndSetOutputs(builder, item, List.of(RecipeUtil.getResultItem(recipe)));
        int width = recipe.getWidth();
        int height = recipe.getHeight();
        craftingGridHelper.createAndSetInputs(builder, item, recipe.getIngredients()
                .stream()
                .map(Ingredient::getItems)
                .map(Arrays::asList)
                .toList(), width, height);
    }
    
    @Override
    public void drawInfo(int recipeWidth, int recipeHeight, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        
        if(RecipeStages.showJEILabel) {
            guiGraphics.drawString(Minecraft.getInstance().font, I18n.get("gui.rs.tip.stage", recipe.getStage()), 0, -11, 0x00000000, false);
        }
    }
    
}
