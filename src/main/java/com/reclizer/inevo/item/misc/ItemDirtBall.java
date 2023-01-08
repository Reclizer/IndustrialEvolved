package com.reclizer.inevo.item.misc;

import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.item.ItemBase;
import net.minecraft.item.Item;

public class ItemDirtBall extends ItemBase {
    public ItemDirtBall(String name) {
        super(name);
        setUnlocalizedName(name);
        setRegistryName(name);
        setMaxStackSize(16);
    }
}
