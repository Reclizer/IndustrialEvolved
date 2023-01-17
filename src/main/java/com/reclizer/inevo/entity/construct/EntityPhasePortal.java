package com.reclizer.inevo.entity.construct;

import com.reclizer.inevo.potion.PotionRegistryHandler;
import com.reclizer.inevo.util.EntityUtils;
import com.reclizer.inevo.util.ParticleBuilder;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.List;

import static com.reclizer.inevo.potion.PotionRegistryHandler.PHASE_POTION_II;

public class EntityPhasePortal extends EntityConstruct
{
    private static final String TAG_ROTATION = "rotation";
    private static final DataParameter<Float> ROTATION = EntityDataManager.createKey(EntityPhasePortal.class, DataSerializers.FLOAT);
    public EntityPhasePortal(World worldIn) {
        super(worldIn);
        this.setSize(1.0F, 3.0F);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(ROTATION, 0F);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        double radius =2;//¹¥»÷°ë¾¶
        List<EntityLivingBase> nearby = EntityUtils.getLivingWithinRadius(radius, posX, posY, posZ, world);
        nearby.sort(Comparator.comparingDouble(e -> e.getDistanceSq(this)));
        int targetsRemaining = 3;

        while(!nearby.isEmpty() && targetsRemaining > 0){

            EntityLivingBase target = nearby.remove(0);
            if(EntityUtils.isLiving(target)&&!world.isRemote) {//É¸Ñ¡¹¥»÷Ä¿±ê
                if(target instanceof EntityPlayer){
                    if(target.getActivePotionEffect(PHASE_POTION_II)==null){
                        target.addPotionEffect(new PotionEffect(PHASE_POTION_II, 2000, 0));
                    }
                }else {

                    EntityUtils.attackEntityWithoutKnockback(target, DamageSource.OUT_OF_WORLD, 20);
                    for (int i = 0; i < 16; ++i)
                    {
                        double d3 = target.posX + (target.getRNG().nextDouble() - 0.5D) * 50.0D;
                        double d4 = MathHelper.clamp(target.posY + (double)(target.getRNG().nextInt(16) - 8), 0.0D, (double)(world.getActualHeight() - 1));
                        double d5 = target.posZ + (target.getRNG().nextDouble() - 0.5D) * 50.0D;

                        if (target.isRiding())
                        {
                            target.dismountRidingEntity();
                        }

                        if (target.attemptTeleport(d3, d4, d5))
                        {
                            target.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
                            break;
                        }
                    }
                }
            }
            targetsRemaining--;
        }





        if(world.isRemote){

            float b = 0.61f;
            float g =0.18f;
            //b-=0.02, 0, 0


            //b-=0.02, 0, 0
            for(double r = 1.5; r < 2.5; r += 0.1){
                ParticleBuilder.create(ParticleBuilder.Type.CLOUD).clr(0.13f, g+=0.05f, b+=0.05f)
                        .pos(posX, posY + 1.5, posZ).scale(0.5f / (float)r)
                        .spin(r, 0.02/r * (1 + world.rand.nextDouble()),getRotation()).spawn(world);
            }

        }
    }

    public float getRotation() {
        return dataManager.get(ROTATION);
    }

    @Override
    public void writeEntityToNBT(@Nonnull NBTTagCompound cmp) {
        super.writeEntityToNBT(cmp);

        cmp.setFloat(TAG_ROTATION, getRotation());
    }

    @Override
    public void readEntityFromNBT(@Nonnull NBTTagCompound cmp) {
        super.readEntityFromNBT(cmp);

        setRotation(cmp.getFloat(TAG_ROTATION));
    }

    public void setRotation(float rot) {
        dataManager.set(ROTATION, rot);
    }

}
