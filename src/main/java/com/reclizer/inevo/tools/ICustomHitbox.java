package com.reclizer.inevo.tools;

import net.minecraft.util.math.Vec3d;

/** This interface allows implementing entity classes to define their own hitbox for wizardry's raytracing and
 * particle collision methods. Typically, entities implementing this interface will return null from the collision
 * bounding box methods in {@code Entity} (there are two for some reason) <b>but</b> return true from
 * {@link net.minecraft.entity.Entity#canBeCollidedWith()} */
public interface ICustomHitbox {


	Vec3d calculateIntercept(Vec3d origin, Vec3d endpoint, float fuzziness);

	/**
	 * Returns whether the given point is inside this entity.
	 * @param point The coordinates to test.
	 * @return True if the point is inside this entity, false if not.
	 */
	boolean contains(Vec3d point);

}
