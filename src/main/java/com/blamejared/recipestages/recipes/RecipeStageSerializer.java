package com.blamejared.recipestages.recipes;

import com.google.gson.*;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.*;
import net.minecraftforge.registries.*;

import javax.annotation.Nullable;

public class RecipeStageSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<RecipeStage> {
    
    public RecipeStageSerializer() {
    
    }
    
    @Override
    public RecipeStage fromJson(ResourceLocation recipeId, JsonObject json) {
        
        String stage = JSONUtils.getAsString(json, "stage");
        IRecipe<?> recipe = RecipeManager.fromJson(recipeId, JSONUtils.getAsJsonObject(json, "recipe"));
        
        if(recipe.getType() != IRecipeType.CRAFTING) {
            throw new JsonSyntaxException("Staged Recipes only work with Crafting Table Recipes");
        }
        
        boolean shapeless = json.has("shapeless") ? JSONUtils.getAsBoolean(json, "shapeless") : (recipe instanceof ShapelessRecipe);
        return new RecipeStage(recipeId, stage, (IRecipe<CraftingInventory>) recipe, shapeless);
    }
    
    @Nullable
    @Override
    public RecipeStage fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
        
        ResourceLocation innerRecipeId = buffer.readResourceLocation();
        ResourceLocation recipeSerializerId = buffer.readResourceLocation();
        IRecipeSerializer<?> value = ForgeRegistries.RECIPE_SERIALIZERS.getValue(recipeSerializerId);
        IRecipe<?> read = value.fromNetwork(innerRecipeId, buffer);
        
        String stage = buffer.readUtf();
        boolean shapeless = buffer.readBoolean();
        return new RecipeStage(recipeId, stage, (IRecipe<CraftingInventory>) read, shapeless);
    }
    
    @Override
    public void toNetwork(PacketBuffer buffer, RecipeStage recipe) {
        
        IRecipe recipe1 = recipe.getRecipe();
        buffer.writeResourceLocation(recipe1.getId());
        buffer.writeResourceLocation(recipe1.getSerializer().getRegistryName());
        recipe1.getSerializer().toNetwork(buffer, recipe1);
        buffer.writeUtf(recipe.getStage());
        buffer.writeBoolean(recipe.isShapeless());
    }
    
}
