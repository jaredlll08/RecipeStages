package com.blamejared.recipestages;

import com.blamejared.recipestages.recipes.IStagedRecipe;
import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.fml.util.thread.EffectiveSide;

import javax.annotation.Nullable;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class RecipeStagesUtil {
    
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object o) {
        
        return (T) o;
    }
    
    public static boolean hasStageForRecipe(Recipe<CraftingContainer> recipe, Player player) {
        
        if(recipe instanceof IStagedRecipe stagedRecipe) {
            return GameStageHelper.hasStage(player, stagedRecipe.getStage());
        }
        return true;
    }
    
    @Nullable
    public static <T> T callForSide(@Nullable Supplier<Callable<T>> client, @Nullable Supplier<Callable<T>> server) {
        
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
    
}
