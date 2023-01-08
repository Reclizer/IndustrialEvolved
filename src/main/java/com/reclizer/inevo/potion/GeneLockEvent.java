package com.reclizer.inevo.potion;

import com.reclizer.inevo.item.ModItems;
import com.reclizer.inevo.item.armor.ArmorState;
import com.reclizer.inevo.item.armor.ArmorUtils;
import com.reclizer.inevo.util.Reference;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.reclizer.inevo.potion.PotionRegistryHandler.*;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class GeneLockEvent {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onCreatureHurt(LivingHurtEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        if (!world.isRemote) {
            EntityLivingBase hurtOne = evt.getEntityLiving();
            DamageSource damageSource =evt.getSource();
            //EntityLivingBase attacker = (EntityLivingBase) evt.getSource().getTrueSource();
            if (hurtOne.getActivePotionEffect(GENE_LOCK_POTION) != null && !"outOfWorld".equals(damageSource.getDamageType())) {
                evt.setCanceled(true);
                return;
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerHeal(LivingHealEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        if (!world.isRemote) {
            EntityLivingBase hurtOne = evt.getEntityLiving();
            //EntityLivingBase attacker = (EntityLivingBase) evt.getSource().getTrueSource();
            if (hurtOne.getActivePotionEffect(GENE_LOCK_POTION) != null) {
                evt.setCanceled(true);
                return;
            }
        }
    }



    @SubscribeEvent
    public static void onPotionRemoveEvent(PotionEvent.PotionExpiryEvent event){
        EntityLivingBase hurtOne = event.getEntityLiving();
        if(event.getPotionEffect().getPotion()!=null&&event.getPotionEffect().getPotion() == GENE_LOCK_POTION){
            if (!hurtOne.isDead && !hurtOne.world.isRemote)
            {
                hurtOne.attackEntityFrom(DamageSource.OUT_OF_WORLD, 100.0F);
                //hurtOne.setHealth(0f);
                return;
            }

        }
    }









}
