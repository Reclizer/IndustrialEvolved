package com.reclizer.inevo.gui.energystorage;

import com.reclizer.inevo.blocks.tileEntity.TileEntityEnergyStorage;
import com.reclizer.inevo.blocks.tileEntity.TileEntityGlowstoneGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;


public class ContainerEnergyStorage extends Container {
    private final TileEntityEnergyStorage tileentity;
    private int energy;
    public ContainerEnergyStorage(InventoryPlayer player, TileEntityEnergyStorage tileentity){
        this.tileentity=tileentity;


        //物品槽位背包栏位
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        //物品槽位手持栏位
        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 142));
        }


    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {

        return this.tileentity.isUsableByPlayer(playerIn);
    }

    @Override
    public void updateProgressBar(int id,int data){
        this.tileentity.setfield(id,data);
    }

    @Override
    public void detectAndSendChanges(){
        super.detectAndSendChanges();

        for(int i=0;i<this.listeners.size();i++){
            IContainerListener listener= (IContainerListener) this.listeners.get(i);
            if(this.energy!=this.tileentity.getField(0)){
                listener.sendWindowProperty(this,0,this.tileentity.getField(0));
            }
        }
        this.energy=this.tileentity.getField(0);

    }




}
