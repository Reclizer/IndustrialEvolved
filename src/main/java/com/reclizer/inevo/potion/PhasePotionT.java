package com.reclizer.inevo.potion;

import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.item.ModItems;
import com.reclizer.inevo.player.PlayerEnergy;
import com.reclizer.inevo.player.PlayerProperties;
import com.reclizer.inevo.util.EntityUtils;
import com.reclizer.inevo.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
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

import java.util.Iterator;

import static com.reclizer.inevo.potion.PotionRegistryHandler.PHASE_POTION;
import static com.reclizer.inevo.potion.PotionRegistryHandler.PHASE_POTION_II;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class PhasePotionT extends Potion {
    public static final ResourceLocation SHADER = new ResourceLocation(IndustrialEvolved.MODID, "shaders/phase_space.json");

    protected static final ResourceLocation TEXTURE = new ResourceLocation("inevo","textures/gui/potions.png");
    protected final int iconIndex;
    public static int time;


    protected PhasePotionT() {
        super(false, 0*806144);
        this.setRegistryName(IndustrialEvolved.MODID+":phase_space_II");
        this.setPotionName("effect."+IndustrialEvolved.MODID+".phaseSpaceII");
        this.registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", 2.0D, 2);
        this.iconIndex = 0;

        //this.setBeneficial();
    }



    @Override
    public boolean hasStatusIcon() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    protected void render(int x, int y, float alpha) {
        Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buf = tessellator.getBuffer();
        buf.begin(7, DefaultVertexFormats.POSITION_TEX);
        GlStateManager.color(1, 1, 1, alpha);

        int textureX = iconIndex % 14 * 18;
        int textureY = 198 - iconIndex / 14 * 18;

        buf.pos(x, y + 18, 0).tex(textureX * 0.00390625, (textureY + 18) * 0.00390625).endVertex();
        buf.pos(x + 18, y + 18, 0).tex((textureX + 18) * 0.00390625, (textureY + 18) * 0.00390625).endVertex();
        buf.pos(x + 18, y, 0).tex((textureX + 18) * 0.00390625, textureY * 0.00390625).endVertex();
        buf.pos(x, y, 0).tex(textureX * 0.00390625, textureY * 0.00390625).endVertex();

        tessellator.draw();
    }

    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
        this.render(x + 6, y + 7, 1.0F);
    }

    @SideOnly(Side.CLIENT)
    public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
        this.render(x + 3, y + 3, alpha);
    }

//    @Override
//    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier){
//        super.performEffect(entityLivingBaseIn, amplifier);
//        if (this == PotionRegistryHandler.PHASE_POTION_II&&entityLivingBaseIn instanceof EntityPlayer)
//        {
//            EntityPlayer player = (EntityPlayer) entityLivingBaseIn;
//            PlayerEnergy playerEnergy = PlayerProperties.getPlayerEnergy(player);
//            if(playerEnergy.getSpaceEnergy()>0){
//                playerEnergy.setSpaceEnergy(playerEnergy.getSpaceEnergy()-1);
//            }else {
//                player.clearActivePotions();
//            }
//
//        }
//    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerAtk(LivingAttackEvent event) {
        if(event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if(player.getActivePotionEffect(PHASE_POTION_II)!= null){
                event.setCanceled(true);
                return;
            }



        }
    }



    @SubscribeEvent
    public static void onCreatureAtk(LivingHurtEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        if (!world.isRemote) {
            //EntityLivingBase hurtOne = evt.getEntityLiving();
            EntityLivingBase attacker = (EntityLivingBase) evt.getSource().getTrueSource();
            if (attacker != null&&attacker.isPotionActive(PHASE_POTION_II)) {
                attacker.clearActivePotions();
                return;
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerInteractEvent(PlayerInteractEvent event){
        // Prevents transient players from interacting with the world in any way
        if(event.isCancelable() && event.getEntityPlayer().isPotionActive(PHASE_POTION_II)){
            EntityPlayer player =event.getEntityPlayer();
            if(player.getHeldItem(EnumHand.MAIN_HAND).getItem() != ModItems.PHASEDEVICE
            &&player.getHeldItem(EnumHand.OFF_HAND).getItem() != ModItems.PHASEDEVICE){
                event.setCanceled(true);
            }


        }
    }



    @SubscribeEvent
    public static void onCreatureJump(LivingEvent.LivingJumpEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        EntityLivingBase target = evt.getEntityLiving();
        if (target.getActivePotionEffect(PHASE_POTION_II) != null) {
            target.motionY+=(double)((float)(2) * 0.1F);
            target.isAirBorne = true;
            return;
        }

    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerVis(PlayerEvent.Visibility event){
        EntityPlayer player = event.getEntityPlayer();
        if (player.getActivePotionEffect(PHASE_POTION_II) != null) {
            event.modifyVisibility(0.01D);

        }

    }



    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerRender(RenderPlayerEvent.Pre event){
        EntityPlayer player = event.getEntityPlayer();

        if (player.getActivePotionEffect(PHASE_POTION_II) != null) {
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
        time++;
        if(time<25){
            return;
        }
        EntityPlayer player =event.player;
        if(player.isPotionActive(PHASE_POTION_II)){
            PlayerEnergy playerEnergy = PlayerProperties.getPlayerEnergy(player);
            if(playerEnergy.getSpaceEnergy()>0){
                playerEnergy.setSpaceEnergy(playerEnergy.getSpaceEnergy()-1);
                time=0;
            }else {
                EntityUtils.attackEntityWithoutKnockback(player, DamageSource.OUT_OF_WORLD, player.getMaxHealth()/5);
                //player.clearActivePotions();
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
