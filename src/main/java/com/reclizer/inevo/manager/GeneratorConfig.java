package com.reclizer.inevo.manager;

import com.reclizer.inevo.IndustrialEvolved;

import net.minecraftforge.common.config.Config;

@Config(modid = IndustrialEvolved.MODID, category = "generator")
public class GeneratorConfig {

    @Config.Comment(value = "Maximum amount of power for the generator")
    public static int MAX_POWER = 100000;

    @Config.Comment(value = "Factor to control how much power is generated per damage unit")
    public static float POWER_DAMAGE_FACTOR = 5.0f;
}
