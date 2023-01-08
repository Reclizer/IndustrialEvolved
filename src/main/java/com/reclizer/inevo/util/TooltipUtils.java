package com.reclizer.inevo.util;

import com.reclizer.inevo.others.UnitDisplayUtils;
import com.reclizer.inevo.others.UnitDisplayUtils.ElectricUnit;

public class TooltipUtils {

    public static String getEnergyDisplay(double energy) {
        if (energy == Double.MAX_VALUE) {
            return LangUtils.localize("gui.infinite");
        }return UnitDisplayUtils.getDisplayShort(energy, ElectricUnit.JOULES);
    }

    public static String getEnergyDisplay(double energy, double max) {
        if (energy == Double.MAX_VALUE) {
            return LangUtils.localize("gui.infinite");
        }
        String energyString = getEnergyDisplay(energy);
        String maxString = getEnergyDisplay(max);
        return energyString + "/" + maxString;
    }
}
