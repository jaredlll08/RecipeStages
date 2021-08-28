package com.blamejared.recipestages.recipes;

import com.blamejared.recipestages.ClientStuff;
import com.blamejared.recipestages.RecipeStages;
import com.blamejared.recipestages.ServerStuff;
import com.blamejared.recipestages.compat.RecipeStagesLogger;
import net.darkhax.bookshelf.util.SidedExecutor;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.crafting.IShapedRecipe;

public class RecipeStage implements ICraftingRecipe {
    
    private final ResourceLocation id;
    private final String stage;
    private final IRecipe<CraftingInventory> recipe;
    
    private final boolean shapeless;
    
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
    public ItemStack assemble(CraftingInventory inv) {
        
        if(isGoodForCrafting(inv)) {
            System.out.println("Good on side");
            return recipe.assemble(inv);
        }
        System.out.println("not good on side");
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
    
    
    public boolean isGoodForCrafting(CraftingInventory inv) {
        
        // We do this check in ServerStuff later down the line,
        // which leads to a de-sync issue with the item showing on the client.
        if(inv.menu == null && ForgeHooks.getCraftingPlayer() == null) {
//            RecipeStagesLogger.instance.error("Cannot craft staged recipes in inventory: `%s` as we have no access to a player or a container! Please report this to RecipeStages and the offending mod!", inv);
            return false;
        }
        if(RecipeStages.printContainer) {
            RecipeStagesLogger.instance.info("Tried to craft a recipe in container: `%s`", inv.menu.getClass()
                    .getName());
        }
        
        return SidedExecutor.<Boolean> callForSide(
                () -> () -> ClientStuff.handleClient(inv,stage),
                () -> () -> ServerStuff.handleServer(inv, stage));
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
        
        return "RecipeStage{" + "stage='" + stage + '\'' +
                ", recipe=" + recipe.getResultItem() + ":" + recipe.getIngredients() +
                '}';
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