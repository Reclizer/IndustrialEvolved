package com.reclizer.inevo.gui.dirtCompressor;

import com.reclizer.inevo.blocks.tileEntity.TileEntityDirtCompressor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerDirtCompressor extends Container {
    private final World world;
    private final BlockPos pos;
    private ItemStackHandler items = new ItemStackHandler(4);
    private  int compressorProgress= 0;
    private final IItemHandler up;
    private final IItemHandler side;
    private final IItemHandler down;
    public ContainerDirtCompressor(EntityPlayer player, World world,int x,int y,int z)
    {
        this.world = world;
        this.pos=new BlockPos(x,y,z);
        //物品槽位机器栏位

        TileEntity tileEntity = world.getTileEntity(this.pos);
        Capability<IItemHandler> itemHandlerCapability = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
        this.side=tileEntity.getCapability(itemHandlerCapability, EnumFacing.NORTH);
        this.up=tileEntity.getCapability(itemHandlerCapability, EnumFacing.UP);
        this.down=tileEntity.getCapability(itemHandlerCapability, EnumFacing.DOWN);

        //InventoryPlayer inventoryPlayer= player.inventory;
        this.addSlotToContainer(new SlotItemHandler(this.up, 0, 45, 57));
        this.addSlotToContainer(new SlotItemHandler(this.down, 0, 117, 37));
        this.addSlotToContainer(new SlotItemHandler(this.side, 0, 45, 37));


        //物品槽位背包栏位
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        //物品槽位手持栏位
        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
        }
    }



    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {

        return playerIn.world.equals(this.world)&&playerIn.getDistanceSq(this.pos)<=64.0;
    }

    @Override//shift自动堆叠物品
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {

        return ItemStack.EMPTY;
    }

//实现进度条
    public int getCompressorProgress(){
        return this.compressorProgress;
    }
    @Override
    public  void detectAndSendChanges(){
        super.detectAndSendChanges();
        TileEntity tileEntity = this.world.getTileEntity(this.pos);
        if(tileEntity instanceof TileEntityDirtCompressor){
            int compressorProgress = ((TileEntityDirtCompressor)tileEntity).getCompressorProgress();
            if(compressorProgress!=this.compressorProgress){
                this.compressorProgress=compressorProgress;
                for(IContainerListener listener:this.listeners){
                    listener.sendWindowProperty(this,0,compressorProgress);
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)//客户端收取数据
    public void updateProgressBar(int id,int data){
        if(id==0){
            this.compressorProgress=data;
        }
    }


}
