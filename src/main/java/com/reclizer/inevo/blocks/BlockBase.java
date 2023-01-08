package com.reclizer.inevo.blocks;



import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.init.ModCreativeTab;
import com.reclizer.inevo.item.ModItems;
import com.reclizer.inevo.util.IHasModel;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

import java.util.Random;

public class BlockBase extends BlockContainer implements IHasModel {
    public BlockBase(String name, Material material) {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(ModCreativeTab.IE_MISC);;

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));

        setHardness(5.0F);
        //setResistance(1000.0F);
        //setHarvestLevel("pickaxe", 1);
        //setLightLevel(1f);
        setLightOpacity(1);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return super.getItemDropped(state, rand, fortune);
    }

    @Override
    public int quantityDropped(Random rand) {
        return super.quantityDropped(rand);
    }

    @Override
    public void registerModels() {
        IndustrialEvolved.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

    //以下为方块实体绑定
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return null;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
}
