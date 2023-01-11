package com.reclizer.inevo.entity.construct;

import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.player.PlayerEnergy;
import com.reclizer.inevo.player.PlayerProperties;
import com.reclizer.inevo.util.EntityUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class EntityConstruct extends Entity implements IEntityOwnable, IEntityAdditionalSpawnData {
    private UUID masterUUID;
    public int lifetime = 600;

    public EntityConstruct(World worldIn) {
        super(worldIn);
        this.noClip = true;
    }

    // Overrides the original to stop the entity moving when it intersects stuff. The default arrow does this to allow
    // it to stick in blocks.
    @Override
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport){
        this.setPosition(x, y, z);
        this.setRotation(yaw, pitch);
    }

    public void onUpdate(){

        if(this.ticksExisted > lifetime && lifetime != -1){
            this.despawn();
        }

        super.onUpdate();

    }




    @Override
    public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand){

        // Permanent constructs can now be dispelled by sneak-right-clicking
        //&& player.getHeldItem(hand).getItem() instanceof ISpellCastingItem  &&lifetime == -1
        if(getMaster() == player && player.isSneaking() ){
            PlayerEnergy playerEnergy = PlayerProperties.getPlayerEnergy(player);
            playerEnergy.setSpaceEnergy(playerEnergy.getSpaceEnergy()+5);
            this.despawn();
            return EnumActionResult.SUCCESS;
        }

        return super.applyPlayerInteraction(player, vec, hand);
    }




    public void despawn(){
        this.setDead();
    }




    @Override
    protected void entityInit(){
        // We could leave this unimplemented, but since the majority of subclasses don't use it, let's make it optional
    }

//==================================================================================================================
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


    //===========================================================
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



    @Override
    public boolean canRenderOnFire(){
        return false;
    }

    @Override
    public boolean isPushedByWater(){
        return false;
    }
}
