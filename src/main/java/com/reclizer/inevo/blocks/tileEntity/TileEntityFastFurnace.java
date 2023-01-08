package com.reclizer.inevo.blocks.tileEntity;

import com.reclizer.inevo.tools.MyEnergyStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nullable;

public class TileEntityFastFurnace extends TileEntity implements ITickable {
    public static final int INPUT_SLOTS = 3;
    public static final int OUTPUT_SLOTS = 3;
    public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;
    public static final int MAX_PROGRESS=40;
    private int progress=0;
    //:::::::energy::::::::


    public static final int MAX_POWER=100000;
    public static final  int RF_PER_TICK=20;
    public static final  int RF_PER_TICK_INPUT=100;
    private int clientProgress=-1;
    private int clientEnergy = -1;


//___________________________________________________________________________________________________

    //处理物品的输入输出
    private ItemStackHandler inputHandler= new ItemStackHandler(INPUT_SLOTS){
        @Override
        protected void onContentsChanged(int slot){
            TileEntityFastFurnace.this.markDirty();
        }
    };

    private ItemStackHandler outputHandler= new ItemStackHandler(OUTPUT_SLOTS){
        @Override
        protected void onContentsChanged(int slot){
            TileEntityFastFurnace.this.markDirty();
        }
    };


    private CombinedInvWrapper combinedInvWrapper = new CombinedInvWrapper(inputHandler,outputHandler);
    //___________________________________________________________________________________________________

    private MyEnergyStorage energyStorage=new MyEnergyStorage(MAX_POWER,RF_PER_TICK_INPUT);



    //___________________________________________________________________________________________________

    //处理方块的NBT信息
    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        if(compound.hasKey("itemsIn")){
            inputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsIn"));
        }
        if(compound.hasKey("itemsOut")){
            outputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsOut"));
        }
        progress=compound.getInteger("progress");
        //:::::::energy::::::::
        energyStorage.setEnergy(compound.getInteger("energy"));

    }
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setTag("itemsIn",inputHandler.serializeNBT());
        compound.setTag("itemsOut",outputHandler.serializeNBT());
        compound.setInteger("progress",progress);
        //:::::::energy::::::::
        compound.setInteger("energy",energyStorage.getEnergyStored());
        return compound;
    }
    //___________________________________________________________________________________________________

    //判断玩家距离多远断开与物品的交互

    public boolean canInteractWith(EntityPlayer playIn){
        return !isInvalid()&& playIn.getDistanceSq(pos.add(0.5D,0.5D,0.5D))<=64D;
    }
    //___________________________________________________________________________________________________
    //设定方块各面能通过的数据类型
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing){
        if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return true;
        }
        if(capability== CapabilityEnergy.ENERGY){
            return true;
        }
        return super.hasCapability(capability,facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability,@Nullable EnumFacing facing){

        if(capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){

            if(facing==null){
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(combinedInvWrapper);
            }else if(facing==EnumFacing.UP){
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inputHandler);
            }else {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(outputHandler);
            }
        }
        //:::::::energy::::::::
        if(capability== CapabilityEnergy.ENERGY){
            return CapabilityEnergy.ENERGY.cast(energyStorage);
        }
        return super.getCapability(capability,facing);
    }


    //-----------------------------------------------------------------------------------------
    //处理数据的发送和接收
    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getClientProgress() {
        return clientProgress;
    }

    public void setClientProgress(int clientProgress) {
        this.clientProgress = clientProgress;
    }

    public int getClientEnergy() {
        return clientEnergy;
    }

    public void setClientEnergy(int clientEnergy) {
        this.clientEnergy = clientEnergy;
    }

    public int getEnergy() {
        return energyStorage.getEnergyStored();
    }

    //-------------------------------------------------------------------------------------------

    //设定实体方块update更新时,所需要的运行的工作

    private boolean insertOutput(ItemStack output, boolean simulate) {
        for (int i = 0 ; i < OUTPUT_SLOTS ; i++) {
            ItemStack remaining = outputHandler.insertItem(i, output, simulate);
            if (remaining.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void startSmelt() {
        for (int i = 0 ; i < INPUT_SLOTS ; i++) {
            ItemStack result = FurnaceRecipes.instance().getSmeltingResult(inputHandler.getStackInSlot(i));
            if (!result.isEmpty()) {
                if (insertOutput(result.copy(), true)) {

                    progress = MAX_PROGRESS;
                    markDirty();
                }
                break;
            }
        }
    }

    private void attemptSmelt() {
        for (int i = 0 ; i < INPUT_SLOTS ; i++) {
            ItemStack result = FurnaceRecipes.instance().getSmeltingResult(inputHandler.getStackInSlot(i));
            if (!result.isEmpty()) {
                // This copy is very important!(
                if (insertOutput(result.copy(), false)) {
                    inputHandler.extractItem(i, 1, false);
                    break;
                }
            }
        }
    }
    //___________________________________________________________________________________________________
    //实体方块更新
    @Override
    public void update() {
        if(!world.isRemote){
            if(energyStorage.getEnergyStored()<RF_PER_TICK){
                return;
            }

            if (progress > 0) {
                energyStorage.consumePower(RF_PER_TICK);
                progress--;
                if (progress == 0) {
                    attemptSmelt();
                }
                markDirty();
            } else {
                startSmelt();
            }
        }
    }
    //___________________________________________________________________________________________________
}
