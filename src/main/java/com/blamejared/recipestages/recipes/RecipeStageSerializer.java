package com.blamejared.recipestages.recipes;

import com.blamejared.recipestages.RecipeStagesUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class RecipeStageSerializer implements RecipeSerializer<RecipeStage> {
    
    @Override
    public RecipeStage fromJson(ResourceLocation recipeId, JsonObject json) {
        
        String stage = GsonHelper.getAsString(json, "stage");
        Recipe<?> recipe = RecipeManager.fromJson(recipeId, GsonHelper.getAsJsonObject(json, "recipe"));
        
        if(!(recipe instanceof CraftingRecipe craftingRecipe)) {
            throw new JsonSyntaxException("Staged Recipes only work with Crafting Table Recipes");
        }
        
        boolean shapeless = json.has("shapeless") ? GsonHelper.getAsBoolean(json, "shapeless") : (recipe instanceof ShapelessRecipe);
        return new RecipeStage(recipeId, stage, craftingRecipe, shapeless);
    }
    
    @Nullable
    @Override
    public RecipeStage fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        
        ResourceLocation innerRecipeId = buffer.readResourceLocation();
        ResourceLocation recipeSerializerId = buffer.readResourceLocation();
        RecipeSerializer<?> value = ForgeRegistries.RECIPE_SERIALIZERS.getValue(recipeSerializerId);
        Recipe<?> read = value.fromNetwork(innerRecipeId, buffer);
        
        String stage = buffer.readUtf();
        boolean shapeless = buffer.readBoolean();
        return new RecipeStage(recipeId, stage, (CraftingRecipe) read, shapeless);
    }
    
    @Override
    public void toNetwork(FriendlyByteBuf buffer, RecipeStage recipe) {
        
        Recipe<CraftingContainer> recipe1 = recipe.getRecipe();
        if(recipe1.getId() == null) {
            throw new IllegalArgumentException("Unable to serialize a recipe without an id: " + recipe1);
        }
        ResourceLocation serializerKey = Registry.RECIPE_SERIALIZER.getKey(recipe1.getSerializer());
        if(serializerKey == null) {
            
            throw new IllegalArgumentException("Unable to serialize a recipe serializer without an id: " + recipe1.getSerializer());
        }
        buffer.writeResourceLocation(recipe1.getId());
        buffer.writeResourceLocation(serializerKey);
        recipe1.getSerializer().toNetwork(buffer, RecipeStagesUtil.cast(recipe1));
        buffer.writeUtf(recipe.getStage());
        buffer.writeBoolean(recipe.isShapeless());
    }
    
}
