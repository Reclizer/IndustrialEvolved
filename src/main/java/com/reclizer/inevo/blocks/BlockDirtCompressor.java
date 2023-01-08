package com.reclizer.inevo.blocks;

import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.blocks.tileEntity.TileEntityDirtCompressor;
import com.reclizer.inevo.gui.ModGuiElementLoader;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockDirtCompressor extends BlockBase{
    private static final IProperty<EnumFacing> FACING = PropertyDirection.create("facing",EnumFacing.Plane.HORIZONTAL);
/*
    public BlockDirtCompressor(String name, Material material) {
        super(Material.ROCK);
        setUnlocalizedName(FMLTutor.MODID+".dirtCompressor");
        setRegistryName("dirt_compressor");
        setCreativeTab(ModCreativeTab.FML_MISC);;
        setHardness(3.5f);
        setHarvestLevel("pickaxe",1);
        setDefaultState((this.blockState.getBaseState().withProperty(FACING,EnumFacing.NORTH)));
        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));


    }*/

    public BlockDirtCompressor(String name, Material material) {
        super(name, material);
        this.setDefaultState((this.blockState.getBaseState().withProperty(FACING,EnumFacing.NORTH)));
    }


    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this,FACING);
    }
    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
                                            int meta, EntityLivingBase placer, EnumHand hand){
        return  this.getDefaultState()
                .withProperty(FACING,placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public int getMetaFromState(IBlockState state){
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(FACING,EnumFacing.getHorizontal(meta));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            int x=pos.getX(),y=pos.getY(),z=pos.getZ();
            playerIn.openGui(IndustrialEvolved.MODID,
                    ModGuiElementLoader.DIRT_COMPRESSOR,worldIn,x,y,z);
        }
        return true;
    }

    //以下为方块实体绑定
    @Override
    public TileEntity createNewTileEntity(World world, int meta){
        return new TileEntityDirtCompressor();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state){
        return EnumBlockRenderType.MODEL;
    }
    //注册方法
    /*
    @Override
    public void registerModels() {
        FMLTutor.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

     */
//破坏方块掉出物品
    @Override
    public void breakBlock(World world,BlockPos pos,IBlockState state){
        TileEntity tileEntity = world.getTileEntity(pos);
        Capability<IItemHandler> itemHandlerCapability = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
        IItemHandler up = tileEntity.getCapability(itemHandlerCapability,EnumFacing.UP);
        IItemHandler down = tileEntity.getCapability(itemHandlerCapability,EnumFacing.DOWN);
        IItemHandler side = tileEntity.getCapability(itemHandlerCapability,EnumFacing.NORTH);

        Block.spawnAsEntity(world,pos,up.getStackInSlot(0));
        Block.spawnAsEntity(world,pos,down.getStackInSlot(0));
        Block.spawnAsEntity(world,pos,side.getStackInSlot(0));

        super.breakBlock(world,pos,state);
    }




}
