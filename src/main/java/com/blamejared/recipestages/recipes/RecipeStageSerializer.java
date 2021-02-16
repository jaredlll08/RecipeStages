package com.blamejared.recipestages.recipes;

import com.google.gson.JsonObject;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.*;

public class RecipeStageSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<RecipeStage> {
    
    public RecipeStageSerializer() {
    }
    
    public RecipeStage read(ResourceLocation recipeId, JsonObject json) {
        String stage = JSONUtils.getString(json, "stage");
        IRecipe<?> recipe = RecipeManager.deserializeRecipe(recipeId, JSONUtils.getJsonObject(json, "recipe"));
        boolean shapeless = json.has("shapeless") ? JSONUtils.getBoolean(json, "shapeless") : (recipe instanceof ShapelessRecipe);
        return new RecipeStage(recipeId, stage, (IRecipe<CraftingInventory>) recipe, shapeless);
    }
    
    public RecipeStage read(ResourceLocation recipeId, PacketBuffer buffer) {
        ResourceLocation innerRecipeId = buffer.readResourceLocation();
        ResourceLocation recipeSerializerId = buffer.readResourceLocation();
        IRecipeSerializer<?> value = ForgeRegistries.RECIPE_SERIALIZERS.getValue(recipeSerializerId);
        IRecipe<?> read = value.read(innerRecipeId, buffer);
        
        String stage = buffer.readString();
        boolean shapeless = buffer.readBoolean();
        return new RecipeStage(recipeId, stage, (IRecipe<CraftingInventory>) read, shapeless);
    }
    
    public void write(PacketBuffer buffer, RecipeStage recipe) {
        IRecipe recipe1 = recipe.getRecipe();
        buffer.writeResourceLocation(recipe1.getId());
        buffer.writeResourceLocation(recipe1.getSerializer().getRegistryName());
        recipe1.getSerializer().write(buffer, recipe1);
        buffer.writeString(recipe.getStage());
        buffer.writeBoolean(recipe.isShapeless());
    }
}
