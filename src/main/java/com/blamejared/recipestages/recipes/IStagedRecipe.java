package com.blamejared.recipestages.recipes;

import com.blamejared.recipestages.ClientStuff;
import com.blamejared.recipestages.RecipeStages;
import com.blamejared.recipestages.RecipeStagesUtil;
import com.blamejared.recipestages.ServerStuff;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeHooks;

public interface IStagedRecipe extends CraftingRecipe {
    
    String getStage();
    
    ResourceLocation getId();
    
    CraftingRecipe getRecipe();
    
    @Override
    default ItemStack assemble(CraftingContainer inv) {
        
        if(isGoodForCrafting(inv)) {
            return forceAssemble(inv);
        }
        return ItemStack.EMPTY;
    }
    
    default ItemStack forceAssemble(CraftingContainer inv) {
        
        return getRecipe().assemble(inv);
    }
    
    @Override
    default NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
        
        return getRecipe().getRemainingItems(inv);
    }
    
    @Override
    default boolean matches(CraftingContainer inv, Level worldIn) {
        
        return getRecipe().matches(inv, worldIn);
    }
    
    @Override
    default NonNullList<Ingredient> getIngredients() {
        
        return getRecipe().getIngredients();
    }
    
    @Override
    default boolean isIncomplete() {
        
        return getRecipe().isIncomplete();
    }
    
    @Override
    default boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        
        return getRecipe().canCraftInDimensions(p_194133_1_, p_194133_2_);
    }
    
    @Override
    default ItemStack getResultItem() {
        
        return getRecipe().getResultItem();
    }
    
    default boolean isGoodForCrafting(CraftingContainer inv) {
        
        // We do this check in ServerStuff later down the line,
        // which leads to a de-sync issue with the item showing on the client.
        //noinspection ConstantValue
        if(inv.menu == null && ForgeHooks.getCraftingPlayer() == null) {
            return false;
        }
        if(RecipeStages.printContainer) {
            RecipeStages.CONTAINER_LOGGER.info("Tried to craft a recipe in container: '{}'", inv.menu.getClass()
                    .getName());
        }
        
        return Boolean.TRUE.equals(RecipeStagesUtil.callForSide(
                () -> () -> ClientStuff.handleClient(inv, getStage()),
                () -> () -> ServerStuff.handleServer(inv, getStage())));
    }
    
    @Override
    default RecipeType<?> getType() {
        
        return getRecipe().getType();
    }
    
}
