package com.blamejared.recipestages;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.recipestages.compat.RecipeStagesLogger;
import com.blamejared.recipestages.compat.RecipeStagesLoggerWithCT;
import com.blamejared.recipestages.compat.RecipeStagesLoggerWithoutCT;
import com.blamejared.recipestages.recipes.RecipeStageSerializer;
import net.minecraft.util.Util;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Mod("recipestages")
public class RecipeStages {
    
    public static final RecipeStageSerializer STAGE_SERIALIZER = Util.make(() -> {
        RecipeStageSerializer recipeStageSerializer = new RecipeStageSerializer();
        recipeStageSerializer.setRegistryName("recipestages", "stage");
        return recipeStageSerializer;
    });
    public static final Map<String, Set<String>> PACKAGE_STAGES = new HashMap<>();
    public static final Map<String, Set<String>> CONTAINER_STAGES = new HashMap<>();
    
    public static boolean printContainer = false;
    public static boolean showJEILabel = true;
    
    public RecipeStages() {
        
        ForgeRegistries.RECIPE_SERIALIZERS.register(STAGE_SERIALIZER);
        
        if(ModList.get().isLoaded(CraftTweaker.MODID)) {
            setCTLoggerSafely();
        } else {
            RecipeStagesLogger.instance = new RecipeStagesLoggerWithoutCT();
        }
    }
    
    private void setCTLoggerSafely() {
        
        RecipeStagesLogger.instance = new RecipeStagesLoggerWithCT();
    }
    
}
