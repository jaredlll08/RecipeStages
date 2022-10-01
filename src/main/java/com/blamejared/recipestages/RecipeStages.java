package com.blamejared.recipestages;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.logger.CraftTweakerLogger;
import com.blamejared.recipestages.recipes.RecipeStageSerializer;
import com.blamejared.recipestages.recipes.ShapedRecipeStageSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Mod("recipestages")
public class RecipeStages {
    
    public static final RecipeStageSerializer STAGE_SERIALIZER = new RecipeStageSerializer();
    public static final ShapedRecipeStageSerializer SHAPED_STAGE_SERIALIZER = new ShapedRecipeStageSerializer();
    
    public static final Map<String, Set<String>> PACKAGE_STAGES = new HashMap<>();
    public static final Map<String, Set<String>> CONTAINER_STAGES = new HashMap<>();
    
    public static boolean printContainer = false;
    public static boolean showJEILabel = true;
    
    public static Logger CONTAINER_LOGGER = LogManager.getLogger("Recipe Stages");
    
    public RecipeStages() {
        
        if(ModList.get().isLoaded(CraftTweakerConstants.MOD_ID)) {
            CONTAINER_LOGGER = LogManager.getLogger(CraftTweakerLogger.LOGGER_NAME);
        }
    
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(RecipeSerializer.class, this::registerSerializers);
    }
    
    @SubscribeEvent
    public void registerSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {
        
        event.getRegistry().register(STAGE_SERIALIZER);
        event.getRegistry().register(SHAPED_STAGE_SERIALIZER);
    }
    
}
