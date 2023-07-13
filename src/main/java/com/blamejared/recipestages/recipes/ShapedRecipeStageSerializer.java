package com.blamejared.recipestages.recipes;

import com.blamejared.recipestages.RecipeStagesUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class ShapedRecipeStageSerializer implements RecipeSerializer<ShapedRecipeStage> {
    
    public ShapedRecipeStageSerializer() {
        
    }
    
    @Override
    public ShapedRecipeStage fromJson(ResourceLocation recipeId, JsonObject json) {
        
        String stage = GsonHelper.getAsString(json, "stage");
        Recipe<?> recipe = RecipeManager.fromJson(recipeId, GsonHelper.getAsJsonObject(json, "recipe"));
        
        if(!(recipe instanceof CraftingRecipe craftingRecipe)) {
            throw new JsonSyntaxException("Staged Recipes only work with Crafting Table Recipes");
        }
        
        return new ShapedRecipeStage(recipeId, stage, craftingRecipe);
    }
    
    @Nullable
    @Override
    public ShapedRecipeStage fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        
        ResourceLocation innerRecipeId = buffer.readResourceLocation();
        ResourceLocation recipeSerializerId = buffer.readResourceLocation();
        RecipeSerializer<?> value = ForgeRegistries.RECIPE_SERIALIZERS.getValue(recipeSerializerId);
        Recipe<?> read = value.fromNetwork(innerRecipeId, buffer);
        
        String stage = buffer.readUtf();
        return new ShapedRecipeStage(recipeId, stage, (CraftingRecipe) read);
    }
    
    @Override
    public void toNetwork(FriendlyByteBuf buffer, ShapedRecipeStage recipe) {
        
        Recipe<CraftingContainer> recipe1 = recipe.getRecipe();
        if(recipe1.getId() == null) {
            throw new IllegalArgumentException("Unable to serialize a recipe without an id: " + recipe1);
        }
        ResourceLocation serializerKey = BuiltInRegistries.RECIPE_SERIALIZER.getKey(recipe1.getSerializer());
        if(serializerKey == null) {
            
            throw new IllegalArgumentException("Unable to serialize a recipe serializer without an id: " + recipe1.getSerializer());
        }
        buffer.writeResourceLocation(recipe1.getId());
        buffer.writeResourceLocation(serializerKey);
        recipe1.getSerializer().toNetwork(buffer, RecipeStagesUtil.cast(recipe1));
        buffer.writeUtf(recipe.getStage());
    }
    
}
