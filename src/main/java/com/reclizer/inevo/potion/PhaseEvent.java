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




//    @SubscribeEvent(priority = EventPriority.HIGHEST)
//    public static void onCreatureKB(LivingKnockBackEvent evt) {
//        World world = evt.getEntity().getEntityWorld();
//        EntityLivingBase hurtOne = evt.getEntityLiving();
//        if (!world.isRemote) {
//
//            //KB Reduction
//            if (evt.isCanceled()) {
//                return;
//            }
//            if (hurtOne.getActivePotionEffect(PHASE_POTION) != null||hurtOne.getActivePotionEffect(PHASE_POTION_II) != null) {
//                evt.setStrength(0 * evt.getStrength());
//                return;
//            }
//
//
//        }
//    }






}