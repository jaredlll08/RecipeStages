//package com.blamejared.recipestages.compat;
//
//import com.blamejared.recipestages.config.Configurations;
//import com.blamejared.recipestages.recipes.RecipeStage;
//import mezz.jei.api.IJeiHelpers;
//import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
//import mezz.jei.plugins.vanilla.crafting.ShapelessRecipeWrapper;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.resources.I18n;
//import net.minecraft.util.ResourceLocation;
//
//import java.util.Collections;
//import java.util.List;
//
//
//public class StagedRecipeWrapperShaped extends ShapelessRecipeWrapper<RecipeStage> implements IShapedCraftingRecipeWrapper {
//
//    private RecipeStage recipe;
//
//    public StagedRecipeWrapperShaped(IJeiHelpers jeiHelpers, RecipeStage recipe) {
//        super(jeiHelpers, recipe);
//        this.recipe = recipe;
//    }
//
////    @Override
////    public void getIngredients(IIngredients ingredients) {
////        ItemStack recipeOutput = recipe.getRecipeOutput();
////        IStackHelper stackHelper = jeiHelpers.getStackHelper();
////
////        try {
////            List<List<ItemStack>> inputLists = stackHelper.expandRecipeItemStackInputs(recipe.getIngredients());
////            ingredients.setInputLists(ItemStack.class, inputLists);
////            ingredients.setOutput(ItemStack.class, recipeOutput);
////        } catch(RuntimeException e) {
////            String info = ErrorUtil.getInfoFromBrokenCraftingRecipe(recipe, recipe.getIngredients(), recipeOutput);
////            throw new BrokenCraftingRecipeException(info, e);
////        }
////    }
//
//    @Override
//    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
//        super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
//        ResourceLocation registryName = recipe.getRegistryName();
//        if(Configurations.showStageName)
//            minecraft.fontRenderer.drawString(I18n.format("gui.rs.tip.stage", recipe.getTier()), 0, -11, 0);
//    }
//
//    public RecipeStage getRecipe() {
//        return recipe;
//    }
//
//    @Override
//    public List<String> getTooltipStrings(int mouseX, int mouseY) {
//        //        ResourceLocation registryName = recipe.getRegistryName();
//        //        if(registryName != null) {
//        //            return recipeInfoIcon.getTooltipStrings(registryName, mouseX, mouseY);
//        //        }
//        return Collections.emptyList();
//    }
//
//    @Override
//    public int getWidth() {
//        return recipe.getWidth();
//    }
//
//    @Override
//    public int getHeight() {
//        return recipe.getHeight();
//    }
//}
