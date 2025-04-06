package baspig.apis.utils.events.block;

import baspig.apis.utils.ModID;
import baspig.apis.utils.events.entity.EntityEvents;
import baspig.apis.utils.events.item.ItemEvents;
import baspig.apis.utils.util.*;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**This class gives you some easy config block events
 * @author Baspig_
 */
@SuppressWarnings("unused")
public class BlockEvents {
    static Random random = new Random();

    /**
     * Spawns a number of particles at random positions outside the block position.
     *
     * @param world The server world in which the particles are to be spawned. Must not be null.
     * @param particle The particle effect to spawn.
     * @param pos The block position inside which the particles will be spawned.
     * @param count The number of particles per tick to spawn.
     * @param spread The area around the block where particles will be added.
     */
    public static void spawnParticles(@NotNull World world, ParticleEffect particle, BlockPos pos, int count, double spread) {
        net.minecraft.util.math.random.Random random = world.getRandom();

        for (int i = 0; i < count; i++) {
            Direction face = Direction.values()[random.nextInt(6)];

            double x = pos.getX() + 0.5 + face.getOffsetX() * 0.51 + (face.getOffsetX() == 0 ? (random.nextDouble() - 0.5) * spread : 0);
            double y = pos.getY() + 0.5 + face.getOffsetY() * 0.51 + (face.getOffsetY() == 0 ? (random.nextDouble() - 0.5) * spread : 0);
            double z = pos.getZ() + 0.5 + face.getOffsetZ() * 0.51 + (face.getOffsetZ() == 0 ? (random.nextDouble() - 0.5) * spread : 0);

            world.addParticleClient(particle, x, y, z, 0, 0, 0);

        }
    }

    /**
     * Spawns a number of particles at random positions outside the block position.
     *
     * @param world The server world in which the particles are to be spawned. Must not be null.
     * @param particle The particle effect to spawn.
     * @param pos The block position inside which the particles will be spawned.
     * @param count The number of particles per tick to spawn.
     * @param spread The area around the block where particles will be added.
     * @param velocityType Type of particle velocity.
     * @param velocityX Initial particle X velocity, it may be affected by particle behavior.
     * @param velocityY Initial particle Y velocity, it may be affected by particle behavior.
     * @param velocityZ Initial particle Z velocity, it may be affected by particle behavior.
     */
    public static void spawnParticles(@NotNull World world,
                                      ParticleEffect particle,
                                      BlockPos pos, int count, double spread, ParticleVelocityType velocityType, float velocityX, float velocityY, float velocityZ) {
        net.minecraft.util.math.random.Random random = world.getRandom();

        for (int i = 0; i < count; i++) {
            Direction face = Direction.values()[random.nextInt(6)];

            double x = pos.getX() + 0.5 + face.getOffsetX() * 0.51 + (face.getOffsetX() == 0 ? (random.nextDouble() - 0.5) * spread : 0);
            double y = pos.getY() + 0.5 + face.getOffsetY() * 0.51 + (face.getOffsetY() == 0 ? (random.nextDouble() - 0.5) * spread : 0);
            double z = pos.getZ() + 0.5 + face.getOffsetZ() * 0.51 + (face.getOffsetZ() == 0 ? (random.nextDouble() - 0.5) * spread : 0);

            double velocity;

            float XVelocity = 0;
            float YVelocity = 0;
            float ZVelocity = 0;

            switch (velocityType){
                case CUSTOM:
                    XVelocity = velocityX;
                    YVelocity = velocityY;
                    ZVelocity = velocityZ;
                    world.addParticleClient(particle, x, y, z, XVelocity, YVelocity, ZVelocity);
                    break;
                case RANDOM:
                    XVelocity = (-random.nextFloat() + random.nextFloat());
                    YVelocity = (-random.nextFloat() + random.nextFloat());
                    ZVelocity = (-random.nextFloat() + random.nextFloat());
                    world.addParticleClient(particle, x, y, z, XVelocity, YVelocity, ZVelocity);
                    break;
                case UPWARD:
                    YVelocity = 0.25F;
                    world.addParticleClient(particle, x, y, z, XVelocity, YVelocity, ZVelocity);
                    break;
                case UPWARD_SLOW:
                    YVelocity = 0.05F;
                    world.addParticleClient(particle, x, y, z, XVelocity, YVelocity, ZVelocity);
                    break;
                case UPWARD_CUSTOM_VELOCITY:
                    YVelocity = velocityY;
                    world.addParticleClient(particle, x, y, z, XVelocity, YVelocity, ZVelocity);
                    break;

                case DOWNWARD:
                    YVelocity = -0.25F;
                    world.addParticleClient(particle, x, y, z, XVelocity, YVelocity, ZVelocity);
                    break;
                case DOWNWARD_SLOW:
                    YVelocity = -0.05F;
                    world.addParticleClient(particle, x, y, z, XVelocity, YVelocity, ZVelocity);
                    break;
                case DOWNWARD_CUSTOM_VELOCITY:
                    YVelocity = -velocityY;
                    world.addParticleClient(particle, x, y, z, XVelocity, YVelocity, ZVelocity);
                    break;

                case EASTWARD:
                    XVelocity = 0.25F;
                    world.addParticleClient(particle, x, y, z, XVelocity, YVelocity, ZVelocity);
                    break;
                case EASTWARD_SLOW:
                    XVelocity = 0.05F;
                    world.addParticleClient(particle, x, y, z, XVelocity, YVelocity, ZVelocity);
                    break;
                case EASTWARD_CUSTOM_VELOCITY:
                    XVelocity = velocityX;
                    world.addParticleClient(particle, x, y, z, XVelocity, YVelocity, ZVelocity);
                    break;

                case WESTWARD:
                    XVelocity = -0.25F;
                    world.addParticleClient(particle, x, y, z, XVelocity, YVelocity, ZVelocity);
                    break;
                case WESTWARD_SLOW:
                    XVelocity = -0.05F;
                    world.addParticleClient(particle, x, y, z, XVelocity, YVelocity, ZVelocity);
                    break;
                case WESTWARD_CUSTOM_VELOCITY:
                    XVelocity = -velocityX;
                    world.addParticleClient(particle, x, y, z, XVelocity, YVelocity, ZVelocity);
                    break;

                case SOUTHWARD:
                    ZVelocity = 0.25F;
                    world.addParticleClient(particle, x, y, z, XVelocity, YVelocity, ZVelocity);
                    break;
                case SOUTHWARD_SLOW:
                    ZVelocity = 0.05F;
                    world.addParticleClient(particle, x, y, z, XVelocity, YVelocity, ZVelocity);
                    break;
                case SOUTHWARD_CUSTOM_VELOCITY:
                    ZVelocity = velocityX;
                    world.addParticleClient(particle, x, y, z, XVelocity, YVelocity, ZVelocity);
                    break;

                case NORTHWARD:
                    ZVelocity = -0.25F;
                    world.addParticleClient(particle, x, y, z, XVelocity, YVelocity, ZVelocity);
                    break;
                case NORTHWARD_SLOW:
                    ZVelocity = -0.05F;
                    world.addParticleClient(particle, x, y, z, XVelocity, YVelocity, ZVelocity);
                    break;
                case NORTHWARD_CUSTOM_VELOCITY:
                    ZVelocity = -velocityX;
                    world.addParticleClient(particle, x, y, z, XVelocity, YVelocity, ZVelocity);
                    break;
            }
        }
    }

    /**
     * Spawns particles in various shapes at block position. The shape, size, and number of particles can be customized.
     *
     * @param world The world where the particles will be spawned.
     * @param particle The particle effect to spawn.
     * @param pos The position of the shape where the particles will spawn, by default aligns in the block center.
     * @param count The number of particles to spawn.
     * @param shape The shape in which the particles will spawn.
     * @param radius The radius of the shape for particle spawning.<p>
     * @param height The height of the shape. Height argument is only for CYLINDER, it will be skipped if another shape is selected.<p>
     * @param offset The positional offset to be applied from the provided center position.
     */
    @Deprecated(forRemoval = true)
    public static void spawnShapeParticles(@NotNull World world, @NotNull ParticleEffect particle, BlockPos pos, /// Pos is the center of the shape
                                           int count, @NotNull ShapeType shape,
                                           float radius,
                                           float height,
                                           BlockPos offset) {
        net.minecraft.util.math.random.Random random = world.getRandom();

        double X;
        double Y;
        double Z;

        for (int i = 0; i < count; i++) {

            double theta = random.nextDouble() * 2 * Math.PI;
            double phi = Math.acos(2 * random.nextDouble() - 1);

            double sphere_radius = Math.cbrt(random.nextDouble()) * radius;

            double cylinder_height = random.nextDouble() * height;

            double cylinder_radius = Math.sqrt(random.nextDouble()) * radius;

            float size = radius + radius;

            double xOffset = (random.nextDouble() - 0.5) * size;
            double yOffset = (random.nextDouble() - 0.5) * size;
            double zOffset = (random.nextDouble() - 0.5) * size;

            switch (shape){
                case CIRCLE:

                    X = pos.getX() + 0.5 + cylinder_radius * Math.cos(theta);
                    Y = pos.getY() + 0.5;
                    Z = pos.getZ() + 0.5 + cylinder_radius * Math.sin(theta);

                    world.addParticleClient(particle, X, Y, Z, 0, 0, 0);
                    break;

                case CIRCLE_OUTLINE:
                    X = pos.getX() + 0.5 + radius * Math.cos(theta);
                    Y = pos.getY() + 0.5;
                    Z = pos.getZ() + 0.5 + radius * Math.sin(theta);

                    world.addParticleClient(particle, X, Y, Z, 0, 0, 0);
                    break;

                case SPHERE:
                    X = pos.getX() + 0.5 + sphere_radius * Math.sin(phi) * Math.cos(theta);
                    Y = pos.getY() + 0.5 + sphere_radius * Math.cos(phi);
                    Z = pos.getZ() + 0.5 + sphere_radius * Math.sin(phi) * Math.sin(theta);

                    world.addParticleClient(particle, X, Y, Z, 0, 0, 0);
                    break;
                case SPHERE_OUTLINE:
                    double r = Math.sqrt(random.nextDouble()) * radius; ///Fill the sphere

                    X = pos.getX() + 0.5 + r * Math.cos(theta);
                    Y = pos.getY() + 0.5;
                    Z = pos.getZ() + 0.5 + r * Math.sin(theta);

                    world.addParticleClient(particle, X, Y, Z, 0, 0, 0);
                    break;

                case CYLINDER:

                    X = pos.getX() + 0.5 + cylinder_radius * Math.cos(theta);
                    Y = pos.getY() + 0.5 - (height/2) + cylinder_height;
                    Z = pos.getZ() + 0.5 + cylinder_radius * Math.sin(theta);

                    world.addParticleClient(particle, X, Y, Z, 0, 0, 0);
                    break;
                case CYLINDER_OUTLINE:

                    double angle = random.nextDouble() * 2 * Math.PI;

                    byte side = (byte) random.nextInt(3);

                    if (side == 0) {
                        // DOWN face
                        X = pos.getX() + 0.5 + cylinder_radius * Math.cos(angle);
                        Y = pos.getY() + 0.5 - (height / 2);
                        Z = pos.getZ() + 0.5 + cylinder_radius * Math.sin(angle);
                    } else if (side == 1) {
                        // UP face
                        X = pos.getX() + 0.5 + cylinder_radius * Math.cos(angle);
                        Y = pos.getY() + 0.5 + (height / 2);
                        Z = pos.getZ() + 0.5 + cylinder_radius * Math.sin(angle);
                    } else {
                        // Circle outline
                        double yHeight = pos.getY() + 0.5 - (height / 2) + random.nextDouble() * height;
                        X = pos.getX() + 0.5 + radius * Math.cos(angle);
                        Y = yHeight;
                        Z = pos.getZ() + 0.5 + radius * Math.sin(angle);
                    }

                    world.addParticleClient(particle, X, Y, Z, 0, 0, 0);
                    break;
                case SQUARE:

                    X = pos.getX() + 0.5 + xOffset;
                    Y = pos.getY() + 0.5;
                    Z = pos.getZ() + 0.5 + zOffset;

                    world.addParticleClient(particle, X, Y, Z, 0, 0, 0);
                    break;
                case SQUARE_OUTLINE:
                    double edge = random.nextDouble() * size - size / 2;

                    if (random.nextBoolean()) {

                        xOffset = (random.nextBoolean() ? size / 2 : -size / 2);
                        zOffset = edge;
                    } else {

                        xOffset = edge;
                        zOffset = (random.nextBoolean() ? size / 2 : -size / 2);
                    }

                    X = pos.getX() + 0.5 + xOffset;
                    Y = pos.getY() + 0.5;
                    Z = pos.getZ() + 0.5 + zOffset;

                    world.addParticleClient(particle, X, Y, Z, 0, 0, 0);
                    break;
                case CUBE:

                    X = pos.getX() + 0.5 + xOffset;
                    Y = pos.getY() + 0.5 + yOffset;
                    Z = pos.getZ() + 0.5 + zOffset;

                    world.addParticleClient(particle, X, Y, Z, 0, 0, 0);
                    break;
                case CUBE_OUTLINE:
                    int axis = random.nextInt(3);
                    double fixed = (random.nextBoolean() ? size / 2 : -size / 2);

                    if (axis == 0) {
                        xOffset = fixed;
                        yOffset = (random.nextDouble() - 0.5) * size;
                        zOffset = (random.nextDouble() - 0.5) * size;
                    } else if (axis == 1) {
                        yOffset = fixed;
                        xOffset = (random.nextDouble() - 0.5) * size;
                        zOffset = (random.nextDouble() - 0.5) * size;
                    } else {
                        zOffset = fixed;
                        xOffset = (random.nextDouble() - 0.5) * size;
                        yOffset = (random.nextDouble() - 0.5) * size;
                    }

                    X = pos.getX() + 0.5 + xOffset;
                    Y = pos.getY() + 0.5 + yOffset;
                    Z = pos.getZ() + 0.5 + zOffset;

                    world.addParticleClient(particle, X, Y, Z, 0, 0, 0);
                    break;
            }

        }
    }

    /**
     * Spawns particles in various shapes at block position. The shape, size, and number of particles can be customized.
     *
     * @param world The world where the particles will be spawned.
     * @param particleSettings The Settings of the shape to be added.
     */
    public static void spawnShapeParticles(@NotNull World world, @NotNull ParticleSettings particleSettings) {
        net.minecraft.util.math.random.Random random = world.getRandom();

        double X;
        double Y;
        double Z;

        int count = particleSettings.getAmount();

        double radius = particleSettings.getRadius();
        double height = particleSettings.getHeight();

        ShapeType shape = particleSettings.getShapeType();

        BlockPos pos = particleSettings.getBlockPos();

        ParticleEffect particle = particleSettings.getParticleEffect();

        for (int i = 0; i < count; i++) {

            double theta = particleSettings.getSpecialTheta();
            double phi = particleSettings.getSpecialPhi();

            double sphere_uniform = particleSettings.getCubicRadiusScaling();

            double cylinder_height = random.nextDouble() * height;

            double cylinder_radius = Math.sqrt(random.nextDouble()) * radius;

            double size = particleSettings.getDiameter();

            double xOffset = particleSettings.getArea();
            double yOffset = particleSettings.getArea();
            double zOffset = particleSettings.getArea();

            switch (shape){
                case CIRCLE:
                    X = pos.getX() + 0.5 + cylinder_radius * Math.cos(theta);
                    Y = pos.getY() + 0.5;
                    Z = pos.getZ() + 0.5 + cylinder_radius * Math.sin(theta);

                    world.addParticleClient(particle, X, Y, Z, 0, 0, 0);
                    break;

                case CIRCLE_OUTLINE:
                    X = pos.getX() + 0.5 + radius * Math.cos(theta);
                    Y = pos.getY() + 0.5;
                    Z = pos.getZ() + 0.5 + radius * Math.sin(theta);

                    world.addParticleClient(particle, X, Y, Z, 0, 0, 0);
                    break;

                case SPHERE:

                    X = pos.getX() + 0.5 + sphere_uniform * Math.sin(phi) * Math.cos(theta);
                    Y = pos.getY() + 0.5 + sphere_uniform * Math.cos(phi);
                    Z = pos.getZ() + 0.5 + sphere_uniform * Math.sin(phi) * Math.sin(theta);

                    world.addParticleClient(particle, X, Y, Z, 0, 0, 0);
                    break;
                case SPHERE_OUTLINE:

                    X = pos.getX() + 0.5 + radius * Math.sin(phi) * Math.cos(theta);
                    Y = pos.getY() + 0.5 + radius * Math.cos(phi);
                    Z = pos.getZ() + 0.5 + radius * Math.sin(phi) * Math.sin(theta);

                    world.addParticleClient(particle, X, Y, Z, 0, 0, 0);
                    break;

                case CYLINDER:

                    X = pos.getX() + 0.5 + cylinder_radius * Math.cos(theta);
                    Y = pos.getY() + 0.5 - (height/2) + cylinder_height;
                    Z = pos.getZ() + 0.5 + cylinder_radius * Math.sin(theta);

                    world.addParticleClient(particle, X, Y, Z, 0, 0, 0);
                    break;
                case CYLINDER_OUTLINE:

                    double angle = random.nextDouble() * 2 * Math.PI;

                    byte side = (byte) random.nextInt(90);

                    if (side <= 25) {
                        // DOWN face
                        X = pos.getX() + 0.5 + cylinder_radius * Math.cos(angle);
                        Y = pos.getY() + 0.5 - (height / 2);
                        Z = pos.getZ() + 0.5 + cylinder_radius * Math.sin(angle);
                    } else if (side <= 50) {
                        // UP face
                        X = pos.getX() + 0.5 + cylinder_radius * Math.cos(angle);
                        Y = pos.getY() + 0.5 + (height / 2);
                        Z = pos.getZ() + 0.5 + cylinder_radius * Math.sin(angle);
                    } else {
                        // Circle outline
                        double yHeight = pos.getY() + 0.5 - (height / 2) + random.nextDouble() * height;
                        X = pos.getX() + 0.5 + radius * Math.cos(angle);
                        Y = yHeight;
                        Z = pos.getZ() + 0.5 + radius * Math.sin(angle);
                    }

                    world.addParticleClient(particle, X, Y, Z, 0, 0, 0);
                    break;
                case SQUARE:

                    X = pos.getX() + 0.5 + xOffset;
                    Y = pos.getY() + 0.5;
                    Z = pos.getZ() + 0.5 + zOffset;

                    world.addParticleClient(particle, X, Y, Z, 0, 0, 0);
                    break;
                case SQUARE_OUTLINE:
                    double edge = random.nextDouble() * size - size / 2;

                    if (random.nextBoolean()) {

                        xOffset = (random.nextBoolean() ? size / 2 : -size / 2);
                        zOffset = edge;
                    } else {

                        xOffset = edge;
                        zOffset = (random.nextBoolean() ? size / 2 : -size / 2);
                    }

                    X = pos.getX() + 0.5 + xOffset;
                    Y = pos.getY() + 0.5;
                    Z = pos.getZ() + 0.5 + zOffset;

                    world.addParticleClient(particle, X, Y, Z, 0, 0, 0);
                    break;
                case CUBE:

                    X = pos.getX() + 0.5 + xOffset;
                    Y = pos.getY() + 0.5 + yOffset;
                    Z = pos.getZ() + 0.5 + zOffset;

                    world.addParticleClient(particle, X, Y, Z, 0, 0, 0);
                    break;
                case CUBE_OUTLINE:
                    int axis = random.nextInt(3);
                    double fixed = (random.nextBoolean() ? size / 2 : -size / 2);

                    if (axis == 0) {
                        xOffset = fixed;
                        yOffset = (random.nextDouble() - 0.5) * size;
                        zOffset = (random.nextDouble() - 0.5) * size;
                    } else if (axis == 1) {
                        yOffset = fixed;
                        xOffset = (random.nextDouble() - 0.5) * size;
                        zOffset = (random.nextDouble() - 0.5) * size;
                    } else {
                        zOffset = fixed;
                        xOffset = (random.nextDouble() - 0.5) * size;
                        yOffset = (random.nextDouble() - 0.5) * size;
                    }

                    X = pos.getX() + 0.5 + xOffset;
                    Y = pos.getY() + 0.5 + yOffset;
                    Z = pos.getZ() + 0.5 + zOffset;

                    world.addParticleClient(particle, X, Y, Z, 0, 0, 0);
                    break;
            }

        }
    }


        /**
         * Checks if the block in any direction isn't the specified block.
         * <p>
         *
         * It is unnecessary while {@link #nextBlockIs} exists. It won't be documented on GitHub Wiki.
         *
         * @param world The world in which the check is performed.
         * @param pos The position to check adjacent blocks from.
         * @param block The block to compare against adjacent blocks.
         * @return True if at least one adjacent block is not the same as the specified block, false otherwise.
         */
    public static boolean nextBlockIsExcept(World world, BlockPos pos, Block block) {
        BlockPos[] directions = {
                pos.up(),
                pos.down(),
                pos.north(),
                pos.south(),
                pos.east(),
                pos.west()
        };

        for (BlockPos adjacentPos : directions) {
            if (!world.isClient() && world.getBlockState(adjacentPos).getBlock() != block) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the block in the specified direction isn't the specified block.
     *
     * @param world the world in which the block is located
     * @param pos the position of the current block
     * @param block the block that is compared against
     * @param direction the direction in which to check for a block
     * @return true if the block in the specified direction is not the same as the given block, false otherwise
     */
    public static boolean nextBlockIsExcept(World world, BlockPos pos, Block block, Direction direction) {
        return switch (direction) {
            case UP -> world.getBlockState(pos.add(0, 1, 0)).getBlock() != block;
            case DOWN -> world.getBlockState(pos.add(0, -1, 0)).getBlock() != block;
            case NORTH -> world.getBlockState(pos.add(0, 0, 1)).getBlock() != block;
            case SOUTH -> world.getBlockState(pos.add(0, 0, -1)).getBlock() != block;
            case EAST -> world.getBlockState(pos.add(1, 0, 0)).getBlock() != block;
            case WEST -> world.getBlockState(pos.add(-1, 0, 0)).getBlock() != block;
        };
    }


    /**
     * Checks if the specified block exists in any of the six adjacent positions <p>
     * (up, down, north, south, east, west) relative to the given position in the world.
     *
     * @param world the world in which the block check is performed
     * @param pos the position around which to check for the specified block
     * @param block the block to check for in the adjacent positions
     * @return true if the specified block is found in one of the adjacent positions; false otherwise
     */
    public static boolean nextBlockIs(World world, BlockPos pos, Block block){
        BlockPos[] directions = {
                pos.up(),
                pos.down(),
                pos.north(),
                pos.south(),
                pos.east(),
                pos.west()
        };

        for (BlockPos adjacentPos : directions) {
            if (!world.isClient() && world.getBlockState(adjacentPos).getBlock() == block) {
                return true;
            }
        }
        return false;
    }


    /**
     * Checks if the block in the specified direction from the given position is the specified block.
     *
     * @param world the world in which the block is located
     * @param pos the position of the current block
     * @param block the block that is compared against
     * @param direction the direction in which to check for a block
     * @return true if the block in the specified direction is not the same as the given block, false otherwise
     */
    public static boolean nextBlockIs(World world, BlockPos pos, Block block, Direction direction) {
        return switch (direction) {
            case UP -> world.getBlockState(pos.add(0, 1, 0)).getBlock() == block;
            case DOWN -> world.getBlockState(pos.add(0, -1, 0)).getBlock() == block;
            case NORTH -> world.getBlockState(pos.add(0, 0, 1)).getBlock() == block;
            case SOUTH -> world.getBlockState(pos.add(0, 0, -1)).getBlock() == block;
            case EAST -> world.getBlockState(pos.add(1, 0, 0)).getBlock() == block;
            case WEST -> world.getBlockState(pos.add(-1, 0, 0)).getBlock() == block;
        };
    }

    private final String modId;
    public BlockEvents(String modId){
        this.modId = modId;
    }

    /**
     * @param block Is a block that will drop an in item on break.
     * @param entityType Is the entity to spawn.
     * @param toolTag The tool tag that the item player holds is tagged
     */
    public void addEntityOnBreak(@NotNull Block block, @NotNull EntityType<?> entityType, TagKey<Item> toolTag){

        Map<Block, Object> updateMap = BlockSettingClasses.SpawnEntityHashMap;

        updateMap.put(block, new BlockSettingClasses.SpawnEntity(entityType, toolTag, 100, 1));

        BlockSettingClasses.GeneralSettingsMap.put(modId, updateMap);
    }

    /**
     * @param block Is a block that will drop an in item on break
     * @param entityType Is the entity to spawn.
     * @param toolTag The tool tag that the item player holds is tagged
     * @param rate Chance to drop the item
     */
    public void addEntityOnBreak(@NotNull Block block, @NotNull EntityType<?> entityType, TagKey<Item> toolTag, float rate){

        Map<Block, Object> updateMap = BlockSettingClasses.SpawnEntityHashMap;

        updateMap.put(block, new BlockSettingClasses.SpawnEntity(entityType, toolTag, rate, 1));

        BlockSettingClasses.GeneralSettingsMap.put(modId, updateMap);
    }

    /**
     * @param block Is a block that will drop an in item on break.
     * @param power is the power of the explosion.
     * @param toolTag The tool tag that the item player holds is tagged.
     */
    public void explodeOnBreak(@NotNull Block block, float power, TagKey<Item> toolTag){

        Map<Block, Object> updateMap = BlockSettingClasses.SpawnEntityHashMap;

        updateMap.put(block, new BlockSettingClasses.Explode(power, toolTag, 100, false));

        BlockSettingClasses.GeneralSettingsMap.put(modId, updateMap);
    }

    /**
     * @param block Is a block that will drop an in item on break.
     * @param power is the power of the explosion.
     * @param toolTag The tool tag that the item player holds is tagged.
     * @param rate Chance to drop the item.
     */
    public void explodeOnBreak(@NotNull Block block, float power, TagKey<Item> toolTag, float rate){

        Map<Block, Object> updateMap = BlockSettingClasses.SpawnEntityHashMap;

        updateMap.put(block, new BlockSettingClasses.Explode(power, toolTag, rate, false));

        BlockSettingClasses.GeneralSettingsMap.put(modId, updateMap);
    }

    /**
     * @param block Is a block that will drop an in item on break.
     * @param power is the power of the explosion.
     * @param toolTag The tool tag that the item player holds is tagged.
     * @param rate Chance to drop the item.
     * @param spreadFire Sets if the explosion must spread fire around.
     */
    public void explodeOnBreak(@NotNull Block block, float power, TagKey<Item> toolTag, float rate, boolean spreadFire){

        Map<Block, Object> updateMap = BlockSettingClasses.SpawnEntityHashMap;

        updateMap.put(block, new BlockSettingClasses.Explode(power, toolTag, rate, spreadFire));

        BlockSettingClasses.GeneralSettingsMap.put(modId, updateMap);
    }

    /**
     * @param block Is a block that will drop an in item on break
     * @param entityType Is the entity to spawn.
     * @param toolTag The tool tag that the item player holds is tagged
     * @param rate Chance to drop the item
     * @param amount How many items the block will drop
     */
    public void addEntityOnBreak(@NotNull Block block, @NotNull EntityType<?> entityType, TagKey<Item> toolTag, float rate, int amount){

        Map<Block, Object> updateMap = BlockSettingClasses.SpawnEntityHashMap;

        updateMap.put(block, new BlockSettingClasses.SpawnEntity(entityType, toolTag, rate, amount));

        BlockSettingClasses.GeneralSettingsMap.put(modId, updateMap);
    }

    /**
     * @param block Is a block that will drop an in item on break
     * @param item Is an item that the block will drop on break
     * */
    public void dropItemOnBreak(@NotNull Block block,@NotNull Item item, TagKey<Item> toolTag){
        Map<Block, Object> updateMap = BlockSettingClasses.SpawnEntityHashMap;

        updateMap.put(block, new BlockSettingClasses.DropSettings(item, toolTag, 100, 1, false, 1,false));

        BlockSettingClasses.GeneralSettingsMap.put(modId, updateMap);
    }

    /**
     * @param block Is a block that will drop an in item on break
     * @param item Is an item that the block will drop on break
     * @param rate Chance to drop the item
     */
    public void dropItemOnBreak(@NotNull Block block, @NotNull Item item, TagKey<Item> toolTag, float rate){
        Map<Block, Object> updateMap = BlockSettingClasses.SpawnEntityHashMap;

        updateMap.put(block, new BlockSettingClasses.DropSettings(item, toolTag, rate, 1, false, 1,false));

        BlockSettingClasses.GeneralSettingsMap.put(modId, updateMap);
    }

    /**
     * @param block Is a block that will drop an in item on break
     * @param item Is an item that the block will drop on break
     * @param rate Chance to drop the item
     * @param amount How many items the block will dropv
     */
    public void dropItemOnBreak(@NotNull Block block, @NotNull Item item, TagKey<Item> toolTag, float rate, int amount){
        Map<Block, Object> updateMap = BlockSettingClasses.SpawnEntityHashMap;

        updateMap.put(block, new BlockSettingClasses.DropSettings(item, toolTag, rate, amount, false, 1,false));

        BlockSettingClasses.GeneralSettingsMap.put(modId, updateMap);
    }

    /**
     * @param block Is a block that will drop an in item on break
     * @param item Is an item that the block will drop on break
     * @param rate Chance to drop the item
     * @param amount How many items the block will drop
     * @param randomDrops make the block drop a random amount
     */
    public void dropItemOnBreak(@NotNull Block block, @NotNull Item item, TagKey<Item> toolTag, float rate, int amount, boolean randomDrops){
        Map<Block, Object> updateMap = BlockSettingClasses.SpawnEntityHashMap;

        updateMap.put(block, new BlockSettingClasses.DropSettings(item, toolTag, rate, amount, randomDrops, 1,false));

        BlockSettingClasses.GeneralSettingsMap.put(modId, updateMap);
    }

    /**
     * @param block Is a block that will drop an in item on break
     * @param item Is an item that the block will drop on break
     * @param rate Chance to drop the item
     * @param amount How many items the block will drop
     * @param randomDrops make the block drop a random amount
     * @param minDrop Minimum amount of items that will be dropped
     */
    public void dropItemOnBreak(@NotNull Block block, @NotNull Item item, TagKey<Item> toolTag, float rate, int amount, boolean randomDrops, int minDrop){
        Map<Block, Object> updateMap = BlockSettingClasses.SpawnEntityHashMap;

        updateMap.put(block, new BlockSettingClasses.DropSettings(item, toolTag, rate, amount, randomDrops, minDrop,false));

        BlockSettingClasses.GeneralSettingsMap.put(modId, updateMap);
    }

    /**
     * @param dropsInCreative The item can be dropped in creative mode?
     * @param block Is a block that will drop an in item on break
     * @param item Is an item that the block will drop on break
     * @param rate Chance to drop the item
     */
    public void dropItemOnBreak(boolean dropsInCreative, @NotNull Block block, @NotNull Item item, TagKey<Item> toolTag,  float rate){
        Map<Block, Object> updateMap = BlockSettingClasses.SpawnEntityHashMap;

        updateMap.put(block, new BlockSettingClasses.DropSettings(item, toolTag, 100, 1, false, 1,dropsInCreative));

        BlockSettingClasses.GeneralSettingsMap.put(modId, updateMap);
    }

    /**
     * @param dropsInCreative The item can be dropped in creative mode?
     * @param block Is a block that will drop an in item on break
     * @param item Is an item that the block will drop on break
     * @param rate Chance to drop the item
     * @param amount How many items the block will drop
     */
    public void dropItemOnBreak(boolean dropsInCreative, @NotNull Block block, @NotNull Item item, TagKey<Item> toolTag, float rate, int amount){
        Map<Block, Object> updateMap = BlockSettingClasses.SpawnEntityHashMap;

        updateMap.put(block, new BlockSettingClasses.DropSettings(item, toolTag, rate, amount, false, 1,dropsInCreative));

        BlockSettingClasses.GeneralSettingsMap.put(modId, updateMap);
    }

    /**
     * @param dropsInCreative The item can be dropped in creative mode?
     * @param block Is a block that will drop an in item on break
     * @param item Is an item that the block will drop on break
     * @param rate Chance to drop the item
     * @param amount How many items the block will drop
     * @param randomDrops make the block drop a random amount
     */
    public void dropItemOnBreak(boolean dropsInCreative, @NotNull Block block, @NotNull Item item, TagKey<Item> toolTag, float rate, int amount, boolean randomDrops){
        Map<Block, Object> updateMap = BlockSettingClasses.SpawnEntityHashMap;

        updateMap.put(block, new BlockSettingClasses.DropSettings(item, toolTag, rate, amount, randomDrops, 1,dropsInCreative));

        BlockSettingClasses.GeneralSettingsMap.put(modId, updateMap);
    }

    /**
     * @param dropsInCreative The item can be dropped in creative mode?
     * @param block Is a block that will drop an in item on break
     * @param item Is an item that the block will drop on break
     * @param rate Chance to drop the item
     * @param amount How many items the block will drop
     * @param randomDrops make the block drop a random amount
     * @param minDrop Minimum amount of items that will be dropped
     */
    public void dropItemOnBreak(boolean dropsInCreative, @NotNull Block block, @NotNull Item item, TagKey<Item> toolTag, float rate, int amount, boolean randomDrops, int minDrop){
        Map<Block, Object> updateMap = BlockSettingClasses.SpawnEntityHashMap;

        updateMap.put(block, new BlockSettingClasses.DropSettings(item, toolTag, rate, amount, randomDrops, minDrop,dropsInCreative));

        BlockSettingClasses.GeneralSettingsMap.put(modId, updateMap);
    }


    /// ----------------------------------------------------------------------------------------------------------------
    /**
     * @param world It's the world that will be executing this method
     * @param pos Is the block position
     * @param isRaining Check if raining at block
     * @param skyVisible Check if sky visible for the block
     * @param biomeHasPrecipitation Check if actual biome has precipitation
     * @return It returns true if its raining, the sky is visible and if the biome has precipitation
     */
    public static boolean isRainingOnBlock(@NotNull ServerWorld world, BlockPos pos, boolean isRaining, boolean skyVisible, boolean biomeHasPrecipitation) {
        return world.isRaining() == isRaining &&
                world.isSkyVisible(pos) == skyVisible &&
                world.getBiome(pos).value().hasPrecipitation() == biomeHasPrecipitation;
    }

    /**
     * @param world It's the world that will be executing this method
     * @param pos Is the block position
     * @return It returns true if it's raining, the sky is visible and if the biome has precipitation
     */
    public static boolean isRainingOnBlock(@NotNull ServerWorld world, BlockPos pos) {
        return world.isRaining()
                && world.isSkyVisible(pos)
                && world.getBiome(pos).value().hasPrecipitation();
    }

    /**
     * @param world Current world that the block will explode
     * @param pos Explosion position, block position
     * @param explosionPower Power of the explosion
     * @param fire If true the explosion will add fire around
     */
    public static void explode(@NotNull ServerWorld world, BlockPos pos, float explosionPower, boolean fire) {
        world.createExplosion(null, pos.getX() + 0.5f, pos.getY() + 0.4f, pos.getZ() + 0.5f, explosionPower, true, World.ExplosionSourceType.TNT);
    }

    /**
     * @param world Current world that the block will explode
     * @param pos Explosion position, block position
     * @param explosionPower Power of the explosion
     * @param fire If true the explosion will add fire around
     * @param sourceType Type of source that the explosion will use
     */
    public static void explode(@NotNull ServerWorld world, BlockPos pos, float explosionPower, boolean fire, World.ExplosionSourceType sourceType) {
        world.createExplosion(null, pos.getX() + 0.5f, pos.getY() + 0.4f, pos.getZ() + 0.5f, explosionPower, true, sourceType);
    }
    /**
     * @param world Current world that the block will explode
     * @param pos Explosion position, block position
     * @param explosionPower Power of the explosion
     * @param fire If true the explosion will add fire around
     * @param replaceSourceBlockWithAir If true the explosion will replace the source block with air
     */
    public static void explode(@NotNull ServerWorld world, BlockPos pos, float explosionPower, boolean fire, boolean replaceSourceBlockWithAir) {
        if(replaceSourceBlockWithAir){
            world.setBlockState(pos, Blocks.AIR.getDefaultState());}
        world.createExplosion(null, pos.getX() + 0.5f, pos.getY() + 0.4f, pos.getZ() + 0.5f, explosionPower, true, World.ExplosionSourceType.TNT);
    }

    /**
     * @param world Current world that the block will explode
     * @param pos Explosion position, block position
     * @param explosionPower Power of the explosion
     * @param fire If true the explosion will add fire around
     * @param replaceSourceBlockWithAir If true the explosion will replace the source block with air
     * @param sourceType Type of source that the explosion will use
     */
    public static void explode(@NotNull ServerWorld world, BlockPos pos, float explosionPower, boolean fire, boolean replaceSourceBlockWithAir, World.ExplosionSourceType sourceType) {
        if(replaceSourceBlockWithAir){
            world.setBlockState(pos, Blocks.AIR.getDefaultState());}
        world.createExplosion(null, pos.getX() + 0.5f, pos.getY() + 0.4f, pos.getZ() + 0.5f, explosionPower, true, sourceType);
    }

    /**
     * @param world Current world that the block will explode
     * @param x Explosion position, x position
     * @param y Explosion position, y position
     * @param z Explosion position, z position
     * @param explosionPower Power of the explosion
     * @param fire If true the explosion will add fire around
     * @param replaceSourceBlockWithAir If true the explosion will replace the source block with air
     */
    public static void explode(@NotNull ServerWorld world, double x, double y, double z, float explosionPower, boolean fire, boolean replaceSourceBlockWithAir) {
        if(replaceSourceBlockWithAir){
            world.setBlockState(new BlockPos((int) x,(int) y,(int) z), Blocks.AIR.getDefaultState());}
        world.createExplosion(null, x + 0.5f, y + 0.4f, z + 0.5f, explosionPower, true, World.ExplosionSourceType.TNT);
    }

    /**
     * @param world Current world that the block will explode
     * @param x Explosion position, x position
     * @param y Explosion position, y position
     * @param z Explosion position, z position
     * @param explosionPower Power of the explosion
     * @param fire If true the explosion will add fire around
     * @param replaceSourceBlockWithAir If true the explosion will replace the source block with air
     * @param sourceType Type of source that the explosion will use
     */
    public static void explode(@NotNull ServerWorld world, double x, double y, double z, float explosionPower, boolean fire, boolean replaceSourceBlockWithAir, World.ExplosionSourceType sourceType) {
        if(replaceSourceBlockWithAir){
            world.setBlockState(new BlockPos((int) x,(int) y,(int) z), Blocks.AIR.getDefaultState());}
        world.createExplosion(null, x + 0.5f, y + 0.4f, z + 0.5f, explosionPower, true, sourceType);
    }


    /**----------------------------------------------------------------------------------------------------------------
     * !!DO NOT USE, THIS MAY CAUSE YOUR MOD AND OTHER TO MALFUNCTION!!
     * <p>
     * This class if for internal use only
     */
    public static void BlockEventRegister(ModID id) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length > 2) {
            String clasWhoCallThis = stackTrace[2].getClassName();
            if(!clasWhoCallThis.equals("baspig.apis.utils.Baspig_utils") && !Objects.equals(id.toString(), "baspig_utils")){

                String r = ConsoleColors.RESET;
                String red = ConsoleColors.RED;
                String yellow = ConsoleColors.YELLOW;
                String lightBlue = ConsoleColors.BLUE_BRIGHT;

                BP.fancyLog(   "BaspigUtils.BlockEvents."+yellow+"class"+r, lightBlue+"BlockEventRegister"+r,
                        "        If you see this message "+yellow+"twice"+r+", it is a " +red+ "problem" +r+
                                   "\n        Other/s mod are calling it. \n It's being called by method: " +yellow+ clasWhoCallThis);
            }
        }



        PlayerBlockBreakEvents.AFTER.register((world, playerEntity, blockPos, blockState, blockEntity) -> {

            if(BlockSettingClasses.GeneralSettingsMap.isEmpty()) return; //Stops the method if not content is found, or it's null.

            Block block = blockState.getBlock(); // Sets the comparator Block

            /// This manages all Maps of setting classes
            /// Creating an instance-like executer
            for(String modIdKeys : BlockSettingClasses.GeneralSettingsMap.keySet()){
                Map<Block, Object> innerMap = BlockSettingClasses.GeneralSettingsMap.get(modIdKeys);

                /// Search for each settings type entry
                for(Map.Entry<Block, Object> entry : innerMap.entrySet()){
                    /// Selected Explode type:
                    if(block == entry.getKey() && entry.getValue() instanceof BlockSettingClasses.Explode settings){
                        if(ItemEvents.playerHasInHand(playerEntity, settings.toolTag)
                                && GeneralUtils.probability(settings.rate)
                                && world instanceof ServerWorld serverWorld){
                            BlockEvents.explode(serverWorld, blockPos, settings.power, settings.fireSpread);
                        }
                    }
                    /// Selected Spawn Entity type:
                    if(block == entry.getKey() && entry.getValue() instanceof BlockSettingClasses.SpawnEntity settings){
                        if(ItemEvents.playerHasInHand(playerEntity, settings.toolTag)
                                && GeneralUtils.probability(settings.rate)
                                && world instanceof ServerWorld serverWorld){
                            EntityEvents.generate(serverWorld, blockPos, settings.entityType);
                        }
                    }
                    /// Selected Drop Item type:
                    if(block == entry.getKey() && entry.getValue() instanceof BlockSettingClasses.DropSettings settings){
                        if(ItemEvents.playerHasInHand(playerEntity, settings.toolTag)
                                && GeneralUtils.probability(settings.rate)
                                && world instanceof ServerWorld serverWorld){
                            if(GeneralUtils.probability(settings.rate)){
                                int extra = (settings.quantity > settings.minDrop) ? random.nextInt(settings.quantity - settings.minDrop) : 0;
                                int dropAmount = settings.randomDrops ? settings.minDrop + extra : settings.minDrop;

                                if(playerEntity instanceof ServerPlayerEntity && settings.dropsOnCreative && (playerEntity).isCreative()){
                                    for (int i = 0; i < dropAmount; i++) {
                                        ItemEvents.createWorldItem(world, blockPos, settings.item.getDefaultStack());
                                    }
                                }
                                if(playerEntity instanceof ServerPlayerEntity && !settings.dropsOnCreative && !(playerEntity).isCreative()){
                                    for (int i = 0; i < dropAmount; i++) {
                                        ItemEvents.createWorldItem(world, blockPos, settings.item.getDefaultStack());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }
    /// ----------------------------------------------------------------------------------------------------------------
}
