package com.blamejared.recipestages.compat;


import com.blamejared.recipestages.reference.Reference;
import com.sun.org.apache.regexp.internal.RE;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.*;
import mezz.jei.api.recipe.wrapper.*;
import mezz.jei.config.Constants;
import mezz.jei.plugins.vanilla.crafting.CraftingRecipeCategory;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

import java.util.List;

public class StagedRecipeCategory implements IRecipeCategory<IRecipeWrapper> {
    
    private static final int craftOutputSlot = 0;
    private static final int craftInputSlot1 = 1;
    
    public static final int width = 116;
    public static final int height = 54;
    
    private final IDrawable background;
    private final String localizedName;
    private final ICraftingGridHelper craftingGridHelper;
    
    public StagedRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation("minecraft", "textures/gui/container/crafting_table.png");
        background = guiHelper.createDrawable(location, 29, 16, width, height);
        localizedName = I18n.translateToLocal("gui.rs.category.craftingTable");
        craftingGridHelper = guiHelper.createCraftingGridHelper(craftInputSlot1, craftOutputSlot);
    }
    
    @Override
    public String getUid() {
        return "recipestages.recipes";
    }
    @Override
    public String getTitle() {
        return localizedName;
    }
    
    @Override
    public String getModName() {
        return Reference.MOD_NAME;
    }
    
    @Override
    public IDrawable getBackground() {
        return background;
    }
    
    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        
        guiItemStacks.init(craftOutputSlot, false, 94, 18);
        
        for(int y = 0; y < 3; ++y) {
            for(int x = 0; x < 3; ++x) {
                int index = craftInputSlot1 + x + (y * 3);
                guiItemStacks.init(index, true, x * 18, y * 18);
            }
        }
        
        if(recipeWrapper instanceof ICustomCraftingRecipeWrapper) {
            ICustomCraftingRecipeWrapper customWrapper = (ICustomCraftingRecipeWrapper) recipeWrapper;
            customWrapper.setRecipe(recipeLayout, ingredients);
            return;
        }
        
        List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
        List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);
        
        if(recipeWrapper instanceof IShapedCraftingRecipeWrapper) {
            IShapedCraftingRecipeWrapper wrapper = (IShapedCraftingRecipeWrapper) recipeWrapper;
            craftingGridHelper.setInputs(guiItemStacks, inputs, wrapper.getWidth(), wrapper.getHeight());
        } else {
            craftingGridHelper.setInputs(guiItemStacks, inputs);
            recipeLayout.setShapeless();
        }
        guiItemStacks.set(craftOutputSlot, outputs.get(0));
    }
    
}