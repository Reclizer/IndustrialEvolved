package com.reclizer.inevo.init;

import com.reclizer.inevo.item.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModCreativeTab {
    public static final CreativeTabs IE_MISC = new CreativeTabs(CreativeTabs.getNextID(), "inEvoMiscTab")//物品注册名
    {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ModItems.HYPER_ENGINE);//群组图标
        }
    };
}
