package com.reclizer.inevo.entity.bullet;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntitySwordLight extends EntityBullet{
    public EntitySwordLight(World worldIn) {
        super(worldIn);
        this.setSize(1.0F, 1.0F);
    }
    public EntitySwordLight(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
        super(worldIn, shooter, accelX, accelY, accelZ);
        this.lifetime=50;
        this.flySpeed=1.5F;
        this.distanceMax=150;
    }

}
