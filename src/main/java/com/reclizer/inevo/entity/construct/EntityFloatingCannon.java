package com.reclizer.inevo.entity.construct;

import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.client.audio.InevoSounds;
import com.reclizer.inevo.entity.ai.*;
import com.reclizer.inevo.player.PlayerProperties;
import com.reclizer.inevo.player.PlayerSummoned;
import com.reclizer.inevo.tools.RayTraceToolsLiving;
import com.reclizer.inevo.util.AllyDesignationSystem;
import com.reclizer.inevo.util.BlockUtils;
import com.reclizer.inevo.util.EntityUtils;
import com.reclizer.inevo.util.ParticleBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.List;
import java.util.stream.Collectors;

public class EntityFloatingCannon extends EntityEnergyConstruct implements IRangedAttackMob{


    private int attackTimer;
    private int particleTime;
    private int timeToRecalcPath;

    public EntityFloatingCannon(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 0.4F);
        //this.experienceValue = 5;
    }

    @Override
    protected void initEntityAI() {

        this.targetTasks.addTask(1, new EntityAIMasterHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIMasterHurtTarget(this));


    }


    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();



        double radius =5;

        List<EntityLivingBase> nearby = EntityUtils.getLivingWithinRadius(radius, posX, posY, posZ, world);
        nearby.sort(Comparator.comparingDouble(e -> e.getDistanceSq(this)));



        int targetsRemaining = 1;
        particleTime++;

            while(!nearby.isEmpty() && targetsRemaining > 0){

                EntityLivingBase target = nearby.remove(0);

                if(EntityUtils.isLiving(target)&& isValidTarget(target)){

//                    if(target.ticksExisted % target.maxHurtResistantTime == 1){
//
//                        float damage = 10f;
//
//                        EntityUtils.attackEntityWithoutKnockback(target, DamageSource.FALL, damage);
//                    }
                    if(particleTime>19){

                        target.setHealth(target.getHealth()>5 ?   target.getHealth()-1:target.getHealth());

                        this.playSound(InevoSounds.ENTITY_LASER, 0.3F + this.rand.nextFloat() / 4,
                                this.rand.nextFloat() * 0.7F + 1.4F);


//                        if(this.getMaster()!=null&&this.getMaster() instanceof EntityPlayer){
//                            EntityPlayer player = (EntityPlayer) this.getMaster();
//                            EntityUtils.attackEntityWithoutKnockback(target, DamageSource.causePlayerDamage(player), 5);
//                        }else {
                            EntityUtils.attackEntityWithoutKnockback(target, DamageSource.causeMobDamage(this), 5);
                        //}

                    }


                    targetsRemaining--;
                    //0.2f, 0.6f, 1
                    if(particleTime>20){

                    if(world.isRemote){
//                    ParticleBuilder.create(ParticleBuilder.Type.BEAM).pos(this.getPositionVector().addVector(0, height/2, 0))
//                            .target(target).clr(0.2f, 0.6f, 1f).spawn(world);
                        ParticleBuilder.create(ParticleBuilder.Type.BEAM).clr(0.2f, 0.6f, 1).time(4).pos(this.getPositionVector().addVector(0, height/2, 0))
                                .target(target).spawn(world);
                    }
                        particleTime=0;
                }
            }

        }


        if (!this.world.isRemote)
        {

            // Makes the phoenix hover.

            EntityLivingBase attTarget= this.getAttackTarget();

            EntityLivingBase entity = this.getMaster();

//            if(this.isDead&&this.getMaster()!=null&&this.getMaster() instanceof EntityPlayer){
//                EntityPlayer master = (EntityPlayer) this.getMaster();
//                PlayerSummoned playerSummoned = PlayerProperties.getPlayerSummoned(master);
//                playerSummoned.setSummoned(playerSummoned.getSummoned()>0 ? playerSummoned.getSummoned()-1:playerSummoned.getSummoned());
//            }

            if(attTarget!=null){
                entity=attTarget;
            }

            if (entity != null)
            {
                Integer floorLevel = BlockUtils.getNearestFloor(world, new BlockPos(entity), 4);

                if(entity.onGround){
                    if(floorLevel == null || this.posY - floorLevel > 3){
                        this.motionY = -0.1;
                    }else if(this.posY - floorLevel < 2){
                        this.motionY = 0.1;
                    }else{
                        this.motionY = 0.0;
                    }
                }else {
                    if (this.posY < entity.posY+3 )
                    {
                        if (this.motionY < 0.0D)
                        {
                            this.motionY = 0.0D;
                        }

                        this.motionY += (0.5D - this.motionY) * 0.6000000238418579D;
                    }
                }







                double d0 = entity.posX - this.posX;
                double d1 = entity.posZ - this.posZ;
                double d3 = d0 * d0 + d1 * d1;

                if (d3 > 9.0D)
                {
                    double d5 = (double) MathHelper.sqrt(d3);
                    this.motionX += (d0 / d5 * 0.5D - this.motionX) * 0.6000000238418579D;
                    this.motionZ += (d1 / d5 * 0.5D - this.motionZ) * 0.6000000238418579D;
                }
            }
        }





    }

    public boolean isValidTarget(Entity target){
        return AllyDesignationSystem.isValidTarget(this.getMaster(), target);
    }






//    @Nullable
//    public EntityLivingBase getCaster(){ // Kept despite the above method because it returns an EntityLivingBase
//
//        Entity entity = EntityUtils.getEntityByUUID(world, getOwnerId());
//
//        if(entity != null && !(entity instanceof EntityLivingBase)){ // Should never happen
//            IndustrialEvolved.logger.warn("{} has a non-living owner!", this);
//            entity = null;
//        }
//
//        return (EntityLivingBase)entity;
//    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(5.0D);//����
        //this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(50.0D);
    }

    @Override//��ˤ���˺�����
    public void fall(float distance, float damageMultiplier){}


    @Override
    public float getEyeHeight() {
        return 0.2F;
    }


    //=====================================================================================================


    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {

    }
}