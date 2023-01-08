package com.reclizer.inevo.blocks.tileEntity;

import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.energy.CustomEnergyStorage;
import com.reclizer.inevo.tools.MyEnergyStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class TileEntityEnergyStorage extends TileEntity implements ITickable {
    private CustomEnergyStorage storage = new CustomEnergyStorage(10000);//电量
    public int energy=storage.getEnergyStored();//能量变量

    private String customName;
    //public static final int MAX_POWER=10000;
//    public static final int RF_PER_TICK=100;
//    public static final int RF_PER_TICK_INPUT=20;

    @Override
    public void update() {
        if(world.isBlockPowered(pos)) energy+=100;

        sendEnergy();
//        if(energyStorage.getEnergyStored()<RF_PER_TICK){
//            return;
//        }
//        storage
//        energyStorage.consumePower(RF_PER_TICK);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing){
        if(capability== CapabilityEnergy.ENERGY)return true;
        return super.hasCapability(capability,facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability,@Nullable EnumFacing facing){
        if(capability== CapabilityEnergy.ENERGY)return (T)this.storage;
        return super.getCapability(capability,facing);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("GuiEnergy",this.energy);
        compound.setString("Name",getDisplayName().toString());
        this.storage.writeToNBT(compound);
//        compound.setInteger("energy",energyStorage.getEnergyStored());
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
       this.energy = compound.getInteger("GuiEnergy");
        this.customName=compound.getString("Name");
        this.storage.readFromNBT(compound);
        //energyStorage.setEnergy(compound.getInteger("energy"));

    }


    //private MyEnergyStorage energyStorage =new MyEnergyStorage(MAX_POWER,RF_PER_TICK_INPUT);

    private void sendEnergy() {
        if (storage.getEnergyStored() > 0) {
            for (EnumFacing facing : EnumFacing.VALUES) {
                TileEntity tileEntity = world.getTileEntity(pos.offset(facing));
                if (tileEntity != null && tileEntity.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite())) {
                    IEnergyStorage handler = tileEntity.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite());
                    if (handler != null && handler.canReceive()) {
                        int accepted = handler.receiveEnergy(storage.getEnergyStored(), false);
                        storage.consumePower(accepted);
                        if (storage.getEnergyStored() <= 0) {
                            break;
                        }
                    }
                }
            }
            markDirty();
        }
    }

    @Override
    public ITextComponent getDisplayName(){
        return new TextComponentTranslation(IndustrialEvolved.MODID+":energy_storage");
    }

    public int getEnergyStored(){
        return  this.energy;
    }

    public  int getMaxEnergyStored(){
        return this.storage.getMaxEnergyStored();
    }

    public int getField(int id){
        switch(id)
        {
            case 0:
                return this.energy;

            default:
                return 0;
        }
    }

    public void setfield(int id,int value){
        switch(id)
        {
            case 0:
                this.energy= value;

        }
    }

    public boolean isUsableByPlayer(EntityPlayer player){
        return this.world.getTileEntity(this.pos)!=this?false:player.getDistanceSq((double)this.pos.getX()+0.5D,
                (double)this.pos.getY()+0.5D,(double)this.pos.getZ()+0.5D) <= 64.0D;
    }

}
