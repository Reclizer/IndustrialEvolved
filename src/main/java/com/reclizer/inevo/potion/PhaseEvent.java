package com.reclizer.inevo.potion;

import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.reclizer.inevo.potion.PotionRegistryHandler.*;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class PhaseEvent {
    public static final ResourceLocation SHADER = new ResourceLocation(IndustrialEvolved.MODID, "shaders/phase_space.json");

/*
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onCreatureHurt(LivingHurtEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        if (!world.isRemote) {
            EntityLivingBase hurtOne = evt.getEntityLiving();
            EntityLivingBase attacker = (EntityLivingBase) evt.getSource().getTrueSource();
            if (hurtOne.getActivePotionEffect(PHASE_POTION) != null) {

                if (attacker != null&&attacker.isPotionActive(PHASE_POTION)) {
                    return;
                }else{
                    evt.setCanceled(true);
                    return;
                }

            }
        }
    }

 */




    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerAtk(LivingAttackEvent event) {
        if(event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if(player.getActivePotionEffect(PHASE_POTION)!= null){
                event.setCanceled(true);
                return;
            }
            if(player.getActivePotionEffect(PHASE_POTION_II)!= null){
                event.setCanceled(true);
                return;
            }



        }
    }


//    @SubscribeEvent
//    public static void onEntityTarget(LivingSetAttackTargetEvent evt) {
//        EntityLivingBase attacker = evt.getEntityLiving();
//        EntityLivingBase target=evt.getTarget();
//        if(target.isPotionActive(PHASE_POTION)){
//            attacker.
//        }
//    }

    @SubscribeEvent
    public static void onCreatureAtk(LivingHurtEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        if (!world.isRemote&&evt.getEntity() instanceof EntityPlayer) {
            //EntityLivingBase hurtOne = evt.getEntityLiving();
            EntityLivingBase attacker = (EntityLivingBase) evt.getSource().getTrueSource();
            if (attacker != null&&attacker.isPotionActive(PHASE_POTION)) {
                attacker.clearActivePotions();
                //attacker.setEntityInvulnerable(false);
                return;
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerInteractEvent(PlayerInteractEvent event){
        // Prevents transient players from interacting with the world in any way
        if(event.isCancelable() && event.getEntityPlayer().isPotionActive(PHASE_POTION_II)){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onCreatureKB(LivingKnockBackEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        EntityLivingBase hurtOne = evt.getEntityLiving();
        if (!world.isRemote) {

            //KB Reduction
            if (evt.isCanceled()) {
                return;
            }
            if (hurtOne.getActivePotionEffect(PHASE_POTION) != null||hurtOne.getActivePotionEffect(PHASE_POTION_II) != null) {
                evt.setStrength(0 * evt.getStrength());
                return;
            }


        }
    }

    @SubscribeEvent
    public static void onCreatureJump(LivingEvent.LivingJumpEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        EntityLivingBase target = evt.getEntityLiving();
        if (target.getActivePotionEffect(PHASE_POTION) != null) {
            target.motionY+=(double)((float)(5 + 1) * 0.1F);
            target.isAirBorne = true;
            return;
        }
        if (target.getActivePotionEffect(PHASE_POTION_II) != null) {
            target.motionY+=(double)((float)(2) * 0.1F);
            target.isAirBorne = true;
            return;
        }

    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerVis(PlayerEvent.Visibility event){
        EntityPlayer player = event.getEntityPlayer();
//        if(ArmorUtils.fullEquipped(player)&&player.getHeldItemMainhand().getItem() == ModItems.TRANSCEND_SWORD){
//            event.setCanceled(true);
//        }
        if (player.getActivePotionEffect(PHASE_POTION) != null||player.getActivePotionEffect(PHASE_POTION_II) != null) {
            event.modifyVisibility(0.01D);

        }

    }



    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerRender(RenderPlayerEvent.Pre event){
        EntityPlayer player = event.getEntityPlayer();
//        if(ArmorUtils.fullEquipped(player)&&player.getHeldItemMainhand().getItem() == ModItems.TRANSCEND_SWORD){
//            event.setCanceled(true);
//        }
        if (player.getActivePotionEffect(PHASE_POTION_II) != null||player.getActivePotionEffect(PHASE_POTION) != null) {
            event.setCanceled(true);


        }

    }

    @SubscribeEvent
    public static void onPotionAddedEvent(PotionEvent.PotionAddedEvent event){
        if(event.getEntity().world.isRemote && event.getPotionEffect().getPotion() == PHASE_POTION_II
                && event.getEntity() instanceof EntityPlayer){
            IndustrialEvolved.proxy.loadShader((EntityPlayer)event.getEntity(), SHADER);
            IndustrialEvolved.proxy.playBlinkEffect((EntityPlayer)event.getEntity());
        }
    }


    @SubscribeEvent
    public static void onPlayerTickEvent(TickEvent.PlayerTickEvent event){


        if(event.player == Minecraft.getMinecraft().player && event.phase == TickEvent.Phase.END){

            if(Minecraft.getMinecraft().entityRenderer.getShaderGroup() != null){ // IntelliJ is wrong, this can be null

                String activeShader = Minecraft.getMinecraft().entityRenderer.getShaderGroup().getShaderGroupName();

                if((activeShader.equals(SHADER.toString()) && !Minecraft.getMinecraft().player.isPotionActive(PHASE_POTION_II))){
                    Minecraft.getMinecraft().entityRenderer.stopUseShader();
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void onPotionRemoveEvent(PotionEvent.PotionExpiryEvent event){
        if(event.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            if (event.getPotionEffect() != null && event.getPotionEffect().getPotion() == PHASE_POTION_II) {
                player.clearActivePotions();
            }
        }
    }







}