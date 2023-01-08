package com.reclizer.inevo.tools;


import com.google.common.base.Predicates;
import net.minecraft.entity.Entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class RayTraceToolsLiving {
    // Trace a beam from the Alice in the direction the Alice is looking. Stop if a block is hit. Call
    // the function on all hit entities sorted by distance (closest entities first). Stop if the function returns true
    public static void rayTrace(Beam beam, Function<Entity, Boolean> consumer) {
        // Based on EntityRender.getMouseOver(float partialTicks) which we can't use because that's client only
//        Vec3d start = beam.getStart();
//        Vec3d lookVec = beam.getLookVec();
//        Vec3d end = beam.getEnd();
//        double dist = beam.getDist();
//        World world = beam.getWorld();
//        EntityLivingBase livingBase = beam.getLivingBase();
//        List<Entity> targets = world.getEntitiesInAABBexcluding(livingBase, livingBase.getEntityBoundingBox().expand(lookVec.x * dist, lookVec.y * dist, lookVec.z * dist).grow(1.0D, 1.0D, 1.0D),
//                Predicates.and(EntitySelectors.NOT_SPECTATING, ent -> ent != null && ent.canBeCollidedWith()));
//        List<Pair<Entity, Double>> hitTargets = new ArrayList<>();
//        for (Entity target : targets) {
//            AxisAlignedBB targetBB = target.getEntityBoundingBox().grow(target.getCollisionBorderSize());
//            if (targetBB.contains(start)) {
//                hitTargets.add(Pair.of(target, 0.0));
//            } else {
//                RayTraceResult targetResult = targetBB.calculateIntercept(start, end);
//                if (targetResult != null) {
//                    double d3 = start.distanceTo(targetResult.hitVec);
//                    if (d3 < dist) {
//                        hitTargets.add(Pair.of(target, d3));
//                    }
//                }
//            }
//        }
//        hitTargets.sort(Comparator.comparing(Pair::getRight));
//        hitTargets.stream().filter(pair -> consumer.apply(pair.getLeft())).findFirst();
    }

    public static class Beam {
        private World world;
        //private EntityLivingBase livingBase;
        private double maxDist;
        private Vec3d start;
        private Vec3d lookVec;
        private Vec3d end;
        private double dist;

        public Beam(World world, Vec3d start,Vec3d lookVec, double maxDist) {
            this.world = world;
            //this.livingBase = livingBase;
            this.start=start;
            this.lookVec=lookVec;
            this.maxDist = maxDist;

            calculate();
        }

        private void calculate() {
            //start = this.livingBase.getPositionEyes(1.0f);
            //lookVec = this.livingBase.getLookVec();

            end = start.addVector(lookVec.x * this.maxDist, lookVec.y * this.maxDist, lookVec.z * this.maxDist);

            // Find out if there is a block blocking our beam
            RayTraceResult result = this.world.rayTraceBlocks(start, end);
            dist = this.maxDist;
            if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
                // Adjust our maximum distance 最大距离
                dist = result.hitVec.distanceTo(start);
                end = start.addVector(lookVec.x * dist, lookVec.y * dist, lookVec.z * dist);
            }
        }

        public Vec3d getStart() {
            return start;
        }

        public Vec3d getLookVec() {
            return lookVec;
        }

        public Vec3d getEnd() {
            return end;
        }

        public double getDist() {
            return dist;
        }

        public World getWorld() {
            return world;
        }

//        public EntityLivingBase getLivingBase() {
//            return livingBase;
//        }

        public double getMaxDist() {
            return maxDist;
        }
    }
}
