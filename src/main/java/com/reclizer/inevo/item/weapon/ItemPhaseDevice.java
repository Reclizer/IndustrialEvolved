package com.reclizer.inevo.item.weapon;

import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.entity.construct.EntityFloatingCannon;
import com.reclizer.inevo.item.ItemEnergyBase;

import com.reclizer.inevo.player.PlayerProperties;
import com.reclizer.inevo.player.PlayerEnergy;
import com.reclizer.inevo.tools.RayTraceTools;
import com.reclizer.inevo.util.ParticleBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ItemPhaseDevice extends ItemEnergyBase {

    public ItemPhaseDevice(double maxElectricity, String name) {
        super(maxElectricity, name);

        this.addPropertyOverride(new ResourceLocation(IndustrialEvolved.MODID, "phase_device_state"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity)
            {
                boolean isHyperState = getHyperState(stack);
                float hyperState = getHyperValue(stack);
                if(isHyperState)
                {
                    if(hyperState <= 0.71f)
                    {
                        hyperState += 0.002f;
                    }
                    else
                    {
                        hyperState = 0.1f;
                    }
                    setHyperValue(stack, hyperState);
                    return hyperState;
                }
                return 0.0f;
            }
        });
    }

//    @Override
//    public void onUpdate(ItemStack stack, World world, Entity entity, int something, boolean somethingelse) {
//        if(!world.isRemote) {
//            int meta = stack.getItemDamage();
//            int newMeta = isActivated ? 1 : 0;
//            if(meta != newMeta)
//                stack.setItemDamage(newMeta);
//        }
//    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack item = player.getHeldItem(hand);
        // ²¥·ÅÓÒ¼üµÄÉùÒô
        world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        if(player.isSneaking()){
            boolean isHyperState = getHyperState(item);
            isHyperState = !isHyperState;
            setHyperState(item, isHyperState);
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








//        if (!world.isRemote) {
//            if(getEnergy(item) > 0){
//                player.addPotionEffect(new PotionEffect(PotionRegistryHandler.PHASE_POTION_II, 25, 0));
//                if (!player.capabilities.isCreativeMode) {
//                    setEnergy(item, getEnergy(item) - 1000);
//                }
//
//                player.getCooldownTracker().setCooldown(this, 60);
//            }
//
//
//        }
        return new ActionResult<>(EnumActionResult.SUCCESS, item);
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

}