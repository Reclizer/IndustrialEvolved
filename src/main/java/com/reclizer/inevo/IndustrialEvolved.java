package com.reclizer.inevo;


import com.reclizer.inevo.blocks.ModBlocks;
import com.reclizer.inevo.blocks.tileEntity.TileEntityDirtCompressor;
import com.reclizer.inevo.blocks.tileEntity.TileEntityEnergyStorage;
import com.reclizer.inevo.blocks.tileEntity.TileEntityFastFurnace;
import com.reclizer.inevo.blocks.tileEntity.TileEntityGlowstoneGenerator;
import com.reclizer.inevo.entity.ModEntities;
import com.reclizer.inevo.init.RegistryHandler;
import com.reclizer.inevo.network.Messages;
import com.reclizer.inevo.player.EnergyTickHandler;
import com.reclizer.inevo.player.PlayerEnergy;
import com.reclizer.inevo.player.PlayerPropertyEvents;
import com.reclizer.inevo.util.Reference;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;
import com.reclizer.inevo.proxy.CommonProxy;

import javax.annotation.Nullable;

@Mod(modid = IndustrialEvolved.MODID, name = IndustrialEvolved.NAME, version = IndustrialEvolved.VERSION,
        acceptedMinecraftVersions = "[1.12,1.13)",dependencies=IndustrialEvolved.DEPENDENCIES)
public class IndustrialEvolved
{
    public static final String MODID = "inevo";
    public static final String NAME = "Industrial Evolved";
    public static final String VERSION = "1.0";
    public static final String DEPENDENCIES = "after:baubles@[1.5.2,);";
    //after:jei@[1.12.2-4.15.0.268,);
    public static Logger logger;


    @Mod.Instance
    public static IndustrialEvolved instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        proxy.preInit(event);
        Messages.registerMessages("inevo");
        ModEntities.init();
        MinecraftForge.EVENT_BUS.register(EnergyTickHandler.instance);
        MinecraftForge.EVENT_BUS.register(PlayerPropertyEvents.instance);
        CapabilityManager.INSTANCE.register(PlayerEnergy.class, new Capability.IStorage<PlayerEnergy>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<PlayerEnergy> capability, PlayerEnergy instance, EnumFacing side) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void readNBT(Capability<PlayerEnergy> capability, PlayerEnergy instance, EnumFacing side, NBTBase nbt) {
                throw new UnsupportedOperationException();
            }
        }, () -> null);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        //注册表
        // some example code
        proxy.init(event);
        proxy.registerParticles();
        RegisterTileEntity();
        RegistryHandler.initRegistries();
        ModBlocks.registerOres();
        if(Loader.isModLoaded("baubles")){
            //conarmConfig.setup();
        }


        //Messages.registerMessages("inevo");
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private static void RegisterTileEntity() {
        //GameRegistry.registerTileEntity(TileEntityDeBoomOrb.class, new ResourceLocation(MODID, "deboom_orb_basic"));
        GameRegistry.registerTileEntity(TileEntityEnergyStorage.class, new ResourceLocation(MODID, "energy_storage"));
        GameRegistry.registerTileEntity(TileEntityGlowstoneGenerator.class, new ResourceLocation(MODID, "glowstone_generator"));
        GameRegistry.registerTileEntity(TileEntityDirtCompressor.class, new ResourceLocation(MODID, "dirt_compressor"));
        GameRegistry.registerTileEntity(TileEntityFastFurnace.class, new ResourceLocation(MODID, "fast_furnace"));
    }


    public static void Log(String str) {
//        if (ModConfig.GeneralConf.LOG_ON)
//        {
        logger.info(str);
//        }
    }


//
//    public static PacketHandler packetHandler = new PacketHandler();



    public static void Log(String str, Object... args) {
//        if (ModConfig.GeneralConf.LOG_ON)
//        {
        logger.info(String.format(str, args));
//        }
        }
//    @SubscribeEvent
//    public void onEnergyTransferred(EnergyTransferEvent event) {
//        try {
//            packetHandler.sendToReceivers(new TransmitterUpdateMessage(PacketType.ENERGY, event.energyNetwork.firstTransmitter().coord(), event.power),
//                    event.energyNetwork.getPacketRange());
//        } catch (Exception ignored) {
//        }
//    }

}
