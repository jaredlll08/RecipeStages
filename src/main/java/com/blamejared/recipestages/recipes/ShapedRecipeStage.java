package com.blamejared.recipestages.recipes;

import com.blamejared.recipestages.ClientStuff;
import com.blamejared.recipestages.RecipeStages;
import com.blamejared.recipestages.ServerStuff;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.fml.util.thread.EffectiveSide;

import javax.annotation.Nullable;
import java.util.StringJoiner;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class ShapedRecipeStage implements CraftingRecipe, IShapedRecipe<CraftingContainer>, IStagedRecipe {
    
    private final ResourceLocation id;
    private final String stage;
    private final CraftingRecipe recipe;
    
    private int width;
    private int height;
    
    public ShapedRecipeStage(ResourceLocation id, String stage, CraftingRecipe recipe) {
        
        this.id = id;
        this.stage = stage;
        this.recipe = recipe;
        this.width = ((IShapedRecipe<CraftingContainer>) recipe).getRecipeWidth();
        this.height = ((IShapedRecipe<CraftingContainer>) recipe).getRecipeHeight();
    }
    
    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
        
        return recipe.getRemainingItems(inv);
    }
    
    @Override
    public boolean matches(CraftingContainer inv, Level worldIn) {
        
        return recipe.matches(inv, worldIn);
    }
    
    @Override
    public ItemStack assemble(CraftingContainer inv) {
        
        if(isGoodForCrafting(inv)) {
            return recipe.assemble(inv);
        }
        return ItemStack.EMPTY;
    }
    
    @Override
    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        
        return recipe.canCraftInDimensions(p_194133_1_, p_194133_2_);
    }
    
    @Override
    public ItemStack getResultItem() {
        
        return recipe.getResultItem();
    }
    
    
    public boolean isGoodForCrafting(CraftingContainer inv) {
        
        // We do this check in ServerStuff later down the line,
        // which leads to a de-sync issue with the item showing on the client.
        if(inv.menu == null && ForgeHooks.getCraftingPlayer() == null) {
            return false;
        }
        if(RecipeStages.printContainer) {
            RecipeStages.CONTAINER_LOGGER.info("Tried to craft a recipe in container: '{}'", inv.menu.getClass()
                    .getName());
        }
        
        return callForSide(
                () -> () -> ClientStuff.handleClient(inv, stage),
                () -> () -> ServerStuff.handleServer(inv, stage));
    }
    
    @Nullable
    private <T> T callForSide(@Nullable Supplier<Callable<T>> client, @Nullable Supplier<Callable<T>> server) {
        
        try {
            
            if(EffectiveSide.get().isClient() && client != null) {
                
                return client.get().call();
            } else if(EffectiveSide.get().isServer() && server != null) {
                
                return server.get().call();
            }
        } catch(final Exception e) {
            
            throw new RuntimeException(e);
        }
        
        return null;
    }
    
    @Override
    public NonNullList<Ingredient> getIngredients() {
        
        return recipe.getIngredients();
    }
    
    @Override
    public ResourceLocation getId() {
        
        return id;
    }
    
    @Override
    public RecipeSerializer<?> getSerializer() {
        
        return RecipeStages.SHAPED_STAGE_SERIALIZER;
    }
    
    @Override
    public RecipeType<?> getType() {
        
        return recipe.getType();
    }
    
    @Override
    public CraftingRecipe getRecipe() {
        
        return recipe;
    }
    
    @Override
    public String getStage() {
        
        return stage;
    }
    
    @Override
    public String toString() {
        
        return new StringJoiner(", ", ShapedRecipeStage.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("stage='" + stage + "'")
                .add("recipe=" + recipe)
                .add("width=" + width)
                .add("height=" + height)
                .toString();
    }
    
    public int getWidth() {
        
        return width;
    }
    
    public int getHeight() {
        
        return height;
    }
    
    @Override
    public int getRecipeWidth() {
        
        return getWidth();
    }
    
    @Override
    public int getRecipeHeight() {
        
        return getHeight();
    }
    
}