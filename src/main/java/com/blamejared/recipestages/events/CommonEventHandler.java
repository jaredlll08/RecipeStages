package com.blamejared.recipestages.events;

import com.blamejared.recipestages.RecipeStages;
import com.blamejared.recipestages.compat.JEIPlugin;
import com.blamejared.recipestages.handlers.Recipes;
import com.blamejared.recipestages.recipes.RecipeStage;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.recipe.*;
import mezz.jei.ingredients.IngredientRegistry;
import net.darkhax.gamestages.event.GameStageEvent;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collections;

import static com.blamejared.recipestages.compat.JEIPlugin.ingredientRegistry;

public class CommonEventHandler {
    
    //    public CommonEventHandler() {
    //        MinecraftForge.EVENT_BUS.register(this);
    //    }
    //
    //    @SubscribeEvent
    //    public void gameStageChange(GameStageEvent.Add event) {
    //        if(FMLCommonHandler.instance().getEffectiveSide().isClient()) {
    //            String stage = event.getStageName();
    //            String guid = VanillaRecipeCategoryUid.CRAFTING;
    //            IRecipeRegistry reg = JEIPlugin.recipeRegistry;
    //            Recipes.recipes.stream().filter(rec -> rec instanceof RecipeStage && ((RecipeStage) rec).getTier().equalsIgnoreCase(stage)).forEach(rec -> {
    //                IRecipeWrapper wrapper = reg.getRecipeWrapper(rec, guid);
    //                reg.unhideRecipe(wrapper);
    //            });
    //            System.out.println(">>>");
    //            JEIPlugin.ingredientRegistry.addIngredientsAtRuntime(ItemStack.class, Collections.singletonList(new ItemStack(Items.WRITTEN_BOOK)));
    //        }
    //    }
    //
    //    @SubscribeEvent
    //    public void gameStageChange(GameStageEvent.Remove event) {
    //        if(FMLCommonHandler.instance().getSide().isClient()) {
    //            String stage = event.getStageName();
    //            String guid = VanillaRecipeCategoryUid.CRAFTING;
    //            IRecipeRegistry reg = JEIPlugin.recipeRegistry;
    //            Recipes.recipes.stream().filter(rec -> rec instanceof RecipeStage && ((RecipeStage) rec).getTier().equalsIgnoreCase(stage)).forEach(rec -> {
    //                IRecipeWrapper wrapper = reg.getRecipeWrapper(rec, guid);
    //                reg.hideRecipe(wrapper);
    //            });
    //        }
    //    }
    //
}
