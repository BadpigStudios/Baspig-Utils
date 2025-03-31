package baspig.apis.utils.util.block;

import java.util.Random;

/**
 * The Offset class represents a 3D offset with x, y, and z coordinates.
 * It optionally supports adding a random offset to the coordinates.
 *
 * @Author Baspig_
 */
@SuppressWarnings("unused")
public class Offset {

    private final Random random = new Random();
    private final int x;
    private final int y;
    private final int z;

    private final double minRandomOffset;
    private final double maxRandomOffset;

    private boolean hasRandomOffset = false;

    /**@param x It is the offset parameter on X.
     * @param y It is the offset parameter on Y.
     * @param z It is the offset parameter on Z.*/
    public Offset(int x, int y, int z) {
        this.minRandomOffset = 0;
        this.maxRandomOffset = 1;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**@param x It is the offset parameter on X.
     * @param y It is the offset parameter on Y.
     * @param z It is the offset parameter on Z.
     * @param randomOffset Adds a random offset between 0 and the custom value.*/
    public Offset(int x, int y, int z, double randomOffset) {
        this.hasRandomOffset = true;
        this.minRandomOffset = 0;
        this.maxRandomOffset = randomOffset;
        this.x = x;
        this.y = y;
        this.z = z;
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
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**@return Returns the offset on X (double).*/
    public double getX() {
        if(hasRandomOffset){
            return x + random.nextDouble(minRandomOffset,maxRandomOffset);
        }
        return x;
    }

    /**@return Returns the offset on X.(int)*/
    public int getXInt() {
        if(hasRandomOffset){
            return x + random.nextInt((int) minRandomOffset, (int) maxRandomOffset);
        }
        return x;
    }

    /**@return Returns the offset on Y.*/
    public double getY() {
        if(hasRandomOffset){
            return y + random.nextDouble(minRandomOffset,maxRandomOffset);
        }
        return y;
    }

    /**@return Returns the offset on Y.(int)*/
    public int getYInt() {
        if(hasRandomOffset){
            return y + random.nextInt((int) minRandomOffset, (int) maxRandomOffset);
        }
        return y;
    }

    /**@return Returns the offset on Z.*/
    public double getZ() {
        if(hasRandomOffset){
            return z + random.nextDouble(minRandomOffset,maxRandomOffset);
        }
        return z;
    }

    /**@return Returns the offset on Z.(int)*/
    public int getZInt() {
        if(hasRandomOffset){
            return z + random.nextInt((int) minRandomOffset, (int) maxRandomOffset);
        }
        return z;
    }
}
