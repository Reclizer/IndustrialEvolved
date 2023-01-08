package com.reclizer.inevo.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import javax.annotation.Nullable;
import java.util.function.BiPredicate;

import java.util.function.Predicate;
public final class BlockUtils {

    private BlockUtils(){} // No instances!




    // Floor finding
    // ===============================================================================================================

    /**
     * Finds the nearest surface (according to the given criteria) in the given direction from the given position,
     * within the range specified. This is a generalised replacement for the old {@code getNearestFloorLevel} methods;
     * overloads and predefined surface criteria that replicate the old behaviours are available.
     *
     * @param world The world to search in
     * @param pos The position to search from
     * @param direction The direction to search in; also defines the direction the surface must face.
     * @param range The maximum distance from the given y coordinate to search.
     * @param doubleSided True to also search in the opposite direction, false to only search in the given direction.
     * @param criteria A {@link SurfaceCriteria} representing the criteria defining a surface. See that class for
     *                 available predefined criteria.
     * @return The x, y, or z coordinate of the closest surface, or null if no surface was found. This represents the
     * exact position of the block boundary that forms the surface (and therefore it may correspond to the coordinate
     * of the inside or outside block of the surface depending on the direction).
     */
    @Nullable
    public static Integer getNearestSurface(World world, BlockPos pos, EnumFacing direction, int range,
                                            boolean doubleSided, SurfaceCriteria criteria){

        // This is a neat trick that allows a default 'not found' return value for integers where all possible integer
        // values could, in theory, be returned. The alternative is to use a double and have NaN as the default, but
        // that would introduce extra casting, and since NaN can be calculated with, it could produce strange results
        // when unaccounted for. Using an Integer means it'll immediately throw an NPE instead.
        Integer surface = null;
        int currentBest = Integer.MAX_VALUE;

        for(int i = doubleSided ? -range : 0; i <= range && i < currentBest; i++){ // Now short-circuits for efficiency

            BlockPos testPos = pos.offset(direction, i);

            if(criteria.test(world, testPos, direction)){
                // Because the loop now short-circuits, this must be closer than the previous surface found
                surface = (int)component(getFaceCentre(testPos, direction), direction.getAxis());
                currentBest = Math.abs(i);
            }
        }

        return surface;
    }

    public static double component(Vec3d vec, Axis axis){
        return new double[]{vec.x, vec.y, vec.z}[axis.ordinal()]; // Damn, that's compact.
    }

    public static Vec3d getFaceCentre(BlockPos pos, EnumFacing face){
        return getCentre(pos).add(new Vec3d(face.getDirectionVec()).scale(0.5));
    }

    public static Vec3d getCentre(BlockPos pos){
        return new Vec3d(pos).addVector(0.5, 0.5, 0.5);
    }

    /**
     * Finds the nearest floor level in the given direction from the given position,
     * within the range specified. This is a shorthand for
     * {@link BlockUtils#getNearestSurface(World, BlockPos, EnumFacing, int, boolean, SurfaceCriteria)};
     * {@code doubleSided} defaults to true, {@code direction} defaults to {@code EnumFacing.UP}, and
     * {@code criteria} defaults to {@link SurfaceCriteria#COLLIDABLE}.
     */
    @Nullable
    public static Integer getNearestFloor(World world, BlockPos pos, int range){
        return getNearestSurface(world, pos, EnumFacing.UP, range, true, SurfaceCriteria.COLLIDABLE);
    }




    /**
     * A {@code SurfaceCriteria} object is used to define a 'surface', a boundary between two blocks which differ in
     * some way, for use in {@link BlockUtils#getNearestSurface(World, BlockPos, EnumFacing, int, boolean, SurfaceCriteria)}.
     * This provides a more flexible replacement for the old {@code getNearestFloorLevel} methods.<br>
     * <br>
     * <i>In the context of this class, 'outside' refers to the side of the surface that is in the supplied direction,
     * and 'inside' refers to the side which is in the opposite direction. For example, if the direction is {@code UP},
     * the inside of the surface is defined as below it, and the outside is defined as above it.</i>
     */
    @FunctionalInterface
    public interface SurfaceCriteria {

        /**
         * Tests whether the inputs define a valid surface according to this set of criteria.
         * @param world The world in which the surface is to be tested.
         * @param pos The block coordinates of the inside ('solid' part) of the surface.
         * @param side The direction in which the surface must face.
         * @return True if the side {@code side} of the block at {@code pos} in {@code world} is a valid surface
         * according to this set of criteria, false otherwise.
         */
        boolean test(World world, BlockPos pos, EnumFacing side);

        /** Returns a {@code SurfaceCriteria} with the opposite arrangement to this one. */
        default SurfaceCriteria flip(){
            return (world, pos, side) -> this.test(world, pos.offset(side), side.getOpposite());
        }

        /** Returns a {@code SurfaceCriteria} based on the given condition, where the inside of the surface satisfies
         * the condition and the outside does not. */
        static SurfaceCriteria basedOn(BiPredicate<World, BlockPos> condition){
            return (world, pos, side) -> condition.test(world, pos) && !condition.test(world, pos.offset(side));
        }

        /** Returns a {@code SurfaceCriteria} based on the given condition, where the inside of the surface satisfies
         * the condition and the outside does not. */
        static SurfaceCriteria basedOn(Predicate<IBlockState> condition){
            return (world, pos, side) -> condition.test(world.getBlockState(pos)) && !condition.test(world.getBlockState(pos.offset(side)));
        }

        /** Surface criterion which defines a surface as the boundary between a block that cannot be moved through and
         * a block that can be moved through. This means the surface can be stood on. */
        SurfaceCriteria COLLIDABLE = basedOn(b -> b.getMaterial().blocksMovement());

        /** Surface criterion which defines a surface as the boundary between a block that is solid on the required side and
         * a block that is replaceable. This means the surface can be built on. */
        SurfaceCriteria BUILDABLE = (world, pos, side) -> world.isSideSolid(pos, side) && world.getBlockState(pos.offset(side)).getBlock().isReplaceable(world, pos.offset(side));

        /** Surface criterion which defines a surface as the boundary between a block that is solid on the required side
         * or a liquid, and an air block. Used for freezing water and placing snow. */
        // Was getNearestFloorLevelB
        SurfaceCriteria SOLID_LIQUID_TO_AIR = (world, pos, side) -> (world.getBlockState(pos).getMaterial().isLiquid()
                || world.isSideSolid(pos, side) && world.isAirBlock(pos.offset(side)));

        /** Surface criterion which defines a surface as the boundary between any non-air block and an air block.
         * Used for particles, and is also good for placing fire. */
        // Was getNearestFloorLevelC
        SurfaceCriteria NOT_AIR_TO_AIR = basedOn(World::isAirBlock).flip();

//        /** Surface criterion which defines a surface as the boundary between a block that cannot be moved through, and
//         * a block that can be moved through or a tree block (log or leaves). Used for structure generation. */
//        SurfaceCriteria COLLIDABLE_IGNORING_TREES = basedOn((world, pos) ->
//                world.getBlockState(pos).getMaterial().blocksMovement() && !isTreeBlock(world, pos));

    }

}
