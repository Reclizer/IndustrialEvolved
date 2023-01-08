package com.reclizer.inevo.player;

import net.minecraft.nbt.NBTTagCompound;

public class PlayerEnergy {

    private int spaceEnergy = 0;
    private int maxSpaceEnergy =40;

    public PlayerEnergy() {
    }

    public int getSpaceEnergy() {
        return spaceEnergy;
    }

    public void setSpaceEnergy(int spaceEnergy) {

        if(spaceEnergy<=this.maxSpaceEnergy){
            this.spaceEnergy = spaceEnergy;
        }else {
            this.spaceEnergy = getMaxSpaceEnergy();
        }


    }

    public int getMaxSpaceEnergy() {
        return maxSpaceEnergy;
    }

    public void setMaxSpaceEnergy(int maxSpaceEnergy) {
        this.maxSpaceEnergy = maxSpaceEnergy;
    }

    public void copyFrom(PlayerEnergy source) {
        spaceEnergy = source.spaceEnergy;
        maxSpaceEnergy = source.maxSpaceEnergy;
    }


    public void saveNBTData(NBTTagCompound compound) {

        compound.setInteger("spaceEnergy", spaceEnergy);
        compound.setInteger("maxSpaceEnergy", maxSpaceEnergy);

    }

    public void loadNBTData(NBTTagCompound compound) {

        spaceEnergy = compound.getInteger("spaceEnergy");
        spaceEnergy = compound.getInteger("maxSpaceEnergy");

    }
}
