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
        // 能够远程攻击：模式正确、主手持弓
        boolean canRangeAttack = this.isBowInMainhand();
        // 能够弹幕攻击：模式正确、主手持剑
        boolean canBulletAttack = this.isSwordInMainhand();
        // 能够处理攻击：攻击目标不为空、上述两者攻击存在一个
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
        // 获取攻击目标
        EntityLivingBase entitylivingbase = this.entityHost.getAttackTarget();


        // 如果攻击目标不为空
        if (entitylivingbase != null) {

//            BlockPos pos=this.entityHost.getPosition();
//            double distance=entitylivingbase.getDistance(pos.getX(),pos.getY(),pos.getZ());
//            boolean canAttack = !entitylivingbase.onGround;


            //if(canAttack){
                // 到攻击目标的底部距离
                double distanceSq = this.entityHost.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
                // 能否看见对方
                boolean canSee = this.entityHost.getEntitySenses().canSee(entitylivingbase);
                // 看见的时长是否大于 0
                boolean seeTimeMoreThanZero = this.seeTime > 0;

                // 如果两者不一致，重置看见时间
                if (canSee != seeTimeMoreThanZero) {
                    this.seeTime = 0;
                }

                // 如果看见了对方，增加看见时间，否则减少
                if (canSee) {
                    ++this.seeTime;
                } else {
                    --this.seeTime;
                }

                // 如果在最大攻击距离之内，而且看见的时长足够长
                if (distanceSq <= this.maxAttackDistance && this.seeTime >= 20) {
                    // 清除先前的寻路路径，开始增加攻击时间
                    this.entityHost.getNavigator().clearPath();
                    ++this.strafingTime;
                } else {
                    // 否则就尝试移动到对方那边，攻击时间重置
                    this.entityHost.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.moveSpeedAmp);
                    this.strafingTime = -1;
                }

                // 如果攻击时间也足够长，随机对走位方向和前后走位进行反转
                if (this.strafingTime >= 20) {
                    if (this.entityHost.getRNG().nextFloat() < 0.3D) {
                        this.strafingClockwise = !this.strafingClockwise;
                    }

                    if (this.entityHost.getRNG().nextFloat() < 0.3D) {
                        this.strafingBackwards = !this.strafingBackwards;
                    }

                    this.strafingTime = 0;
                }

                // 如果攻击时间大于 -1
                if (this.strafingTime > -1) {
                    // 依据距离远近决定是否前后走位
                    if (distanceSq > this.maxAttackDistance * 0.75F) {
                        this.strafingBackwards = false;
                    } else if (distanceSq < this.maxAttackDistance * 0.25F) {
                        this.strafingBackwards = true;
                    }

                    // 应用走位
                    this.entityHost.getMoveHelper().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
                    this.entityHost.faceEntity(entitylivingbase, 30.0F, 30.0F);
                } else {
                    // 否则只朝向攻击目标
                    this.entityHost.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
                }

                // 如果实体手部处于激活状态
                if (this.entityHost.isHandActive()) {
                    // 如果看不见对方超时 60，重置激活状态
                    if (!canSee && this.seeTime < -60) {
                        this.entityHost.resetActiveHand();
                    } else if (canSee) {
                        // 否则开始进行远程攻击
                        int i = this.entityHost.getItemInUseMaxCount();

                        // 物品最大使用计数大于 20 才可以
                        // 这里大致解释下计数的意思，也就是蓄力，蓄力越长自然射的越远
                        // 只有蓄力超过 1 秒才会进行发射
                        if (i >= 50) {
                            this.entityHost.resetActiveHand();
                            this.entityHost.attackEntityWithRangedAttack(entitylivingbase, ItemBow.getArrowVelocity(i));


                            this.attackTime = this.attackCooldown;

                        }
                    }
                } else if (--this.attackTime <= 0 && this.seeTime >= -60) {
                    // 非激活状态，但是时长合适，开始激活手部
                    this.entityHost.setActiveHand(EnumHand.MAIN_HAND);
                }
            //}




        }

    }
}
