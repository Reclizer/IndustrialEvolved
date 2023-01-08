package com.reclizer.inevo.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    /*
     * To add a block, put a line here,
     * -Create a json at assets.eo.blockstates
     * -Create a json at assets.eo.models.block
     * -Create a json at assets.eo.models.item
     * -Add corresponding texture png
     */
    //录入方块id，setCreativeTab(ModCreativeTab.FML_MISC)为方块放在游戏里的某一个标签页下 / setHardness(15f)为方块的硬度（采集速度）
    //录入方块id，基础要素new BlockBase(”方块id“，物品材质）
    public static final Block GRID_BLOCK_1 = new BlockBase("compressed_dirt", Material.GROUND);

    public static final Block GRID_BLOCK_2 = new BlockDirtCompressor("dirt_compressor", Material.GROUND).setHardness(1f);
    //public static final Block GRID_BLOCK_3 = new BlockMetalFurnace("metal_furnace", Material.GROUND).setHardness(1f);
    public static final Block GLOWSTONE_GENERTOR = new BlockGlowstoneGenerator("glowstone_genertor",Material.IRON).setHardness(1f);
    public static final Block ENERGY_STORGE = new BlockEnergyStorage("energy_storge",Material.IRON).setHardness(1f);
    public static final Block ELECTRIC_FURNACE = new BlockElectricFurnace("electric_furnace",Material.IRON).setHardness(1f);
    public static final Block FAST_FURNACE = new BlockFastFurnace("fast_furnace",Material.IRON).setHardness(1f);

    //public static final Block GRID_BLOCK_1 = new BlockBase("test", Material.CLAY).setCreativeTab(ModCreativeTab.FML_MISC).setHardness(15f);
}