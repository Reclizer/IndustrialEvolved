package com.reclizer.inevo.item.itemblock;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemBlockWithMetadataAndName extends ItemBlockMod {
    public ItemBlockWithMetadataAndName(Block block) {
        super(block);
        setHasSubtypes(true);
    }

    @Nonnull
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + stack.getItemDamage();
    }

    @Override
    public int getMetadata(int damage)
    {
        return damage;
    }
}
