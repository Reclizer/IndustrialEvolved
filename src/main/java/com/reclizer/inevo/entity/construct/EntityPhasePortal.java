package com.reclizer.inevo.entity.construct;

import com.reclizer.inevo.util.ParticleBuilder;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

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

        if(world.isRemote){

            float b = 0.15f;
            //b-=0.02, 0, 0



            for(double r = 1.5; r < 4; r += 0.2){
                ParticleBuilder.create(ParticleBuilder.Type.CLOUD).clr(0.2f, 0.6f, 1)
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
