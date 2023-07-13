package com.blamejared.recipestages.recipes;

import com.blamejared.recipestages.ClientStuff;
import com.blamejared.recipestages.RecipeStages;
import com.blamejared.recipestages.RecipeStagesUtil;
import com.blamejared.recipestages.ServerStuff;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
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
    default ItemStack assemble(CraftingContainer inv, RegistryAccess registryAccess) {
        
        if(isGoodForCrafting(inv)) {
            return forceAssemble(inv, registryAccess);
        }
        return ItemStack.EMPTY;
    }
    
    default ItemStack forceAssemble(CraftingContainer inv, RegistryAccess registryAccess) {
        
        return getRecipe().assemble(inv, registryAccess);
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
    default ItemStack getResultItem(RegistryAccess registryAccess) {
        
        return getRecipe().getResultItem(registryAccess);
    }
    
    default boolean isGoodForCrafting(CraftingContainer inv) {
        
        // We do this check in ServerStuff later down the line,
        // which leads to a de-sync issue with the item showing on the client.
        //noinspection ConstantValue
        if(inv instanceof TransientCraftingContainer transientInv) {
            
            if(transientInv.menu == null && ForgeHooks.getCraftingPlayer() == null) {
                return false;
            }
            if(RecipeStages.printContainer) {
                RecipeStages.CONTAINER_LOGGER.info("Tried to craft a recipe in container: '{}'", transientInv.menu.getClass()
                        .getName());
            }
            
            return Boolean.TRUE.equals(RecipeStagesUtil.callForSide(
                    () -> () -> ClientStuff.handleClient(transientInv, getStage()),
                    () -> () -> ServerStuff.handleServer(transientInv, getStage())));
        }
        throw new UnsupportedOperationException("Tried to craft a staged recipe in a non TransientCraftingContainer('%s'). Please report this to RecipeStages!".formatted(inv.getClass()));
    }
    
    @Override
    default RecipeType<?> getType() {
        
        return getRecipe().getType();
    }
    
    @Override
    default CraftingBookCategory category() {
        
        return getRecipe().category();
    }
    
}
