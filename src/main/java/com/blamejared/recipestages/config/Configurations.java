package com.blamejared.recipestages.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;

@Configurations(modid = "recipestages")
public class Configurations {

    @Configurations.Comment("Toggle whether to display which stage is needed for a recipe to work in the JEI entries.")
    @Configurations.Name("Show stage name")

    public static boolean showStageName = true;
}
