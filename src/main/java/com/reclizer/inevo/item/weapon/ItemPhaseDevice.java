package com.reclizer.inevo.item.weapon;

import com.reclizer.inevo.entity.construct.EntityFloatingCannon;
import com.reclizer.inevo.item.ItemEnergyBase;

import com.reclizer.inevo.player.PlayerProperties;
import com.reclizer.inevo.player.PlayerEnergy;
import com.reclizer.inevo.tools.RayTraceTools;
import com.reclizer.inevo.util.ParticleBuilder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemPhaseDevice extends ItemEnergyBase {
    public ItemPhaseDevice(double maxElectricity, String name) {
        super(maxElectricity, name);
        setFull3D();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack item = player.getHeldItem(hand);
        // ²¥·ÅÓÒ¼üµÄÉùÒô
        world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));


        if(!world.isRemote){
            PlayerEnergy playerEnergy = PlayerProperties.getPlayerSummoned(player);

            if(playerEnergy.getSpaceEnergy()>= 10){
                playerEnergy.setSpaceEnergy(playerEnergy.getSpaceEnergy()-10);
                EntityFloatingCannon floatingCannon = new EntityFloatingCannon(world);
                floatingCannon.setPosition(player.posX, player.posY+3, player.posZ);
                floatingCannon.setMaster(player);
                world.spawnEntity(floatingCannon);
            }

        }

















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
}


