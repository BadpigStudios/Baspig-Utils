package baspig.apis.utils.events.block;

import baspig.apis.utils.util.ShapeType;
import baspig.apis.utils.util.block.Offset;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

/**
 * This generates and manages some data to make easier to handle.
 * @Author : Baspig_
 */
@SuppressWarnings("unused")
public class ParticleSettings{
    private final Random random = new Random();
    private final ParticleEffect particleEffect;
    private final BlockPos blockPos;
    private final ShapeType shapeType;
    private final int amount;
    private final int radius;
    private final int height;
    private final Offset offset;


    /**
     * This lightly process data for particles math
     *
     * @param particleType The particle type to use
     * @param blockPos The block position where shape uses as center
     * @param shapeType The shape type of the particles
     * @param amount The amount of particles per tick to add
     * @param radius The radius of any shape
     */
    public ParticleSettings(ParticleEffect particleType, BlockPos blockPos, ShapeType shapeType, int amount, int radius) {
        this.particleEffect = particleType;
        this.blockPos = blockPos;
        this.shapeType = shapeType;
        this.amount = amount;
        this.radius = radius;
        this.offset = new Offset(0,0,0);
        this.height = 0;
    }


    /**
     * This lightly process data for particles math
     *
     * @param particleType The particle type to use
     * @param blockPos The block position where shape uses as center
     * @param shapeType The shape type of the particles
     * @param amount The amount of particles per tick to add
     * @param radius The radius of any shape
     * @param height Height of the shape
     */
    public ParticleSettings(ParticleEffect particleType, BlockPos blockPos, ShapeType shapeType, int amount, int radius, int height) {
        this.particleEffect = particleType;
        this.blockPos = blockPos;
        this.shapeType = shapeType;
        this.amount = amount;
        this.radius = radius;
        this.offset = new Offset(0,0,0);
        this.height = height;
    }

    /**
     * This lightly process data for particles math
     *
     * @param particleType The particle type to use
     * @param blockPos The block position where shape uses as center
     * @param shapeType The shape type of the particles
     * @param amount The amount of particles per tick to add
     * @param radius The radius of any shape
     * @param offset Offset position of the center of the shape
     */
    public ParticleSettings(ParticleEffect particleType, BlockPos blockPos, ShapeType shapeType, int amount, int radius, Offset offset) {
        this.particleEffect = particleType;
        this.blockPos = blockPos;
        this.shapeType = shapeType;
        this.amount = amount;
        this.radius = radius;
        this.offset = offset;
        this.height = radius;
    }


    /**
     * This lightly process data for particles math
     *
     * @param particleType The particle type to use
     * @param blockPos The block position where shape uses as center
     * @param shapeType The shape type of the particles
     * @param amount The amount of particles per tick to add
     * @param radius The radius of any shape
     * @param height Height of the shape
     * @param offset Offset position of the center of the shape
     */
    public ParticleSettings(ParticleEffect particleType, BlockPos blockPos, ShapeType shapeType, int amount, int radius, int height, Offset offset) {
        this.particleEffect = particleType;
        this.blockPos = blockPos;
        this.shapeType = shapeType;
        this.amount = amount;
        this.radius = radius;
        this.offset = offset;
        this.height = height;
    }

    /**
     * @return This returns the particle type.
     */
    public ParticleEffect getParticleEffect() {
        return particleEffect;
    }

    /**
     * @return This returns the block position with offset, the center of the shape.
     */
    public BlockPos getBlockPos() {
        return blockPos.add(offset.getXInt(), offset.getYInt(), offset.getZInt());
    }

    /**
     * @return This returns the original block position.
     */
    public BlockPos getOriginalBlockPos() {
        return blockPos;
    }

    /**
     * @return The shape type.
     */
    public ShapeType getShapeType() {
        return shapeType;
    }

    /**
     * @return The amount of particles per tick.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @return The radius of the shape.
     */
    public int getRadius() {
        return radius;
    }

    /**
     * @return The diameter of the shape.
     */
    public int getDiameter() {
        return radius * 2;
    }

    /**
     * @return The height of the shape.  If it doesn't have, it returns radius multiplied by 2.
     */
    public int getHeight() {
        return height == 0 ? radius * 2 : height;
    }

    /**
     * @return The original offset values of the shape. If it has.
     */
    public Offset getOffset() {
        return offset;
    }

    /**
     * @return The original offset X value of the shape.  If it has.
     */
    public int getXOffset() {
        return offset.getXInt();
    }

    /**
     * @return The original offset Y value of the shape.  If it has.
     */
    public int getYOffset() {
        return offset.getYInt();
    }

    /**
     * @return The original offset Z value of the shape.  If it has.
     */
    public int getZOffset() {
        return offset.getZInt();
    }

    public double getTheta(){
        return random.nextDouble() * 2 * Math.PI;
    }

    /**
     * @return A random angle phi for spherical coordinates
     */
    public double getSpecialPhi(){
        return Math.acos(2 * random.nextDouble() - 1);
    }

    /**Cubic Radius Scaling<p>
     * This a way to scale and distribute uniformly particles into the sphere shape
     *
     * @return Gives the maths for one dimension of the sphere
     *
     *
     * <pre>{@code double sphere_uniform = particleSettings.getCubicRadiusScaling();
     *
     * //To use later in:
     * X = pos.getX() + 0.5 + sphere_uniform * Math.sin(phi) * Math.cos(theta);}</pre>
     */
    public double getCubicRadiusScaling(){
        return Math.cbrt(random.nextDouble()) * radius;
    }

    /**
     * @return A random angle theta for spherical coordinates
     */
    public double getSpecialTheta(){
        return random.nextDouble() * 2 * Math.PI;
    }

    /**
     * @return Unfixed phi value for circles and spheres
     */
    public double getPhi(){
        return random.nextDouble() * Math.PI;
    }

    /**
     * @return The value of a random point into x circle area.
     */
    public double getCircleX(){
        return getCubicRadiusScaling() * Math.cos(getSpecialTheta());
    }

    /**
     * @return The value of a random point into z circle area,
     */
    public double getCircleZ(){
        return getCubicRadiusScaling() * Math.sin(getSpecialTheta());
    }


    /**
     * @return The area to spawn particles into a square.
     */
    public double getArea(){
        return (random.nextDouble() - 0.5) * getDiameter();
    }
}
