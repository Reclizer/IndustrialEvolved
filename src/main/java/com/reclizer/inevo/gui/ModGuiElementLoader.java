package com.reclizer.inevo.gui;

import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.blocks.tileEntity.TileEntityEnergyStorage;
import com.reclizer.inevo.blocks.tileEntity.TileEntityFastFurnace;
import com.reclizer.inevo.blocks.tileEntity.TileEntityGlowstoneGenerator;
import com.reclizer.inevo.gui.dirtCompressor.ContainerDirtCompressor;
import com.reclizer.inevo.gui.dirtCompressor.GuiDirtCompressor;
import com.reclizer.inevo.gui.energystorage.ContainerEnergyStorage;
import com.reclizer.inevo.gui.energystorage.GuiEnergyStorage;
import com.reclizer.inevo.gui.fastFurnace.ContainerFastFurnace;
import com.reclizer.inevo.gui.fastFurnace.GuiFastFurnace;
import com.reclizer.inevo.gui.glowstoneGenerator.ContainerGlowstoneGenerator;
import com.reclizer.inevo.gui.glowstoneGenerator.GuiGlowstoneGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import javax.annotation.Nullable;

public class ModGuiElementLoader implements IGuiHandler {

    public static final int GUI_DEMO = 1;
    public static final int GUI_RESEARCH = 2;
    public static final int DIRT_COMPRESSOR = 3;
    public static final int GLOWSTONE_GENERATOR=4;
    public static final int ENERGY_STORAGE=5;
    public static final int FAST_FURNACE=6;



    public ModGuiElementLoader()
    {
        //NetworkRegistry.INSTANCE.registerGuiHandler(IndustrialEvolved.instance, this);
    }

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID)
        {
            case DIRT_COMPRESSOR:
                return new ContainerDirtCompressor(player,world,x,y,z);
            case GLOWSTONE_GENERATOR:
                return  new ContainerGlowstoneGenerator(player.inventory, (TileEntityGlowstoneGenerator)world.getTileEntity(new BlockPos(x,y,z)));
            case ENERGY_STORAGE:
                return  new ContainerEnergyStorage(player.inventory, (TileEntityEnergyStorage)world.getTileEntity(new BlockPos(x,y,z)));
            case FAST_FURNACE:
                return  new ContainerFastFurnace(player.inventory, (TileEntityFastFurnace)world.getTileEntity(new BlockPos(x,y,z)));
                default:
                return null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID)
        {
            case DIRT_COMPRESSOR:
                return new GuiDirtCompressor(player,world,x,y,z);
            case GLOWSTONE_GENERATOR:
                return  new GuiGlowstoneGenerator(player.inventory, (TileEntityGlowstoneGenerator)world.getTileEntity(new BlockPos(x,y,z)));
            case ENERGY_STORAGE:
                return  new GuiEnergyStorage(player.inventory, (TileEntityEnergyStorage)world.getTileEntity(new BlockPos(x,y,z)));
            case FAST_FURNACE:
                return  new GuiFastFurnace((TileEntityFastFurnace)world.getTileEntity(new BlockPos(x,y,z)),new ContainerFastFurnace(player.inventory,(TileEntityFastFurnace)world.getTileEntity(new BlockPos(x,y,z))));
            default:
                return null;
        }
    }

}

