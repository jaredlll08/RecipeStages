package com.blamejared.recipestages;

import com.blamejared.recipestages.events.ClientEventHandler;
import com.blamejared.recipestages.recipes.RecipeStageSerializer;
import net.minecraft.util.Util;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

@Mod("recipestages")
public class RecipeStages {
    
    public static final RecipeStageSerializer STAGE_SERIALIZER = Util.make(() -> {
        RecipeStageSerializer recipeStageSerializer = new RecipeStageSerializer();
        recipeStageSerializer.setRegistryName("recipestages", "stage");
        return recipeStageSerializer;
    });
    public static boolean printContainers = false;
    public static boolean showJEILabel = true;
    public static final Map<String, Set<String>> packageStages = new HashMap<>();
    public static final Map<String, Set<String>> containerStages = new HashMap<>();
    
    public RecipeStages() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        ForgeRegistries.RECIPE_SERIALIZERS.register(STAGE_SERIALIZER);
    }
    
    private void doClientStuff(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }
    
}
