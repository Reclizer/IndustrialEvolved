package com.reclizer.inevo.player;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class PropertiesDispatcher implements ICapabilitySerializable<NBTTagCompound> {
    private PlayerSummoned playerSummoned = new PlayerSummoned();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == PlayerProperties.PLAYER_SUMMONED;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == PlayerProperties.PLAYER_SUMMONED) {
            return (T) playerSummoned;
        }
        return null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        playerSummoned.saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        playerSummoned.loadNBTData(nbt);
    }
}
