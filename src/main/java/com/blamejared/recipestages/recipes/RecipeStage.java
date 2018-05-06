package com.blamejared.recipestages.recipes;

import com.blamejared.recipestages.RecipeStages;
import com.blamejared.recipestages.handlers.Recipes;
import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.*;

public class RecipeStage extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    
    private String tier;
    private IRecipe recipe;
    
    private boolean shapeless;
    
    private int width, height;
    
    public RecipeStage(String tier, IRecipe recipe, boolean shapeless) {
        this.tier = tier;
        this.recipe = recipe;
        this.shapeless = shapeless;
    }
    
    public RecipeStage(String tier, IRecipe recipe, boolean shapeless, int width, int height) {
        this.tier = tier;
        this.recipe = recipe;
        this.shapeless = shapeless;
        this.width = width;
        this.height = height;
    }
    
    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        return recipe.matches(inv, worldIn);
    }
    
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        if(isGoodForCrafting(inv)) {
            return recipe.getCraftingResult(inv);
        }
        return ItemStack.EMPTY;
    }
    
    @Override
    public boolean canFit(int width, int height) {
        return recipe.canFit(width, height);
    }
    
    public boolean isGoodForCrafting(InventoryCrafting inv) {
        if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            EntityPlayer player = RecipeStages.proxy.getClientPlayer();
            if(player == null || player instanceof FakePlayer) {
                return true;
            }
            return GameStageHelper.getPlayerData(player).hasStage(tier);
        } else {
            MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
            if(server != null) {
                PlayerList manager = server.getPlayerList();
                if(manager != null) {
                    Container container = inv.eventHandler;
                    if(container == null) {
                        return false;
                    }
                    EntityPlayerMP foundPlayer = null;
                    Iterator var6 = manager.getPlayers().iterator();
                    while(var6.hasNext()) {
                        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) var6.next();
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
            }
            if(Recipes.printContainers)
                System.out.println("Current container: " + inv.eventHandler.getClass().getName());
            if(Recipes.crafterStages.getOrDefault(inv.eventHandler.getClass().getName(), new String[0]).length > 0) {
                for(String s : Recipes.crafterStages.get(inv.eventHandler.getClass().getName())) {
                    if(tier.equalsIgnoreCase(s)) {
                        return true;
                    }
                }
            }
            for(Map.Entry<String, String[]> entry : Recipes.packageStages.entrySet()) {
                String pack = entry.getKey().toLowerCase();
                String[] stages = entry.getValue();
                if(inv.eventHandler.getClass().getName().toLowerCase().startsWith(pack)){
                    for(String s : stages) {
                        if(tier.equalsIgnoreCase(s)) {
                            return true;
                        }
                    }
                }
                
            }
            
            return false;
        }
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        return recipe.getRecipeOutput();
    }
    
    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        return recipe.getRemainingItems(inv);
    }
    
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipe.getIngredients();
    }
    
    public IRecipe getRecipe() {
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