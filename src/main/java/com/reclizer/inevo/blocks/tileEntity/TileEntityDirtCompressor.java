package com.reclizer.inevo.blocks.tileEntity;

import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.item.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityDirtCompressor extends TileEntity implements ITickable {
    public  static final String ID = IndustrialEvolved.MODID+":dirt_compressor";


    private int compressorProgress = 0;

    private final ItemStackHandler up = new ItemStackHandler(1){
        @Override
        protected void onContentsChanged(int slot){
            TileEntityDirtCompressor.this.markDirty();
        }
    };
    private final ItemStackHandler side = new ItemStackHandler(1){
        @Override
        protected void onContentsChanged(int slot){
            TileEntityDirtCompressor.this.markDirty();
        }
    };
    private final ItemStackHandler down = new ItemStackHandler(1){
        @Override
        protected void onContentsChanged(int slot){
            TileEntityDirtCompressor.this.markDirty();
        }
    };

    public int getCompressorProgress(){
        return this.compressorProgress;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        this.compressorProgress = compound.getInteger("Progress");
        this.down.deserializeNBT(compound.getCompoundTag("Down"));
        this.side.deserializeNBT(compound.getCompoundTag("Side"));
        this.up.deserializeNBT(compound.getCompoundTag("Up"));
        super.readFromNBT(compound);
    }
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        compound.setInteger("Progress",this.compressorProgress);
        compound.setTag("Down",this.down.serializeNBT());
        compound.setTag("Side",this.side.serializeNBT());
        compound.setTag("Up",this.up.serializeNBT());
        return super.writeToNBT(compound);
    }
    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing){
        Capability<IItemHandler> itemHandlerCapability = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
        return itemHandlerCapability.equals(capability) || super.hasCapability(capability,facing);

    }
    @Override
    public <T> T getCapability(Capability<T> capability,@Nullable EnumFacing facing){
        Capability<IItemHandler> itemHandlerCapability = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
        if(itemHandlerCapability.equals(capability)){
            if(EnumFacing.UP.equals(facing)){
                return itemHandlerCapability.cast(this.up);
            }
            if(EnumFacing.DOWN.equals(facing)){
                return itemHandlerCapability.cast(this.down);
            }
            return itemHandlerCapability.cast(this.side);

        }
        return super.getCapability(capability,facing);
    }


    @Override
    public void update(){
        Item dirt = Item.getItemFromBlock(Blocks.DIRT);//输入物品
        boolean canExtractInput = dirt.equals(this.side.extractItem(0,1,true).getItem());
        if (canExtractInput){
            if(this.compressorProgress%20==0){
                Item dawnItem = ModItems.DIRT_BALL;//燃料
                boolean canExtractDawnItem=dawnItem.equals(this.up.extractItem(0,1,true).getItem());
                if (canExtractDawnItem){
                    this.up.extractItem(0,1,false);
                    this.compressorProgress+=1;
                }
            }
            else
            {
                this.compressorProgress+=1;
                if(this.compressorProgress>=240){
                    ItemStack diamond = new ItemStack(Items.DIAMOND);//产出物品
                    boolean canInsertDIAMOND=this.down.insertItem(0,diamond,true).isEmpty();
                    if (canInsertDIAMOND){
                        this.down.insertItem(0,diamond,false);
                        this.side.extractItem(0,1,false);
                        this.compressorProgress = 0;
                    }else{
                        this.compressorProgress-=1;
                    }
                }
                else
                {
                    this.markDirty();
                }
            }
        }else if(this.compressorProgress>0){
            this.compressorProgress=0;
            this.markDirty();
        }
    }
}
