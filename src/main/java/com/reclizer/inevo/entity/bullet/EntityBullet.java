package com.reclizer.inevo.entity.bullet;

import com.reclizer.inevo.entity.construct.EntityPhasePortal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBullet extends Entity {


    private EntityLivingBase shootingEntity;
    protected EntityLivingBase thrower;
    private int ticksAlive;
    private int ticksInAir;
    private double accelerationX;
    private double accelerationY;
    private double accelerationZ;
    public double posEndX;
    public double posEndY;
    public double posEndZ;
    public float damage=10.0F;
    public float flySpeed=2.00F;
    public int lifetime = 100;
    public int stagnation=20;
    public  double distanceMax=200;
    public double posSpawnX;
    public double posSpawnY;
    public double posSpawnZ;


    public EntityBullet(World worldIn) {
        super(worldIn);
        this.setSize(1.0F, 1.0F);
    }

    @Override
    protected void entityInit() {
    }

    public EntityBullet(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
        super(worldIn);
        this.shootingEntity = shooter;
        this.setSize(1.0F, 1.0F);
        this.setLocationAndAngles(shooter.posX, shooter.posY, shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        double d0 = MathHelper.sqrt(accelX * accelX + accelY * accelY + accelZ * accelZ);
        this.accelerationX = accelX / d0 * 0.1D;
        this.accelerationY = accelY / d0 * 0.1D;
        this.accelerationZ = accelZ / d0 * 0.1D;
    }

    @Override
    public void onUpdate() {
        if (this.world.isRemote || this.world.isBlockLoaded(new BlockPos(this))) {
            super.onUpdate();

            ++this.ticksInAir;
            RayTraceResult raytraceresult = ProjectileHelper.forwardsRaycast(this, true, this.ticksInAir >= 20, this.shootingEntity);

            if (raytraceresult != null && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
                this.onImpact(raytraceresult);
            }

            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            ProjectileHelper.rotateTowardsMovement(this, 0.2F);
            float f = this.getMotionFactor();

            if (this.isInWater()) {
                for (int i = 0; i < 4; ++i) {
                    this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * 0.25D, this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY, this.motionZ);
                }

            }



//            for (int i = 0; i < 10; ++i) {
//                this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX - this.motionX * 0.25D, this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY, this.motionZ);
//            }



            this.motionX += this.accelerationX;
            this.motionY += this.accelerationY;
            this.motionZ += this.accelerationZ;
            this.motionX *= f;
            this.motionY *= f;
            this.motionZ *= f;



            this.setPosition(this.posX, this.posY, this.posZ);

        if(!this.world.isRemote){
            if(this.ticksExisted > lifetime){
                this.deSpawn();
            }

//            if(this.ticksInAir>=stagnation){
//                f=0.0F;
//                this.motionX=0.0F;
//                this.motionY=0.0F;
//                this.motionZ=0.0F;
//            }

        if(this.getDistanceSq(this.posSpawnX,this.posSpawnY,this.posSpawnZ)>distanceMax){
            this.deSpawn();
        }
        }


        } else {

            deSpawn();
        }


    }

    public void deSpawn(){
        this.setDead();
    }



    public BlockPos getPositionEnd(){
        return new BlockPos(this.posEndX, this.posEndY, this.posEndZ);// + 0.5D
    }

    protected float getMotionFactor() {
        return flySpeed;
    }




    protected void onImpact(RayTraceResult result) {

        if (!this.world.isRemote) {
            if (result.entityHit != null) {
                result.entityHit.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, this.shootingEntity), damage);
            }
            deSpawn();
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setTag("direction", this.newDoubleNBTList(this.motionX, this.motionY, this.motionZ));
        compound.setTag("power", this.newDoubleNBTList(this.accelerationX, this.accelerationY, this.accelerationZ));
        compound.setInteger("life", this.ticksAlive);
        compound.setInteger("lifetime", lifetime);
        compound.setInteger("stagnation", stagnation);
        compound.setTag("spawnPos", this.newDoubleNBTList(this.posSpawnX, this.posSpawnY, this.posSpawnZ));

    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        if (compound.hasKey("power", 9)) {
            NBTTagList nbttaglist = compound.getTagList("power", 6);

            if (nbttaglist.tagCount() == 3) {
                this.accelerationX = nbttaglist.getDoubleAt(0);
                this.accelerationY = nbttaglist.getDoubleAt(1);
                this.accelerationZ = nbttaglist.getDoubleAt(2);
            }
        }

        this.ticksAlive = compound.getInteger("life");

        if (compound.hasKey("direction", 9) && compound.getTagList("direction", 6).tagCount() == 3) {
            NBTTagList nbttaglist1 = compound.getTagList("direction", 6);
            this.motionX = nbttaglist1.getDoubleAt(0);
            this.motionY = nbttaglist1.getDoubleAt(1);
            this.motionZ = nbttaglist1.getDoubleAt(2);
        } else {
            deSpawn();
        }
        if (compound.hasKey("spawnPos", 9) && compound.getTagList("spawnPos", 6).tagCount() == 3){
            NBTTagList nbttaglist2 = compound.getTagList("spawnPos", 6);
            this.posSpawnX = nbttaglist2.getDoubleAt(0);
            this.posSpawnY = nbttaglist2.getDoubleAt(1);
            this.posSpawnZ = nbttaglist2.getDoubleAt(2);
        }else {
            deSpawn();
        }

        lifetime = compound.getInteger("lifetime");
        stagnation=compound.getInteger("stagnation");
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public float getCollisionBorderSize() {
        return 1.0F;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isEntityInvulnerable(source)) {
            return false;
        } else {
            this.markVelocityChanged();

            if (source.getTrueSource() != null) {
                Vec3d vec3d = source.getTrueSource().getLookVec();

                if (vec3d != null) {
                    this.motionX = vec3d.x;
                    this.motionY = vec3d.y;
                    this.motionZ = vec3d.z;
                    this.accelerationX = this.motionX * 0.1D;
                    this.accelerationY = this.motionY * 0.1D;
                    this.accelerationZ = this.motionZ * 0.1D;
                }

                if (source.getTrueSource() instanceof EntityLivingBase) {
                    this.shootingEntity = (EntityLivingBase) source.getTrueSource();
                }

                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public float getBrightness() {
        return 1.0F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
        return 15728880;
    }

}
