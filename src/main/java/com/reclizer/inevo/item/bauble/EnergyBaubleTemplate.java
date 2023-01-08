package com.reclizer.inevo.item.bauble;


import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.render.IRenderBauble;
import com.reclizer.inevo.item.ItemEnergyBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract  class EnergyBaubleTemplate extends ItemEnergyBase implements IBauble {

    public EnergyBaubleTemplate(double maxElectricity, String name) {
        super(maxElectricity, name);
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return null;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {

    }


}
