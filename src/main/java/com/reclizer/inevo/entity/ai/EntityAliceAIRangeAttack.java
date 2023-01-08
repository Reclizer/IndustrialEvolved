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
        // �ܹ�Զ�̹�����ģʽ��ȷ�����ֳֹ�
        boolean canRangeAttack = this.isBowInMainhand();
        // �ܹ���Ļ������ģʽ��ȷ�����ֳֽ�
        boolean canBulletAttack = this.isSwordInMainhand();
        // �ܹ�������������Ŀ�겻Ϊ�ա��������߹�������һ��
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
        // ��ȡ����Ŀ��
        EntityLivingBase entitylivingbase = this.entityHost.getAttackTarget();


        // �������Ŀ�겻Ϊ��
        if (entitylivingbase != null) {

//            BlockPos pos=this.entityHost.getPosition();
//            double distance=entitylivingbase.getDistance(pos.getX(),pos.getY(),pos.getZ());
//            boolean canAttack = !entitylivingbase.onGround;


            //if(canAttack){
                // ������Ŀ��ĵײ�����
                double distanceSq = this.entityHost.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
                // �ܷ񿴼��Է�
                boolean canSee = this.entityHost.getEntitySenses().canSee(entitylivingbase);
                // ������ʱ���Ƿ���� 0
                boolean seeTimeMoreThanZero = this.seeTime > 0;

                // ������߲�һ�£����ÿ���ʱ��
                if (canSee != seeTimeMoreThanZero) {
                    this.seeTime = 0;
                }

                // ��������˶Է������ӿ���ʱ�䣬�������
                if (canSee) {
                    ++this.seeTime;
                } else {
                    --this.seeTime;
                }

                // �������󹥻�����֮�ڣ����ҿ�����ʱ���㹻��
                if (distanceSq <= this.maxAttackDistance && this.seeTime >= 20) {
                    // �����ǰ��Ѱ··������ʼ���ӹ���ʱ��
                    this.entityHost.getNavigator().clearPath();
                    ++this.strafingTime;
                } else {
                    // ����ͳ����ƶ����Է��Ǳߣ�����ʱ������
                    this.entityHost.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.moveSpeedAmp);
                    this.strafingTime = -1;
                }

                // �������ʱ��Ҳ�㹻�����������λ�����ǰ����λ���з�ת
                if (this.strafingTime >= 20) {
                    if (this.entityHost.getRNG().nextFloat() < 0.3D) {
                        this.strafingClockwise = !this.strafingClockwise;
                    }

                    if (this.entityHost.getRNG().nextFloat() < 0.3D) {
                        this.strafingBackwards = !this.strafingBackwards;
                    }

                    this.strafingTime = 0;
                }

                // �������ʱ����� -1
                if (this.strafingTime > -1) {
                    // ���ݾ���Զ�������Ƿ�ǰ����λ
                    if (distanceSq > this.maxAttackDistance * 0.75F) {
                        this.strafingBackwards = false;
                    } else if (distanceSq < this.maxAttackDistance * 0.25F) {
                        this.strafingBackwards = true;
                    }

                    // Ӧ����λ
                    this.entityHost.getMoveHelper().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
                    this.entityHost.faceEntity(entitylivingbase, 30.0F, 30.0F);
                } else {
                    // ����ֻ���򹥻�Ŀ��
                    this.entityHost.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
                }

                // ���ʵ���ֲ����ڼ���״̬
                if (this.entityHost.isHandActive()) {
                    // ����������Է���ʱ 60�����ü���״̬
                    if (!canSee && this.seeTime < -60) {
                        this.entityHost.resetActiveHand();
                    } else if (canSee) {
                        // ����ʼ����Զ�̹���
                        int i = this.entityHost.getItemInUseMaxCount();

                        // ��Ʒ���ʹ�ü������� 20 �ſ���
                        // ������½����¼�������˼��Ҳ��������������Խ����Ȼ���ԽԶ
                        // ֻ���������� 1 ��Ż���з���
                        if (i >= 50) {
                            this.entityHost.resetActiveHand();
                            this.entityHost.attackEntityWithRangedAttack(entitylivingbase, ItemBow.getArrowVelocity(i));


                            this.attackTime = this.attackCooldown;

                        }
                    }
                } else if (--this.attackTime <= 0 && this.seeTime >= -60) {
                    // �Ǽ���״̬������ʱ�����ʣ���ʼ�����ֲ�
                    this.entityHost.setActiveHand(EnumHand.MAIN_HAND);
                }
            //}




        }

    }
}
