package com.reclizer.inevo.blocks;

import com.reclizer.inevo.init.ModCreativeTab;
import com.reclizer.inevo.init.ModelHandler;
import com.reclizer.inevo.item.itemblock.ItemBlockWithMetadataAndName;
import com.reclizer.inevo.item.ModItems;
import com.reclizer.inevo.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;


public class BlockOres extends Block implements IHasModel{
    public BlockOres(String name) {
        super(Material.IRON);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(ModCreativeTab.IE_MISC);;
        setDefaultState(blockState.getBaseState().withProperty(InevoStateProps.ORE_VARIANT, OreVariant.COPPER));




        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlockWithMetadataAndName(this).setRegistryName(this.getRegistryName()));

        setHardness(5.0F);
        //setResistance(1000.0F);
        setHarvestLevel("pickaxe", 2);
        //setLightLevel(1f);
        setLightOpacity(1);
    }


//==========================================================================================================
@Nonnull
@Override
public BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, InevoStateProps.ORE_VARIANT);
}

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(InevoStateProps.ORE_VARIANT).ordinal();
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        if (meta >= OreVariant.values().length) {
            meta = 0;
        }
        return getDefaultState().withProperty(InevoStateProps.ORE_VARIANT, OreVariant.values()[meta]);
    }

    @Override
    public void getSubBlocks(CreativeTabs par2, NonNullList<ItemStack> par3) {
        for(int i = 0; i < OreVariant.values().length; i++)
            par3.add(new ItemStack(this, 1, i));
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public boolean isBeaconBase(IBlockAccess world, BlockPos pos, BlockPos beaconPos) {
        return true;
    }

//    @Override
//    public LexiconEntry getEntry(World world, BlockPos pos, EntityPlayer player, ItemStack lexicon) {
//        StorageVariant variant = world.getBlockState(pos).getValue(InevoStateProps.ORE_VARIANT);
//        return variant == StorageVariant.MANASTEEL ? LexiconData.pool : LexiconData.terrasteel;
//    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModels() {
        ModelHandler.registerBlockToState(this, OreVariant.values().length);
    }






}

