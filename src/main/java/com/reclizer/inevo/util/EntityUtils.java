package com.reclizer.inevo.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public final class EntityUtils {
    /**
     * Shorthand for {@link EntityUtils#getEntitiesWithinRadius(double, double, double, double, World, Class)}
     * with EntityLivingBase as the entity type. This is by far the most common use for that method.
     *
     * @param radius The search radius
     * @param x The x coordinate to search around
     * @param y The y coordinate to search around
     * @param z The z coordinate to search around
     * @param world The world to search in
     */
    public static List<EntityLivingBase> getLivingWithinRadius(double radius, double x, double y, double z, World world){
        return getEntitiesWithinRadius(radius, x, y, z, world, EntityLivingBase.class);
    }

    public static <T extends Entity> List<T> getEntitiesWithinRadius(double radius, double x, double y, double z, World world, Class<T> entityType){
        AxisAlignedBB aabb = new AxisAlignedBB(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius);
        List<T> entityList = world.getEntitiesWithinAABB(entityType, aabb);
        for(int i = 0; i < entityList.size(); i++){
            if(entityList.get(i).getDistance(x, y, z) > radius){
                entityList.remove(i);
                break;
            }
        }
        return entityList;
    }


    @Nullable
    public static Entity getEntityByUUID(World world, @Nullable UUID id){

        if(id == null) return null; // It would return null eventually but there's no point even looking

        for(Entity entity : world.loadedEntityList){
            // This is a perfect example of where you need to use .equals() and not ==. For most applications,
            // this was unnoticeable until world reload because the UUID instance or entity instance is stored.
            // Fixed now though.
            if(entity != null && entity.getUniqueID() != null && entity.getUniqueID().equals(id)){
                return entity;
            }
        }
        return null;
    }

    /**
     * Returns true if the given entity is an EntityLivingBase and not an armour stand; makes the code a bit neater.
     * This was added because armour stands are a subclass of EntityLivingBase, but shouldn't necessarily be treated
     * as living entities - this depends on the situation. <i>The given entity can safely be cast to EntityLivingBase
     * if this method returns true.</i>
     */
    // In my opinion, it's a bad design choice to have armour stands extend EntityLivingBase directly - it would be
    // better to make a parent class which is extended by both armour stands and EntityLivingBase and contains only
    // the code required by both.
    public static boolean isLiving(Entity entity){
        return entity instanceof EntityLivingBase && !(entity instanceof EntityArmorStand);
    }

    /**
     * Attacks the given entity with the given damage source and amount, but preserving the entity's original velocity
     * instead of applying knockback, as would happen with
     * {@link EntityLivingBase#attackEntityFrom(DamageSource, float)} <i>(More accurately, calls that method as normal
     * and then resets the entity's velocity to what it was before).</i> Handy for when you need to damage an entity
     * repeatedly in a short space of time.
     *
     * @param entity The entity to attack
     * @param source The source of the damage
     * @param amount The amount of damage to apply
     * @return True if the attack succeeded, false if not.
     */
    public static boolean attackEntityWithoutKnockback(Entity entity, DamageSource source, float amount){
        double vx = entity.motionX;
        double vy = entity.motionY;
        double vz = entity.motionZ;
        boolean succeeded = entity.attackEntityFrom(source, amount);
        entity.motionX = vx;
        entity.motionY = vy;
        entity.motionZ = vz;
        return succeeded;
    }
}
