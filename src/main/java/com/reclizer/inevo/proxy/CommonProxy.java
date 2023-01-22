package com.reclizer.inevo.proxy;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.World;
import com.google.common.util.concurrent.ListenableFuture;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.animation.ITimeValue;
import net.minecraftforge.common.model.animation.IAnimationStateMachine;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


import javax.annotation.Nullable;


import static com.reclizer.inevo.IndustrialEvolved.MODID;

//@Mod.EventBusSubscriber
@SuppressWarnings("deprecation")
public class CommonProxy {


public void preInit(FMLPreInitializationEvent e){

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

    public void registerParticles(){} // Does nothing since particles are client-side only

    public com.reclizer.inevo.client.particle.ParticleInevo createParticle(ResourceLocation type, World world, double x, double y, double z){
        return null;
    }

//====================================================================================================
}
