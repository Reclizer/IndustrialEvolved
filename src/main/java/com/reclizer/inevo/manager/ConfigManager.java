package com.reclizer.inevo.manager;

import com.reclizer.inevo.IndustrialEvolved;
import net.minecraftforge.common.config.Config;

@Config(modid = IndustrialEvolved.MODID)
public class ConfigManager {
    @Config.RequiresWorldRestart
    public static double AdvancedIridiumSwordBaseCost = 800d;
    @Config.RequiresWorldRestart
    public static float AdvancedIridiumSwordBaseAttackDamage = 25f;
}
