package com.reclizer.inevo.blocks.tileEntity;

import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.energy.CustomEnergyStorage;
import com.reclizer.inevo.item.ItemEnergyBase;
import com.reclizer.inevo.item.ModItems;
import com.reclizer.inevo.item.weapon.ItemElectricBow;
import com.reclizer.inevo.util.ItemDataUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import scala.annotation.meta.field;

import javax.annotation.Nullable;

import static net.minecraft.tileentity.TileEntityFurnace.isItemFuel;
import static net.minecraftforge.fml.common.registry.GameRegistry.getFuelValue;

public class TileEntityGlowstoneGenerator extends TileEntity implements ITickable {

    //public ItemStackHandler handler= new ItemStackHandler(1);
    //private int compressorProgress = 0;
    private CustomEnergyStorage storage = new CustomEnergyStorage(100000);//电量
    public int energy=storage.getEnergyStored();//能量变量

    private String customName;
    public int cooktime;//工作时间
    public  int rechargeTime;



    private final ItemStackHandler up = new ItemStackHandler(1){
        @Override
        protected void onContentsChanged(int slot){
            TileEntityGlowstoneGenerator.this.markDirty();
        }
    };
    private final ItemStackHandler down= new ItemStackHandler(1){
        @Override
        protected void onContentsChanged(int slot){
            TileEntityGlowstoneGenerator.this.markDirty();
        }
    };

    public double itemEnergy=ItemDataUtils.getDouble(down.getStackInSlot(0), "energyStored");;
    public double itemMaxEnergy;

    @Override
    public <T> T getCapability(Capability<T> capability,@Nullable EnumFacing facing){
        if(capability== CapabilityEnergy.ENERGY)return(T)this.storage;
//        if(capability== CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)return(T)this.handler;
//        return super.getCapability(capability,facing);

        Capability<IItemHandler> itemHandlerCapability = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
        if(itemHandlerCapability.equals(capability)){
            if(EnumFacing.UP.equals(facing)){
                return itemHandlerCapability.cast(this.up);
            }
            return itemHandlerCapability.cast(this.down);

        }
        return super.getCapability(capability,facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing){
        if(capability== CapabilityEnergy.ENERGY)return true;
        if(capability== CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)return true;
        return super.hasCapability(capability,facing);

    }



    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        //compound.setTag("Inventory",this.handler.serializeNBT());
        compound.setInteger("CookTime",this.cooktime);
        compound.setInteger("RechargeTime",this.rechargeTime);
        compound.setInteger("GuiEnergy",this.energy);
        compound.setString("Name",getDisplayName().toString());
        compound.setTag("Down",this.down.serializeNBT());
        compound.setTag("Up",this.up.serializeNBT());
        this.storage.writeToNBT(compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        //this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
        this.cooktime = compound.getInteger("CookTime");
        this.rechargeTime = compound.getInteger("RechargeTime");
        this.energy = compound.getInteger("GuiEnergy");
        this.customName=compound.getString("Name");
        this.storage.readFromNBT(compound);
        this.down.deserializeNBT(compound.getCompoundTag("Down"));
        this.up.deserializeNBT(compound.getCompoundTag("Up"));
    }

//    @Override
//    public ITextComponent getDisplayName(){
//        return new TextComponentTranslation(IndustrialEvolved.MODID+":glowstone_generator");
//    }




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
            case 1:
                return this.cooktime;
            case 3:
                return this.rechargeTime;
            default:
                return 0;
        }
    }

    public void setfield(int id,int value){
        switch(id)
        {
            case 0:
                this.energy= value;
            case 1:
                this.cooktime=value;
            case 3:
                this.rechargeTime=value;
        }
    }

    public boolean isUsableByPlayer(EntityPlayer player){
        return this.world.getTileEntity(this.pos)!=this?false:player.getDistanceSq((double)this.pos.getX()+0.5D,
                (double)this.pos.getY()+0.5D,(double)this.pos.getZ()+0.5D) <= 64.0D;
    }


    @Override
    public void update() {

        if(energy<storage.getMaxEnergyStored()) {
            //判断槽位不是空的&&判断槽位是否为可燃烧物品
            if (!up.getStackInSlot(0).isEmpty() && isItemFuel(up.getStackInSlot(0))) {
                cooktime++;
                if (cooktime == 25) {
                    energy += getFuelValue(up.getStackInSlot(0));
                    up.getStackInSlot(0).shrink(1);
                    cooktime = 0;
                }
            }
        }

        if(energy>0){
            if (!down.getStackInSlot(0).isEmpty() && isItemRecharge(down.getStackInSlot(0))) {
                rechargeTime++;
                if(rechargeTime==10){
                    energy -= getRechargeValue(down.getStackInSlot(0));
                    //down.getStackInSlot(0).shrink(1);
                    ItemDataUtils.setDouble(down.getStackInSlot(0), "energyStored", Math.max(Math.min(itemEnergy+=1000f, getMaxEnergy(down.getStackInSlot(0))), 0));
                    //setEnergy(down.getStackInSlot(0), getEnergy(down.getStackInSlot(0)) + 100);
                    rechargeTime=0;
                }
            }
        }
    }

    public double getMaxEnergy(ItemStack itemStack) {
        return 12000;
    }

    private boolean isItemFuel(ItemStack stack){
        return getFuelValue(stack)>0;
    }

    private  int getFuelValue(ItemStack stack){

        //可选燃料返还的能量
        if(stack.getItem()== Items.GLOWSTONE_DUST)return 1000;
        else return 0;
    }

    private boolean isItemRecharge(ItemStack stack){
        return getRechargeValue(stack)>0;
    }

    private  int getRechargeValue(ItemStack stack){

        //可选燃料返还的能量
        if(stack.getItem()== ModItems.ELECTRICBOW)return 1000;
        else return 0;
    }


}
