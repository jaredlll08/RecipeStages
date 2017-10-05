package com.blamejared.recipestages;

import com.blamejared.recipestages.handlers.Recipes;
import com.blamejared.recipestages.proxy.CommonProxy;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.darkhax.gamestages.capabilities.PlayerDataHandler;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;

import static com.blamejared.recipestages.compat.JEIPlugin.recipeRegistry;
import static com.blamejared.recipestages.reference.Reference.*;

@Mod(modid = MOD_ID, name = MOD_NAME, version = VERSION, dependencies = "required-after:crafttweaker;")
public class RecipeStages {
    
    
    @SidedProxy(clientSide = "com.blamejared.recipestages.proxy.ClientProxy", serverSide = "com.blamejared.recipestages.proxy.CommonProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.registerEvents();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
    
    @EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
    }
    
    @EventHandler
    public void onFMLServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandBase() {
            @Override
            public String getName() {
                return "resetRS";
            }
            
            @Override
            public String getUsage(ICommandSender sender) {
                return "resetRS";
            }
            
            @Override
            public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
                if(sender instanceof EntityPlayer) {
                    PlayerDataHandler.getStageData((EntityPlayer) sender).clear();
                    for(IRecipe recipe : Recipes.recipes) {
                        recipeRegistry.hideRecipe(recipeRegistry.getRecipeWrapper(recipe, VanillaRecipeCategoryUid.CRAFTING));
                    }
                }
                sender.sendMessage(new TextComponentString("RecipeStages has been reset! All player stages have been lost!"));
            }
        });
    }
}
