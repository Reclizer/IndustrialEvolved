package com.reclizer.inevo.proxy;

import com.google.common.collect.ImmutableMap;
import com.reclizer.inevo.client.particle.ParticleInevo;
import com.reclizer.inevo.entity.ModEntities;
import com.reclizer.inevo.player.PlayerPropertyEvents;
import com.reclizer.inevo.player.PlayerEnergy;
import com.reclizer.inevo.player.EnergyTickHandler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import com.google.common.util.concurrent.ListenableFuture;
import com.reclizer.inevo.network.Messages;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.animation.ITimeValue;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.model.animation.IAnimationStateMachine;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber
public class CommonProxy {
    private static final Map<ResourceLocation, ParticleInevo.IInevoParticleFactory> factories = new HashMap<>();

public void preInit(FMLPreInitializationEvent e){
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

    public boolean isServer()
    {
        return false;
    }



    public void registerItemRenderer(Item item, int meta, String id) {
        //Ignored
    }

    public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
        throw new IllegalStateException("This should only be called from client side");
    }

    public void init(FMLInitializationEvent e) {



        if(Loader.isModLoaded("baubles")){
            //conarmConfig.setup();
        }
    }

    public EntityPlayer getClientPlayer() {
        throw new IllegalStateException("This should only be called from client side");
    }

    @Nullable
    public IAnimationStateMachine load(ResourceLocation location, ImmutableMap<String, ITimeValue> parameters) {
        return null;
    }

    public void loadShader(EntityPlayer player, ResourceLocation shader){}

    /**
     * Gets the client-side world using {@code Minecraft.getMinecraft().world}. <b>Only to be called client side!</b>
     * Returns null on the server side.
     */

    public void playBlinkEffect(EntityPlayer player){}



//====================================================================================================
    //Á£×ÓÉú³ÉÆ÷

//    /** Called from init() in the main mod class to initialise the particle factories. */
//    public void registerParticles(){
//        ParticleInevo.registerParticle(ParticleBuilder.Type.BEAM, ParticleBeam::new);
//        ParticleInevo.registerParticle(ParticleBuilder.Type.DUST, ParticleDust::new);
//
//    } // Does nothing since particles are client-side only
//
//    /** Use {@link ParticleInevo#registerParticle(ResourceLocation, com.reclizer.inevo.client.particle.ParticleInevo.IInevoParticleFactory)}, this is internal. */
//    // I mean, it does exactly the same thing but I might want to make it do something else in future...
//    public static void addParticleFactory(ResourceLocation name, ParticleInevo.IInevoParticleFactory factory){
//        factories.put(name, factory);
//    }
//
//
//    public ParticleInevo createParticle(ResourceLocation type, World world, double x, double y, double z){
//        ParticleInevo.IInevoParticleFactory factory = factories.get(type);
//        if(factory == null){
//            IndustrialEvolved.logger.warn("Unrecognised particle type {} ! Ensure the particle is properly registered.", type);
//            return null;
//        }
//        return factory.createParticle(world, x, y, z);
//    }







    public void registerParticles(){} // Does nothing since particles are client-side only


    public com.reclizer.inevo.client.particle.ParticleInevo createParticle(ResourceLocation type, World world, double x, double y, double z){
        return null;
    }






//====================================================================================================
}
