package com.reclizer.inevo.gui.fastFurnace;

import com.reclizer.inevo.blocks.tileEntity.TileEntityEnergyStorage;
import com.reclizer.inevo.blocks.tileEntity.TileEntityFastFurnace;
import com.reclizer.inevo.net.Messages;
import com.reclizer.inevo.net.PacketSyncPower;
import com.reclizer.inevo.tools.IEnergyContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;


public class ContainerFastFurnace extends Container implements IEnergyContainer {
    private final TileEntityFastFurnace tileentity;
    //private int energy;
    private static final int PROGRESS_ID=0;
    public ContainerFastFurnace(InventoryPlayer player, TileEntityFastFurnace tileentity){
        this.tileentity=tileentity;

        addOwnSlots();
        addPlayerSlots(player);


    }


    private void addPlayerSlots(IInventory playerInventory) {
        // Slots for the main inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = 10 + col * 18;
                int y = row * 18 + 70;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
            }
        }

        // Slots for the hotbar
        for (int row = 0; row < 9; ++row) {
            int x = 10 + row * 18;
            int y = 58 + 70;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }

    private void addOwnSlots() {
        IItemHandler itemHandler = this.tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        int x = 10;
        int y = 26;

        int slotIndex = 0;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y)); x += 18;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y)); x += 18;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));

        x = 118;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y)); x += 18;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y)); x += 18;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        //判断玩家是否可以交互
        return tileentity.canInteractWith(playerIn);
    }


    @Override
    public void detectAndSendChanges() {
        //处理方块工作数据,同步客户端和服务端数据
        super.detectAndSendChanges();
        if(tileentity.getProgress() !=tileentity.getClientProgress()){
            tileentity.setClientProgress(tileentity.getProgress());
            for(IContainerListener listener:listeners){
                listener.sendWindowProperty(this,PROGRESS_ID,tileentity.getProgress());
            }
        }
        //''''''''''Energy'''''''''''''''
        if(tileentity.getEnergy()!=tileentity.getClientEnergy()){
            tileentity.setClientEnergy(tileentity.getEnergy());
            for(IContainerListener listener:listeners){
                if(listener instanceof EntityPlayerMP){
                    EntityPlayerMP playerMP=(EntityPlayerMP) listener;
                    Messages.INSTANCE.sendTo(new PacketSyncPower(tileentity.getEnergy()),playerMP);
                }

            }
        }


//        for(IContainerListener listener:listeners){
//            listener.sendWindowProperty(this,PROGRESS_ID,tileentity.getProgress());
//        }

    }




    @Override
    public void updateProgressBar(int id, int data) {
        //更新客户端物品工作数据
        if(id==PROGRESS_ID){
            tileentity.setClientProgress(data);
        }

//        if(id==PROGRESS_ID){
//            tileentity.setProgress(data);
//        }
    }

    @Override
    public void syncPower(int energy) {
        //更新客户端电量数据
        tileentity.setClientEnergy(energy);
    }







    @Override//shift自动堆叠物品
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < TileEntityFastFurnace.SIZE) {
                if (!this.mergeItemStack(itemstack1, TileEntityFastFurnace.SIZE, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, TileEntityFastFurnace.SIZE, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }
}
