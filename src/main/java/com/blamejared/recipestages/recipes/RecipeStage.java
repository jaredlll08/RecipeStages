package com.blamejared.recipestages.recipes;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.recipestages.*;
import net.darkhax.bookshelf.util.SidedExecutor;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.IShapedRecipe;

public class RecipeStage implements ICraftingRecipe {
    
    private final ResourceLocation id;
    private String stage;
    private IRecipe<CraftingInventory> recipe;
    
    private boolean shapeless;
    
    private int width, height;
    
    public RecipeStage(ResourceLocation id, String stage, IRecipe<CraftingInventory> recipe, boolean shapeless) {
        this.id = id;
        this.stage = stage;
        this.recipe = recipe;
        this.shapeless = shapeless;
        if(recipe instanceof IShapedRecipe) {
            this.width = ((IShapedRecipe<CraftingInventory>) recipe).getRecipeWidth();
            this.height = ((IShapedRecipe<CraftingInventory>) recipe).getRecipeHeight();
        }
    }
    
    public RecipeStage(ResourceLocation id, String stage, IRecipe<CraftingInventory> recipe, boolean shapeless, int width, int height) {
        this.id = id;
        this.stage = stage;
        this.recipe = recipe;
        this.shapeless = shapeless;
        this.width = width;
        this.height = height;
    }
    
    
    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv) {
        return recipe.getRemainingItems(inv);
    }
    
    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        return recipe.matches(inv, worldIn);
    }
    
    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        if(isGoodForCrafting(inv)) {
            return recipe.getCraftingResult(inv);
        }
        return ItemStack.EMPTY;
    }
    
    @Override
    public boolean canFit(int width, int height) {
        return recipe.canFit(width, height);
    }
    
    public boolean isGoodForCrafting(CraftingInventory inv) {
        if(RecipeStages.printContainers) {
            CraftTweakerAPI.logInfo("Tried to craft a recipe in container: \"" + inv.eventHandler.getClass().getName() + "\"");
        }
        return SidedExecutor.<Boolean> callForSide(() -> () -> ClientStuff.handleClient(stage), () -> () -> ServerStuff.handleServer(inv, stage));
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        return recipe.getRecipeOutput();
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
    public IRecipeSerializer<?> getSerializer() {
        return RecipeStages.STAGE_SERIALIZER;
    }
    
    @Override
    public IRecipeType<?> getType() {
        return recipe.getType();
    }
    
    public IRecipe<CraftingInventory> getRecipe() {
        return recipe;
    }
    
    public String getStage() {
        return stage;
    }
    
    @Override
    public String toString() {
        return "RecipeStage{" + "stage='" + stage + '\'' + ", recipe=" + recipe.getRecipeOutput() + ":" + recipe.getIngredients() + '}';
    }
    
    public boolean isShapeless() {
        return shapeless;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
}