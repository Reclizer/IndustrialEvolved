package com.reclizer.inevo.entity.ai;

import com.reclizer.inevo.entity.creature.EntityAlice;
import com.reclizer.inevo.item.ModItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemBow;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAliceAIRangeAttack extends EntityAIBase {
    private final EntityAlice entityHost;
    private final IRangedAttackMob rangedAttackEntityHost;
    private EntityLivingBase attackTarget;
    private int rangedAttackTime;
    private final double entityMoveSpeed;
    private int seeTime;
    private int attackTime = -1;
    private final int attackIntervalMin;
    private final int maxRangedAttackTime;
    private final float attackRadius;
    private final float maxAttackDistance;
    private final double moveSpeedAmp;
    private int attackCooldown;
    private int delayCounter;

    private boolean strafingClockwise;
    private boolean strafingBackwards;
    private int strafingTime = -1;

    public EntityAliceAIRangeAttack(IRangedAttackMob attacker, double movespeed, int maxAttackTime, float maxAttackDistanceIn)
    {
        this(attacker, movespeed, maxAttackTime, maxAttackTime, maxAttackDistanceIn);
    }

    public EntityAliceAIRangeAttack(IRangedAttackMob attacker, double movespeed, int p_i1650_4_, int maxAttackTime, float maxAttackDistanceIn)
    {
        this.rangedAttackTime = -1;
        this.moveSpeedAmp = movespeed;
        if (!(attacker instanceof EntityLivingBase))
        {
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
        }
        else
        {
            this.rangedAttackEntityHost = attacker;
            this.entityHost = (EntityAlice) attacker;
            this.entityMoveSpeed = movespeed;
            this.attackIntervalMin = p_i1650_4_;
            this.maxRangedAttackTime = maxAttackTime;
            this.attackRadius = maxAttackDistanceIn;
            this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
            this.setMutexBits(3);
        }
    }

    public boolean shouldExecute()
    {
        // ????????????????????????????????
        boolean canRangeAttack = this.isBowInMainhand();
        // ????????????????????????????????
        boolean canBulletAttack = this.isSwordInMainhand();
        // ??????????????????????????????????????????????????
        EntityLivingBase target = this.entityHost.getAttackTarget();

        boolean canAttack = target != null && target.isEntityAlive()
                && (canRangeAttack || canBulletAttack);

        return canAttack;

//        if(canAttack){
//            BlockPos pos=this.entityHost.getPosition();
//            double distance=target.getDistanceSq(pos);
//            //boolean canAttack1 = !target.onGround||distance>5D;
//            return !target.onGround;
//        }else {
//            return false;
//        }


    }
//=====================================================================================
    private boolean isBowInMainhand() {
        return this.entityHost.getHeldItemMainhand().getItem() instanceof ItemBow;
    }

    private boolean isSwordInMainhand() {
        return this.entityHost.getHeldItemMainhand().getItem() == ModItems.PHASE_SWORD;
    }

//=====================================================================================





    @Override
    public boolean shouldContinueExecuting()
    {
        //return this.shouldExecute() || !this.entityHost.getNavigator().noPath();]
        return this.shouldExecute();
    }

    @Override
    public void startExecuting() {

        this.entityHost.setSwingingArms(true);
    }


    public void resetTask()
    {
//        this.attackTarget = null;
//        this.seeTime = 0;
//        this.rangedAttackTime = -1;
        //============================
        this.seeTime = 0;
        this.attackTime = -1;
        this.entityHost.resetActiveHand();
        //this.entityHost.setSwingingArms(false);
        this.entityHost.getNavigator().clearPath();
        this.entityHost.setMoveForward(0.0F);
        this.entityHost.setMoveStrafing(0.0F);

    }

    public void updateTask()
    {
        // ????????????
        EntityLivingBase entitylivingbase = this.entityHost.getAttackTarget();


        // ??????????????????
        if (entitylivingbase != null) {

//            BlockPos pos=this.entityHost.getPosition();
//            double distance=entitylivingbase.getDistance(pos.getX(),pos.getY(),pos.getZ());
//            boolean canAttack = !entitylivingbase.onGround;


            //if(canAttack){
                // ????????????????????
                double distanceSq = this.entityHost.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
                // ????????????
                boolean canSee = this.entityHost.getEntitySenses().canSee(entitylivingbase);
                // ?????????????????? 0
                boolean seeTimeMoreThanZero = this.seeTime > 0;

                // ????????????????????????????
                if (canSee != seeTimeMoreThanZero) {
                    this.seeTime = 0;
                }

                // ??????????????????????????????????????
                if (canSee) {
                    ++this.seeTime;
                } else {
                    --this.seeTime;
                }

                // ????????????????????????????????????????????
                if (distanceSq <= this.maxAttackDistance && this.seeTime >= 20) {
                    // ????????????????????????????????????
                    this.entityHost.getNavigator().clearPath();
                    ++this.strafingTime;
                } else {
                    // ??????????????????????????????????????
                    this.entityHost.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.moveSpeedAmp);
                    this.strafingTime = -1;
                }

                // ??????????????????????????????????????????????????????
                if (this.strafingTime >= 20) {
                    if (this.entityHost.getRNG().nextFloat() < 0.3D) {
                        this.strafingClockwise = !this.strafingClockwise;
                    }

                    if (this.entityHost.getRNG().nextFloat() < 0.3D) {
                        this.strafingBackwards = !this.strafingBackwards;
                    }

                    this.strafingTime = 0;
                }

                // ???????????????? -1
                if (this.strafingTime > -1) {
                    // ????????????????????????????
                    if (distanceSq > this.maxAttackDistance * 0.75F) {
                        this.strafingBackwards = false;
                    } else if (distanceSq < this.maxAttackDistance * 0.25F) {
                        this.strafingBackwards = true;
                    }

                    // ????????
                    this.entityHost.getMoveHelper().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
                    this.entityHost.faceEntity(entitylivingbase, 30.0F, 30.0F);
                } else {
                    // ??????????????????
                    this.entityHost.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
                }

                // ????????????????????????
                if (this.entityHost.isHandActive()) {
                    // ?????????????????? 60??????????????
                    if (!canSee && this.seeTime < -60) {
                        this.entityHost.resetActiveHand();
                    } else if (canSee) {
                        // ????????????????????
                        int i = this.entityHost.getItemInUseMaxCount();

                        // ???????????????????? 20 ??????
                        // ??????????????????????????????????????????????????????????
                        // ???????????? 1 ??????????????
                        if (i >= 50) {
                            this.entityHost.resetActiveHand();
                            this.entityHost.attackEntityWithRangedAttack(entitylivingbase, ItemBow.getArrowVelocity(i));


                            this.attackTime = this.attackCooldown;

                        }
                    }
                } else if (--this.attackTime <= 0 && this.seeTime >= -60) {
                    // ??????????????????????????????????????
                    this.entityHost.setActiveHand(EnumHand.MAIN_HAND);
                }
            //}




        }

    }
}
