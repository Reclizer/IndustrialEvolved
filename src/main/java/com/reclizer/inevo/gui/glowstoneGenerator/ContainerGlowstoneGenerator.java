package com.reclizer.inevo.gui.glowstoneGenerator;

import com.reclizer.inevo.blocks.tileEntity.TileEntityGlowstoneGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import scala.xml.dtd.EMPTY;


public class ContainerGlowstoneGenerator extends Container {
    private final TileEntityGlowstoneGenerator tileentity;
    private int energy,cookTime;
    private final IItemHandler Input;
    private final IItemHandler Extract;
    public ContainerGlowstoneGenerator(InventoryPlayer player,TileEntityGlowstoneGenerator tileentity){
        this.tileentity=tileentity;
        //IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null);
        //this.addSlotToContainer(new SlotItemHandler(handler,0,80,33));


        Capability<IItemHandler> itemHandlerCapability = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
        this.Input=tileentity.getCapability(itemHandlerCapability, EnumFacing.UP);
        this.Extract=tileentity.getCapability(itemHandlerCapability, EnumFacing.DOWN);

        this.addSlotToContainer(new SlotItemHandler(this.Input,0,80,33));
        this.addSlotToContainer(new SlotItemHandler(this.Extract,0,40,33));

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
            if(this.cookTime!=this.tileentity.getField(1)){
                listener.sendWindowProperty(this,1,this.tileentity.getField(1));
            }
        }
        this.energy=this.tileentity.getField(0);
        this.cookTime=this.tileentity.getField(1);
    }



    @Override//shift自动堆叠物品
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {

        ItemStack stack = ItemStack.EMPTY;
        Slot slot= (Slot)this.inventorySlots.get(index);

        if(slot!=null&&slot.getHasStack()){
            ItemStack stack1=slot.getStack();
            stack=stack1.copy();

            if(index>=0&&index<27)
            {
                if(!this.mergeItemStack(stack1,27,36,false))return ItemStack.EMPTY;
            }
            else if(index>=27&&index<36)
            {
                if(!this.mergeItemStack(stack1,0,27,false))return ItemStack.EMPTY;
            }
            else if(!this.mergeItemStack(stack1,0,36,false))
            {
                return ItemStack.EMPTY;
            }
            if(stack1.isEmpty()) slot.putStack(ItemStack.EMPTY);
            else slot.onSlotChanged();

            if(stack1.getCount()==stack.getCount()) return ItemStack.EMPTY;
            else slot.onTake(playerIn,stack1);
        }
        return stack;
    }
}
