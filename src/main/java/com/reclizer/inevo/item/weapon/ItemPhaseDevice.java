package com.reclizer.inevo.item.weapon;

import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.entity.construct.EntityFloatingCannon;
import com.reclizer.inevo.item.ItemEnergyBase;

import com.reclizer.inevo.item.ModItems;
import com.reclizer.inevo.player.PlayerProperties;
import com.reclizer.inevo.player.PlayerEnergy;
import com.reclizer.inevo.potion.PotionRegistryHandler;
import com.reclizer.inevo.tools.RayTraceTools;
import com.reclizer.inevo.util.ParticleBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static com.reclizer.inevo.potion.PotionRegistryHandler.PHASE_POTION_II;

public class ItemPhaseDevice extends ItemEnergyBase {
    private int chargeTime;
    private final int maxCharge=400;
    private  boolean portal;
    public ItemPhaseDevice(double maxElectricity, String name) {
        super(maxElectricity, name);

        this.addPropertyOverride(new ResourceLocation(IndustrialEvolved.MODID, "phase_device_state"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity)
            {
                boolean isHyperState = getHyperState(stack);
                float hyperState = getHyperValue(stack);
                int chargeValue =getChargeValue(stack);

                if(entity==null){
                    return 0.0f;
                }

               if(isHyperState){
                   //if(chargeValue%100==0) {
                       int i = chargeValue<300 ? 0:chargeValue<600 ?1:chargeValue<900 ?2:chargeValue<1200 ? 3:chargeValue<1500 ?4:0;
                       switch (i) {
                           case 0:
                               hyperState = 0.1f;
                               break;
                           case 1:
                               hyperState = 0.2f;
                               break;
                           case 2:
                               hyperState = 0.3f;
                               break;
                           case 3:
                               hyperState = 0.4f;
                               break;
                           case 4:
                               hyperState = 0.5f;
                               break;
                       }
                   //}
                    setHyperValue(stack, hyperState);
                    return hyperState;
                }



                return 0.0f;
            }
        });
    }
    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int something, boolean somethingelse) {
        if(getEnergy(stack)<100){
            setHyperState(stack,false);
            return;
        }
        if(!world.isRemote&&getHyperState(stack)) {

            int chargeValue =getChargeValue(stack);
            if(chargeValue<1200){
                setChargeValue(stack,chargeValue+1);
            }else {
                setPortalState(stack,true);
            }
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
//        if(getHyperState(newStack)){
//            return false;
//        }else {
//            return true;
//        }
        //return !oldStack.equals(newStack); //!ItemStack.areItemStacksEqual(oldStack, newStack);
        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack item = player.getHeldItem(hand);
        // ²¥·ÅÓÒ¼üµÄÉùÒô

        if(getEnergy(item)<100){
            return new ActionResult<>(EnumActionResult.FAIL, item);
        }


        if(player.isSneaking()){
            boolean isHyperState = getHyperState(item);
            isHyperState = !isHyperState;
            setHyperState(item, isHyperState);
            return new ActionResult<>(EnumActionResult.FAIL, item);
        }


        if (!world.isRemote) {

            if(!getHyperState(item)){
                if(getEnergy(item) > 0){
                    world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
                    player.addPotionEffect(new PotionEffect(PotionRegistryHandler.PHASE_POTION_II, 25, 0));
                    if (!player.capabilities.isCreativeMode) {
                        setEnergy(item, getEnergy(item) - 100);
                    }
                    player.getCooldownTracker().setCooldown(this, 60);
                }
            }else {
                if(getPortalState(item)){
                    if(player.isPotionActive(PHASE_POTION_II)){
                        player.clearActivePotions();
                        return new ActionResult<>(EnumActionResult.FAIL, item);
                    }
                    world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
                    player.addPotionEffect(new PotionEffect(PotionRegistryHandler.PHASE_POTION_II, 1000, 0));
                    setPortalState(item,false);
                    setChargeValue(item,0);
                    setHyperValue(item,0.1f);
                    return new ActionResult<>(EnumActionResult.FAIL, item);
                }
            }

        }






//        if(!world.isRemote){
//            PlayerEnergy playerEnergy = PlayerProperties.getPlayerSummoned(player);
//
//            if(playerEnergy.getSpaceEnergy()>= 10){
//                playerEnergy.setSpaceEnergy(playerEnergy.getSpaceEnergy()-10);
//                EntityFloatingCannon floatingCannon = new EntityFloatingCannon(world);
//                floatingCannon.setPosition(player.posX, player.posY+3, player.posZ);
//                floatingCannon.setMaster(player);
//                world.spawnEntity(floatingCannon);
//            }
//
//        }

















//        RayTraceTools.Beam beam = new RayTraceTools.Beam(world, player, 10);
//        Vec3d start = beam.getStart();
//        Vec3d end =beam.getEnd();
//        Vec3d lookVec = beam.getLookVec();
//
//        double accelX = lookVec.x * 1.0D;
//        double accelY = lookVec.y * 1.0D;
//        double accelZ = lookVec.z * 1.0D;
//
//        if(world.isRemote){
//            ParticleBuilder.create(ParticleBuilder.Type.BEAM).clr(0.2f, 0.6f, 1).time(4).pos(start)
//                    .target(end).spawn(world);
//        }









        return new ActionResult<>(EnumActionResult.FAIL, item);
    }



    @Override
    public int getMaxItemUseDuration(ItemStack itemstack) {
        return 72000;
    }


//=================================================================================================
    private void setHyperState(ItemStack stack, boolean state) {stack.getItem().getNBTShareTag(stack).setBoolean("isHyperState", state);}

    private boolean getHyperState(ItemStack stack)
    {
        boolean value = false;
        try {
            value = stack.getItem().getNBTShareTag(stack).getBoolean("isHyperState");
        } catch (Exception expt) {}
        return value;
    }

    private void setHyperValue(ItemStack stack, float value) {stack.getItem().getNBTShareTag(stack).setFloat("HyperValue", value);}
    private float getHyperValue(ItemStack stack)
    {
        float value = 0.0f;
        try {
            value = stack.getItem().getNBTShareTag(stack).getFloat("HyperValue");
        } catch (Exception expt) {}
        return value;
    }

    private void setPortalState(ItemStack stack, boolean state) {stack.getItem().getNBTShareTag(stack).setBoolean("isPortalState", state);}

    private boolean getPortalState(ItemStack stack)
    {
        boolean value = false;
        try {
            value = stack.getItem().getNBTShareTag(stack).getBoolean("isPortalState");
        } catch (Exception expt) {}
        return value;
    }

    private void setChargeValue(ItemStack stack, int value) {stack.getItem().getNBTShareTag(stack).setInteger("chargeValue", value);}
    private int getChargeValue(ItemStack stack)
    {
        int value = 0;
        try {
            value = stack.getItem().getNBTShareTag(stack).getInteger("chargeValue");
        } catch (Exception expt) {}
        return value;
    }

}