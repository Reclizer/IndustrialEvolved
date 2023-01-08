package com.reclizer.inevo.player;

import net.minecraft.nbt.NBTTagCompound;

public class PlayerSummoned {

    private int summoned = 0;
    private int maxSummoned=4;

    public PlayerSummoned() {
    }

    public int getSummoned() {
        return summoned;
    }

    public void setSummoned(int summoned) {

        if(summoned>this.maxSummoned){
            this.summoned=maxSummoned;
        }else
        {
            this.summoned = summoned;
        }

    }

    public int getMaxSummoned() {
        return maxSummoned;
    }

    public void setMaxSummoned(int maxSummoned) {
        this.maxSummoned = maxSummoned;
    }

    public void copyFrom(PlayerSummoned source) {
        summoned = source.summoned;
        maxSummoned = source.maxSummoned;
    }


    public void saveNBTData(NBTTagCompound compound) {

        compound.setInteger("summoned", summoned);
        compound.setInteger("maxSummoned", maxSummoned);

    }

    public void loadNBTData(NBTTagCompound compound) {

        summoned = compound.getInteger("summoned");
        summoned = compound.getInteger("maxSummoned");

    }
}
