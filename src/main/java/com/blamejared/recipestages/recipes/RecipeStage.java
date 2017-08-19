package com.blamejared.recipestages.recipes;

import com.blamejared.recipestages.RecipeStages;
import net.darkhax.gamestages.capabilities.PlayerDataHandler;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Iterator;

public class RecipeStage extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    
    private String tier;
    private IRecipe recipe;
    
    public RecipeStage(String tier, IRecipe recipe) {
        this.tier = tier;
        this.recipe = recipe;
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
            return player != null && !PlayerDataHandler.getStageData(player).hasUnlockedStage(tier);
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
                        return PlayerDataHandler.getStageData(foundPlayer).hasUnlockedStage(tier);
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
 
}
