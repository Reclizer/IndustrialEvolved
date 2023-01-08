package com.reclizer.inevo.entity.construct;

import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.entity.ai.EntityAIMasterHurtByTarget;
import com.reclizer.inevo.player.PlayerEnergy;
import com.reclizer.inevo.player.PlayerProperties;
import com.reclizer.inevo.util.AllyDesignationSystem;
import com.reclizer.inevo.util.EntityUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class EntityEnergyConstruct extends EntityCreature implements IEntityOwnable, IEntityAdditionalSpawnData {

    public int lifetime = 600;
    private UUID masterUUID;

    public EntityEnergyConstruct(World worldIn) {
        super(worldIn);

    }
    @Override
    protected void initEntityAI() {
        //this.targetTasks.addTask(1, new EntityAIMasterHurtByTarget(this));
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(this.ticksExisted > lifetime && lifetime != -1){
            this.despawn();
        }
        if(this.getMaster()==null){
            this.despawn();
        }
    }

    public boolean isValidTarget(Entity target){
        return AllyDesignationSystem.isValidTarget(this, target);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        //this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        //this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(50.0D);
    }

    @Override
    public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand){

        // Permanent constructs can now be dispelled by sneak-right-clicking
        //&& player.getHeldItem(hand).getItem() instanceof ISpellCastingItem  &&lifetime == -1
        if(getMaster() == player && player.isSneaking() ){
            PlayerEnergy playerEnergy = PlayerProperties.getPlayerSummoned(player);
            playerEnergy.setSpaceEnergy(playerEnergy.getSpaceEnergy()+5);
            this.despawn();
            return EnumActionResult.SUCCESS;
        }

        return super.applyPlayerInteraction(player, vec, hand);
    }




    public void despawn(){
        this.setDead();
    }




    @Override//∂‘À§¬‰…À∫¶√‚“ﬂ
    public void fall(float distance, float damageMultiplier){}


    @Nullable
    @Override
    public UUID getOwnerId() {
        return masterUUID;
    }

    @Nullable
    @Override
    public Entity getOwner() {
        return getMaster();
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeInt(lifetime);
        buffer.writeInt(getMaster() == null ? -1 : getMaster().getEntityId());
    }

    @Override
    public void readSpawnData(ByteBuf data) {
        lifetime = data.readInt();

        int id = data.readInt();

        if(id == -1){
            setMaster(null);
        }else{
            Entity entity = world.getEntityByID(id);
            if(entity instanceof EntityLivingBase){
                setMaster((EntityLivingBase)entity);
            }else{
                IndustrialEvolved.logger.warn("Construct master with ID in spawn data not found");
            }
        }
    }

    /**
     * Returns the EntityLivingBase that created this construct, or null if it no longer exists. Cases where the entity
     * may no longer exist are: entity died or was deleted, mob despawned, player logged out, entity teleported to
     * another dimension, or this construct simply had no master in the first place.
     */
    @Nullable
    public EntityLivingBase getMaster(){ // Kept despite the above method because it returns an EntityLivingBase

        Entity entity = EntityUtils.getEntityByUUID(world, getOwnerId());

        if(entity != null && !(entity instanceof EntityLivingBase)){ // Should never happen
            IndustrialEvolved.logger.warn("{} has a non-living owner!", this);
            entity = null;
        }

        return (EntityLivingBase)entity;
    }

    public void setMaster(@Nullable EntityLivingBase master){
        this.masterUUID = master == null ? null : master.getUniqueID();
    }


    public boolean hasMaster(){
        if(this.getMaster()!=null){
            return true;
        }else {
            return false;
        }
    }


    public boolean shouldAttackEntity(EntityLivingBase target, EntityLivingBase owner)
    {
        return true;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound){
        //super.readEntityFromNBT(nbttagcompound);
        if(nbttagcompound.hasUniqueId("masterUUID")) masterUUID = nbttagcompound.getUniqueId("masterUUID");
        lifetime = nbttagcompound.getInteger("lifetime");
        //damageMultiplier = nbttagcompound.getFloat("damageMultiplier");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound){
        //super.writeEntityToNBT(nbttagcompound);
        if(masterUUID != null){
            nbttagcompound.setUniqueId("masterUUID", masterUUID);
        }
        nbttagcompound.setInteger("lifetime", lifetime);
        //nbttagcompound.setFloat("damageMultiplier", damageMultiplier);
    }


    @Override
    public boolean canRenderOnFire(){
        return false;
    }

    @Override
    public boolean isPushedByWater(){
        return false;
    }
}
