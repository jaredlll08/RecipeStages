package com.blamejared.recipestages.recipes;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.recipestages.RecipeStages;
import net.darkhax.bookshelf.util.SidedExecutor;
import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.HashSet;
import java.util.Set;

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
        return SidedExecutor.<Boolean> callForSide(() -> () -> {
            PlayerEntity player = Minecraft.getInstance().player;
            if(player == null || player instanceof FakePlayer) {
                return true;
            }
            return GameStageHelper.getPlayerData(player).hasStage(stage);
        }, () -> () -> {
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            if(server != null) {
                PlayerList manager = server.getPlayerList();
                Container container = inv.eventHandler;
                if(container == null) {
                    return false;
                }
                ServerPlayerEntity foundPlayer = null;
                for(ServerPlayerEntity serverPlayerEntity : manager.getPlayers()) {
                    ServerPlayerEntity entityPlayerMP = (ServerPlayerEntity) serverPlayerEntity;
                    if(entityPlayerMP.openContainer == container && container.canInteractWith(entityPlayerMP) && container.getCanCraft(entityPlayerMP)) {
                        if(foundPlayer != null) {
                            return false;
                        }
                        
                        foundPlayer = entityPlayerMP;
                    }
                }
                if(foundPlayer != null) {
                    return GameStageHelper.getPlayerData(foundPlayer).hasStage(stage);
                }
                
                Set<String> crafterStages = RecipeStages.containerStages.getOrDefault(inv.eventHandler.getClass().getName(), new HashSet<>());
                if(crafterStages.isEmpty()) {
                    return false;
                }
                if(crafterStages.contains(stage)) {
                    return true;
                }
                
                Set<String> packageStages = RecipeStages.packageStages.keySet().stream().filter(s -> inv.eventHandler.getClass().getName().startsWith(s)).map(RecipeStages.packageStages::get).reduce((strings, strings2) -> {
                    strings.addAll(strings2);
                    return strings;
                }).orElse(new HashSet<>());
                if(packageStages.isEmpty()) {
                    return false;
                }
                return packageStages.contains(stage);
            }
            
            return false;
        });
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
        return null;
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