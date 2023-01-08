package com.reclizer.inevo.blocks;

import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.blocks.tileEntity.TileEntityEnergyStorage;
import com.reclizer.inevo.blocks.tileEntity.TileEntityGlowstoneGenerator;
import com.reclizer.inevo.gui.ModGuiElementLoader;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockEnergyStorage extends BlockBase{
    public BlockEnergyStorage(String name, Material material) {

        super(name,material);

    }

    @Override
    public boolean hasTileEntity(IBlockState state){
        return true;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            int x=pos.getX(),y=pos.getY(),z=pos.getZ();
            playerIn.openGui(IndustrialEvolved.MODID,
                    ModGuiElementLoader.ENERGY_STORAGE,worldIn,x,y,z);
        }
        return true;
    }


    @Override
    public TileEntity createTileEntity(World world, IBlockState state){
        return new TileEntityEnergyStorage();
    }


    @Override
    public void breakBlock(World world,BlockPos pos,IBlockState state){

        TileEntityGlowstoneGenerator tileentity = (TileEntityGlowstoneGenerator)world.getTileEntity(pos);
        Capability<IItemHandler> itemHandlerCapability = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
        IItemHandler up = tileentity.getCapability(itemHandlerCapability,EnumFacing.UP);
        IItemHandler down = tileentity.getCapability(itemHandlerCapability,EnumFacing.DOWN);
        Block.spawnAsEntity(world,pos,up.getStackInSlot(0));
        Block.spawnAsEntity(world,pos,down.getStackInSlot(0));
        //world.spawnEntity(new EntityItem(world,pos.getX(),pos.getY(),pos.getZ(),tileentity.handler.getStackInSlot(0)));
        super.breakBlock(world,pos,state);
    }
}
