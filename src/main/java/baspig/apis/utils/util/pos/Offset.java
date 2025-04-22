package baspig.apis.utils.util.pos;

import net.minecraft.util.math.BlockPos;

import java.util.Random;

/**
 * The Offset class represents a 3D offset with x, y, and z coordinates.
 * It optionally supports adding a random offset to the coordinates.
 *
 * @author Baspig_
 */
@SuppressWarnings("unused")
public class Offset {
    private final Random random = new Random();

    private final double x;
    private final double y;
    private final double z;

    private final double xOffset;
    private final double yOffset;
    private final double zOffset;

    private final double minRandomOffset;
    private final double maxRandomOffset;

    private final boolean hasRandomOffset;

    /**@param x It is the offset parameter on X.
     * @param y It is the offset parameter on Y.
     * @param z It is the offset parameter on Z.*/
    public Offset(int x, int y, int z) {
        this.hasRandomOffset = false;
        this.minRandomOffset = 0;
        this.maxRandomOffset = 1;
        this.x = x;
        this.y = y;
        this.z = z;

        this.xOffset = 0;
        this.yOffset = 0;
        this.zOffset = 0;
    }

    /**@param x It is the offset parameter on X.
     * @param y It is the offset parameter on Y.
     * @param z It is the offset parameter on Z.
     * @param randomOffset Adds a random offset between 0 and the custom value.*/
    public Offset(int x, int y, int z, double randomOffset) {
        this.hasRandomOffset = true;
        this.minRandomOffset = 0;
        this.maxRandomOffset = randomOffset;

        this.xOffset = random.nextDouble(maxRandomOffset);
        this.yOffset = random.nextDouble(maxRandomOffset);
        this.zOffset = random.nextDouble(maxRandomOffset);

        this.x = x + xOffset;
        this.y = y + yOffset;
        this.z = z + zOffset;
    }

    /**@param x It is the offset parameter on X.
     * @param y It is the offset parameter on Y.
     * @param z It is the offset parameter on Z.
     * @param minRandomOffset Adds a random offset starting from this value.
     * @param maxRandomOffset Adds a random offset ending in this value.*/
    public Offset(int x, int y, int z, double minRandomOffset, double maxRandomOffset) {

        if(minRandomOffset > maxRandomOffset){
            minRandomOffset = 0;
            maxRandomOffset = 1;
        }
        this.hasRandomOffset = true;
        this.minRandomOffset = minRandomOffset;
        this.maxRandomOffset = maxRandomOffset;

        this.xOffset = random.nextDouble(minRandomOffset,maxRandomOffset);
        this.yOffset = random.nextDouble(minRandomOffset,maxRandomOffset);
        this.zOffset = random.nextDouble(minRandomOffset,maxRandomOffset);

        this.x = x + xOffset;
        this.y = y + yOffset;
        this.z = z + zOffset;
    }

    /**@param x It is the offset parameter on X.
     * @param y It is the offset parameter on Y.
     * @param z It is the offset parameter on Z.*/
    public Offset(double x, double y, double z) {
        this.hasRandomOffset = false;
        this.minRandomOffset = 0;
        this.maxRandomOffset = 1;

        this.xOffset = 0;
        this.yOffset = 0;
        this.zOffset = 0;

        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**@param x It is the offset parameter on X.
     * @param y It is the offset parameter on Y.
     * @param z It is the offset parameter on Z.
     * @param randomOffset Adds a random offset between 0 and the custom value.*/
    public Offset(double x, double y, double z, double randomOffset) {
        this.hasRandomOffset = true;
        this.minRandomOffset = 0;
        this.maxRandomOffset = randomOffset;

        this.xOffset = random.nextDouble(maxRandomOffset);
        this.yOffset = random.nextDouble(maxRandomOffset);
        this.zOffset = random.nextDouble(maxRandomOffset);

        this.x = x + xOffset;
        this.y = y + yOffset;
        this.z = z + zOffset;
    }

    /**@param x It is the offset parameter on X.
     * @param y It is the offset parameter on Y.
     * @param z It is the offset parameter on Z.
     * @param minRandomOffset Adds a random offset starting from this value.
     * @param maxRandomOffset Adds a random offset ending in this value.*/
    public Offset(double x, double y, double z, double minRandomOffset, double maxRandomOffset) {

        if(minRandomOffset > maxRandomOffset){
            minRandomOffset = 0;
            maxRandomOffset = 1;
        }

        this.hasRandomOffset = true;
        this.minRandomOffset = minRandomOffset;
        this.maxRandomOffset = maxRandomOffset;

        this.xOffset = random.nextDouble(minRandomOffset,maxRandomOffset);
        this.yOffset = random.nextDouble(minRandomOffset,maxRandomOffset);
        this.zOffset = random.nextDouble(minRandomOffset,maxRandomOffset);

        this.x = x + xOffset;
        this.y = y + yOffset;
        this.z = z + zOffset;
    }

    /**@return Returns the offset on X (double).*/
    public double getX() {
        return x;
    }

    /**@return Returns the offset on X.(int)*/
    public int getXInt() {
        return (int) x;
    }

    /**@return Returns the offset on Y.*/
    public double getY() {
        return y;
    }

    /**@return Returns the offset on Y.(int)*/
    public int getYInt() {
        return (int) y;
    }

    /**@return Returns the offset on Z.*/
    public double getZ() {
        return z;
    }

    /**@return Returns the offset on Z.(int)*/
    public int getZInt() {
        return (int) z;
    }

    public boolean isHasRandomOffset() {
        return hasRandomOffset;
    }

    public double getMinRandomOffset() {
        return minRandomOffset;
    }

    public double getMaxRandomOffset() {
        return maxRandomOffset;
    }

    public double getXOffset() {
        return xOffset;
    }

    public double getYOffset() {
        return yOffset;
    }

    public double getZOffset() {
        return zOffset;
    }

    public int getXOffsetToInt() {
        return (int) xOffset;
    }

    public int getYOffsetToInt() {
        return (int) yOffset;
    }

    public int getZOffsetToInt() {
        return (int) zOffset;
    }

    public BlockPos getBlockPos(){
        return new BlockPos((int) x, (int) y, (int) z);
    }

}
