package com.reclizer.inevo.proxy;

import com.google.common.util.concurrent.ListenableFuture;
import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.client.particle.*;
import com.reclizer.inevo.client.player.OverlayRenderer;
import com.reclizer.inevo.entity.ModEntities;
import com.reclizer.inevo.input.KeyBindings;
import com.reclizer.inevo.input.KeyInputHandler;
import com.reclizer.inevo.potion.RenderBlinkEffect;
import com.reclizer.inevo.util.ParticleBuilder;
import com.reclizer.inevo.util.ParticleBuilder.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    private static final Map<ResourceLocation, ParticleInevo.IInevoParticleFactory> factories = new HashMap<>();

    public static final List<KeyBinding> KEY_BINDINGS = new ArrayList<KeyBinding>();
    //public static final KeyBinding CAST_MAINHAND = new ModKeyBinding("activate_skill_mainhand", KeyConflictContext.IN_GAME, KeyModifier.NONE, Keyboard.KEY_R, "key.category.idlframewok");
    //public static final KeyBinding CAST_OFFHAND = new ModKeyBinding("activate_skill_offhand", KeyConflictContext.IN_GAME, KeyModifier.NONE, Keyboard.KEY_GRAVE, "key.category.idlframewok");

    public boolean isServer()
    {
        return false;
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        //×¢²á¿Í»§¶Ë²ÄÖÊ
        ModEntities.initModels();
    }
    public void registerItemRenderer(Item item, int meta, String id)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }
    @Override
    public void preInit(FMLPreInitializationEvent e){
        super.preInit(e);
        MinecraftForge.EVENT_BUS.register(OverlayRenderer.instance);
    }

    @Override
    public void init(FMLInitializationEvent e) {

        super.init(e);
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
        KeyBindings.init();
    }

    @Override
    public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
        return Minecraft.getMinecraft().addScheduledTask(runnableToSchedule);
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().player;
    }

    @Override
    public void loadShader(EntityPlayer player, ResourceLocation shader){
        if(Minecraft.getMinecraft().player == player
                && !Minecraft.getMinecraft().entityRenderer.isShaderActive())
            Minecraft.getMinecraft().entityRenderer.loadShader(shader);
    }



    @Override
    public void playBlinkEffect(EntityPlayer player){
        if(Minecraft.getMinecraft().player == player) RenderBlinkEffect.playBlinkEffect();
    }

//===============================================================================================================
    @Override
    public void registerParticles(){
        // I'll be a good programmer and use the API method rather than the one above. Lead by example, as they say...
        ParticleInevo.registerParticle(Type.BEAM, ParticleBeam::new);
        ParticleInevo.registerParticle(Type.DUST, ParticleDust::new);
        ParticleInevo.registerParticle(Type.SPARKLE, ParticleSparkle::new);
        ParticleInevo.registerParticle(Type.CLOUD, ParticleCloud::new);

    }

    /** Use {@link ParticleInevo#registerParticle(ResourceLocation, com.reclizer.inevo.client.particle.ParticleInevo.IInevoParticleFactory)}, this is internal. */
    // I mean, it does exactly the same thing but I might want to make it do something else in future...
    public static void addParticleFactory(ResourceLocation name, ParticleInevo.IInevoParticleFactory factory){
        factories.put(name, factory);
    }

    @Override
    public ParticleInevo createParticle(ResourceLocation type, World world, double x, double y, double z){
        ParticleInevo.IInevoParticleFactory factory = factories.get(type);
        if(factory == null){
            IndustrialEvolved.logger.warn("Unrecognised particle type {} ! Ensure the particle is properly registered.", type);
            return null;
        }
        return factory.createParticle(world, x, y, z);
    }



    //===============================================================================================================
}
