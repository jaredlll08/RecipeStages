package com.blamejared.recipestages.recipes;

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
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class RecipeStage implements ICraftingRecipe {
    
    private final ResourceLocation id;
    private String tier;
    private IRecipe<CraftingInventory> recipe;
    
    private boolean shapeless;
    
    private int width, height;
    
    public RecipeStage(ResourceLocation id, String tier, IRecipe<CraftingInventory> recipe, boolean shapeless) {
        this.id = id;
        this.tier = tier;
        this.recipe = recipe;
        this.shapeless = shapeless;
    }
    
    public RecipeStage(ResourceLocation id, String tier, IRecipe<CraftingInventory> recipe, boolean shapeless, int width, int height) {
        this.id = id;
        this.tier = tier;
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
        return SidedExecutor.<Boolean> callForSide(() -> () -> {
            PlayerEntity player = Minecraft.getInstance().player;
            if(player == null || player instanceof FakePlayer) {
                return true;
            }
            return GameStageHelper.getPlayerData(player).hasStage(tier);
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
                    return GameStageHelper.getPlayerData(foundPlayer).hasStage(tier);
                }
            }
            //            if(Recipes.printContainers)
            //                System.out.println("Current container: " + inv.eventHandler.getClass().getName());
            //            if(Recipes.crafterStages.getOrDefault(inv.eventHandler.getClass().getName(), new String[0]).length > 0) {
            //                for(String s : Recipes.crafterStages.get(inv.eventHandler.getClass().getName())) {
            //                    if(tier.equalsIgnoreCase(s)) {
            //                        return true;
            //                    }
            //                }
            //            }
            //            for(Map.Entry<String, String[]> entry : Recipes.packageStages.entrySet()) {
            //                String pack = entry.getKey().toLowerCase();
            //                String[] stages = entry.getValue();
            //                if(inv.eventHandler.getClass().getName().toLowerCase().startsWith(pack)) {
            //                    for(String s : stages) {
            //                        if(tier.equalsIgnoreCase(s)) {
            //                            return true;
            //                        }
            //                    }
            //                }
            //
            //            }
    
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
    
    public String getTier() {
        return tier;
    }
    
    @Override
    public String toString() {
        return "RecipeStage{" + "tier='" + tier + '\'' + ", recipe=" + recipe.getRecipeOutput() + ":" + recipe.getIngredients() + '}';
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