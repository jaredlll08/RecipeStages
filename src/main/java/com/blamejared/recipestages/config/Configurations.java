package com.blamejared.recipestages.config;

import net.minecraftforge.common.config.Config;

@Config(modid = "recipestages")
public class Configurations {

    @Config.Comment("Toggle whether to display which stage is needed for a recipe to work in the JEI entries.")
    @Config.Name("Show stage name")

    public static boolean showStageName = true;
}
