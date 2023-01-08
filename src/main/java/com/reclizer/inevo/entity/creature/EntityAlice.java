package com.reclizer.inevo.entity.creature;

import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.entity.ai.AliceAIFollow;
import com.reclizer.inevo.entity.ai.EntityAliceAIRangeAttack;
import com.reclizer.inevo.entity.bullet.EntityBullet;

import com.reclizer.inevo.tools.RayTraceToolsLiving;
import com.reclizer.inevo.util.EntityUtils;
import com.reclizer.inevo.util.ParticleBuilder;
import com.reclizer.inevo.util.Reference;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import com.reclizer.inevo.util.ParticleBuilder.Type;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EntityAlice extends EntityCreature implements IAnimals,IRangedAttackMob {


    private static final DataParameter<Integer> ATTACK_COOLDOWN = EntityDataManager.createKey(EntityAlice.class,
            DataSerializers.VARINT);
    private static final DataParameter<Boolean> CAN_ATTACK = EntityDataManager.createKey(EntityAlice.class,
            DataSerializers.BOOLEAN);

    public static final ResourceLocation LOOT = new ResourceLocation(IndustrialEvolved.MODID, "entities/alice");
    private int attackTimer;
    private int healTime;
    public int targetTime=0;
    private int targetCD;

    public EntityAlice(World worldIn) {
        super(worldIn);

        this.setSize(0.6F, 1.95F);
        this.experienceValue = 5;
    }


    @Override
    protected void entityInit(){
        super.entityInit();
        this.dataManager.register(ATTACK_COOLDOWN, -1);
        this.dataManager.register(CAN_ATTACK, false);


    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAliceAIRangeAttack(this, 1.25D, 20, 10.0F));
        //this.tasks.addTask(1, new EntityAIAttackRanged(this, 1.25D, 20, 10.0F));
        //this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));
        this.tasks.addTask(3, new AliceAIFollow(this, 1.0D, 6.0F, 9.0F));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityLiving.class, 4, false, true,
                entity -> entity != null && IMob.VISIBLE_MOB_SELECTOR.apply(entity)));
    }


    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {//怪物持有物品
        livingdata = super.onInitialSpawn(difficulty, livingdata);//继承原有的逻辑
        //this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BOW));//主手

        this.setCanPickUpLoot(true);//允许捡起物品

        return livingdata;
    }



    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.attackTimer > 0) {
            --this.attackTimer;
        }
        healTime++;
        if(healTime>50){
            if(!this.world.isRemote){
                this.setHealth(this.getHealth()+2);
                this.clearActivePotions();
            }else {
                for(int i = 0; i < 15; i++){
                    float brightness = 0.3f + (rand.nextFloat() / 2);
                    ParticleBuilder.create(Type.SPARKLE, this).vel(0, 0.05, 0).time(20 + rand.nextInt(10))
                            .clr(brightness, brightness + 0.2f, 1.0f).spawn(world);
                }
            }
            healTime=0;
        }

        int attackCooldown = this.getAttackCooldown();
//                for(int i=0; i<10; i++){
//                    double x = (double)((float)this.posX + rand.nextFloat() * 2 - 1.0F);
//                    // Apparently the client side spawns the particles 1 block higher than it should... hence the -
//                    // 0.5F.
//                    double y = (double)((float)this.posY - 0.5F + rand.nextFloat());
//                    double z = (double)((float)this.posZ + rand.nextFloat() * 2 - 1.0F);
//                    ParticleBuilder.create(Type.SPARKLE).pos(x, y, z).vel(0, 0.1F, 0).clr(1, 1, 0.3f).spawn(world);
//                }




//        if(target !=null){
//            this.setCanAttack(true);
//        }else {
//            this.setCanAttack(false);
//        }
//
//        boolean canAttack=this.getCanAttack();

//        EntityLivingBase target = this.getAttackTarget();
//
//        if(this.world.isRemote&&target !=null){
//            RayTraceToolsAlice.Beam beam = new RayTraceToolsAlice.Beam(world, this, 20);
//            Vec3d start = beam.getStart();
//            Vec3d end =beam.getEnd();
//
//            ParticleBuilder.create(ParticleBuilder.Type.BEAM).clr(0.2f, 0.6f, 1).time(4).pos(start)
//                    .target(end).spawn(world);
//        }




//        EntityLivingBase target = this.getAttackTarget();
//
//        if(this.world.isRemote&&target !=null){
//            for(int i = 0; i < 15; i++){
//                float brightness = 0.3f + (rand.nextFloat() / 2);
//                ParticleBuilder.create(Type.SPARKLE, this).vel(0, 0.05, 0).time(20 + rand.nextInt(10))
//                        .clr(brightness, brightness + 0.2f, 1.0f).spawn(world);
//            }
//        }



    }


//============================================================================================
    //实体数据

    private int getAttackCooldown(){
        return this.dataManager.get(ATTACK_COOLDOWN);
    }

    private void setAttackCooldown(int cooldown){
        this.dataManager.set(ATTACK_COOLDOWN, cooldown);
    }

    private boolean getCanAttack(){
        return this.dataManager.get(CAN_ATTACK);
    }

    private void setCanAttack(boolean can){
        this.dataManager.set(CAN_ATTACK, can);
    }



//    public Element getElement(){
//        int n = this.dataManager.get(ELEMENT);
//        return n == -1 ? null : Element.values()[n];
//    }

//============================================================================================
    //监听事件


//    @SubscribeEvent
//    public void aliceAttack(LivingAttackEvent evt){
//        World world = evt.getEntity().getEntityWorld();
//
//        if(evt.getSource() != null && evt.getSource().getTrueSource() instanceof EntityLivingBase&&evt.getEntity() instanceof EntityAlice){
//            EntityAlice attacker = (EntityAlice) evt.getEntityLiving();
//            EntityLivingBase hurtOne = (EntityLivingBase) evt.getSource().getTrueSource();
//            Vec3d start= attacker.getPositionEyes(1.0f);
//
//            Vec3d end = hurtOne.getPositionVector();
//            if(world.isRemote){
//                ParticleBuilder.create(ParticleBuilder.Type.BEAM).clr(0.2f, 0.6f, 1).time(4).pos(start)
//                            .target(end).spawn(world);
//            }
//        }
//    }



//    @SubscribeEvent
//    public void aliceHurt(LivingHurtEvent evt){
//        World world = evt.getEntity().getEntityWorld();
//
//            EntityLivingBase hurtOne = (EntityLivingBase) evt.getEntityLiving();
//            //EntityAlice attacker = (EntityAlice) evt.getSource().getTrueSource();
//            EntityLivingBase attacker = (EntityLivingBase) evt.getSource().getTrueSource();
//            //&&evt.getEntity() instanceof EntityAlice
//            Vec3d end = hurtOne.getPositionVector();
//            if(attacker!=null){
//                Vec3d start= attacker.getPositionEyes(1.0f);
//                ParticleBuilder.create(ParticleBuilder.Type.BEAM).clr(0.2f, 0.6f, 1).time(4).pos(start)
//                       .target(end).spawn(world);
//            }
//    }



//============================================================================================
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(300.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(50.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);//盔甲
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LOOT;
    }

    @Override
    public float getEyeHeight() {
        return 1.74F;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        this.attackTimer = 10;
        this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, 1.0F, 1.0F);
        return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)(1 + this.rand.nextInt(15)));
    }

//==========================================================================================================
    public int getPhase() {
//        if (isShadowClone() || getShieldStrength() > 0) {
//            return 1;
//        } else if (getMinionsToSummon() > 0) {
//            return 2;
//        } else {
//            return 3;
//        }
        return 0;
    }
    //======================================================================================================

    @Override//对摔落伤害免疫
    public void fall(float distance, float damageMultiplier){}


//======================================================================================================
    //IRangedAttackMob工具
    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {

        this.playSound(SoundEvents.ENTITY_SNOWMAN_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));

//         EntityLivingBase livingBase=(EntityLivingBase)this;
//        RayTraceToolsLiving.Beam beam = new RayTraceToolsLiving(world,livingBase, 20);
        EntityUtils.attackEntityWithoutKnockback(target,DamageSource.causeMobDamage(this), 1f);
        //targetTime=1;
        //spawnBullet(this, beam);
        //EntityUtils.attackEntityWithoutKnockback(target,DamageSource.OUT_OF_WORLD, 1f);
//        Vec3d start = beam.getStart();
//        Vec3d end =beam.getEnd();
//        Vec3d lookVec = beam.getLookVec();
//
//        if(world.isRemote){
//            ParticleBuilder.create(ParticleBuilder.Type.BEAM).clr(0.2f, 0.6f, 1).time(4).pos(start)
//                    .target(end).spawn(world);
//
//        }


    }

    @Override
    public void setSwingingArms(boolean swingingArms) {
        //this.dataManager.set(SWINGING_ARMS, Boolean.valueOf(swingingArms));
    }


    private void spawnBullet(EntityAlice alice, RayTraceToolsLiving.Beam beam) {
        Vec3d start = beam.getStart();
        Vec3d end =beam.getEnd();
        Vec3d lookVec = beam.getLookVec();
        double accelX = lookVec.x * 1.0D;
        double accelY = lookVec.y * 1.0D;
        double accelZ = lookVec.z * 1.0D;

        EntityBullet laser = new EntityBullet(alice.getEntityWorld(), alice, accelX, accelY, accelZ);
        laser.posX = start.x;
        laser.posY = start.y;
        laser.posZ = start.z;

        alice.getEntityWorld().spawnEntity(laser);

    }

    //===================================================================================================






}
