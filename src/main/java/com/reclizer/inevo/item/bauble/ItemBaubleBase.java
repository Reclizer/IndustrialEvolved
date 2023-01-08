package com.reclizer.inevo.item.bauble;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.reclizer.inevo.init.ModCreativeTab;
import com.reclizer.inevo.item.ItemBase;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBaubleBase extends ItemBase implements IBauble {

    public ItemBaubleBase(String name) {
        super(name);
        setMaxStackSize(1);
        setCreativeTab(ModCreativeTab.IE_MISC);
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return null;
    }


}
