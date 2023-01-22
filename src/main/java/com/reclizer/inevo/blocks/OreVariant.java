package com.reclizer.inevo.blocks;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum OreVariant implements IStringSerializable {
    COPPER,
    TIN;

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }
}
