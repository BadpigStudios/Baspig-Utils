package baspig.apis.utils.util.pos;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.joml.Math;
import org.joml.Vector3f;

import java.util.List;

@SuppressWarnings("unused")
public class PosUtils {
    /**
     * It converts the first 3 double array index values to a Vec3d
     *
     * @param array The given array to convert to Vec3d.
     * @return A new Vec3d from the given array
     */
    public static Vec3d getVec3dFromArray(double[] array){
        return new Vec3d(array[0], array[1], array[2]);
    }

    /**
     * It converts the first 3 float array index values to a Vec3d
     *
     * @param array The given array to convert to Vec3d.
     * @return A new Vec3d from the given array
     */
    public static Vec3d getVec3dFromArray(float[] array){
        return new Vec3d(array[0], array[1], array[2]);
    }

    /**
     * It converts the first 3 int array index values to a Vec3d
     *
     * @param array The given array to convert to Vec3d.
     * @return A new Vec3d from the given array
     */
    public static Vec3d getVec3dFromArray(int[] array){
        return new Vec3d(array[0], array[1], array[2]);
    }

    /**
     * It converts the first 3 float array index values to a Vector3f
     *
     * @param array The given array to convert to Vector3f.
     * @return A new Vector3f from the given array
     */
    public static Vector3f getVector3fFromArray(float[] array){
        return new Vector3f(array[0], array[1], array[2]);
    }

    /**
     * It converts the first 3 int array index values to a Vector3f
     *
     * @param array The given array to convert to Vector3f.
     * @return A new Vector3f from the given array
     */
    public static Vector3f getVector3fFromArray(int[] array){
        return new Vector3f(array[0], array[1], array[2]);
    }

    /**
     * It converts a Vector3f to a new int array
     *
     * @param vector3f The Vector3f that will be converted to an int array
     * @return An int array made of the Vector3f
     */
    public static int[] getArrayFromVector3f(Vector3f vector3f){
        return new int[]{
                (int) vector3f.x, (int) vector3f.y, (int) vector3f.z
        };
    }

    /**
     * It converts a Vector3f to a new int array and multiplies its values
     *
     * @param vector3f The Vector3f that will be converted to an int array
     * @return An int array made of the Vector3f
     */
    public static int[] getArrayFromVector3f(Vector3f vector3f, int multiplyBy){
        return new int[]{
                (int) vector3f.x *multiplyBy, (int) vector3f.y *multiplyBy, (int) vector3f.z *multiplyBy
        };
    }

    /**
     * It converts some Vector3f to a new int array and multiplies its values.
     * <p>
     * It takes four vectors and creates a single int array
     *
     * @param ve1 first parameter to add to the array
     * @param ve2 second parameter to add to the array
     * @param ve3 third parameter to add to the array
     * @param ve4 fourth parameter to add to the array
     *
     * <pre>{@code
     * ve1.x, ve1.y, ve1.z, //First 3 values are for the first vector
     * ve2.x, ve2.y, ve2.z, //Second 3 values are for the second vector
     * ve3.x, ve3.y, ve3.z, //Third 3 values are for the third vector
     * ve4.x, ve4.y, ve4.z //Fourth 3 values are for the fourth vector} </pre>
     * @return An int array made of the Vector3f
     */
    public static int[] getArrayFromVectors3f(Vector3f ve1, Vector3f ve2, Vector3f ve3, Vector3f ve4){
        return new int[]{
                (int) ve1.x, (int) ve1.y, (int) ve1.z,
                (int) ve2.x, (int) ve2.y, (int) ve2.z,
                (int) ve3.x, (int) ve3.y, (int) ve3.z,
                (int) ve4.x, (int) ve4.y, (int) ve4.z
        };
    }

    /**
     * @return Just a quick 3 length array
     */
    public static int[] empty3Array(){
        return new int[] {0,0,0};
    }


    /**
     * Divides each value of the array by the given value
     *
     * @param array The array to operate with
     * @param divideBy The value to divide by
     * @return new array with each value divided by the given value
     */
    public static float[] divideArrayBy(float[] array, float divideBy){
        int length = 0;
        float[] newArray = new float[array.length];

        for (float f : array){
            newArray[length++] = f;
        }
        return newArray;
    }

    /**
     * Divides the values of the vector by the given value
     *
     * @param vector3f The vector to operate with
     * @param divideBy The value to divide by
     * @return a new Vector3f with each value divided by the given value
     */
    public static Vector3f divideVector3fBy(Vector3f vector3f, float divideBy){
        return new Vector3f(vector3f.x /divideBy, vector3f.y /divideBy, vector3f.z /divideBy);
    }

    /**
     * Converts a BlockPos into a array
     *
     * @param pos The BlockPos to convert
     * @return a new array with the BlockPos values
     */
    public static int[] getArrayFromBlockPos(BlockPos pos){
        return new int[]{pos.getX(), pos.getY(), pos.getZ()};
    }

    /**
     * Converts the first 3 values from the array into a BlockPos
     *
     * @param array The array to read
     * @return a new BlockPos with the array values
     */
    public static BlockPos getBlockPosFromArray(int[] array){
        return new BlockPos(array[0], array[1], array[2]);
    }

    /**
     * Centers the position
     *
     * @param pos the position to center
     * @return the center position
     */
    public static float fixedCenteredAxis(float pos){
        return pos > 0 ? pos + 0.5f : pos - 0.5f;
    }

    /**
     * Centers the position
     *
     * @param pos the position to center
     * @return the center position
     */
    public static double fixedCenteredAxis(double pos){
        return pos > 0 ? pos + 0.5f : pos - 0.5f;
    }

    /**
     * This snap the pos to the block grid rounding it up or down
     *
     * @param pos The position to snap
     * @param roundDown If the pos will be round up or down
     * @return the fixed position
     */
    public static float fixPosToGrid(float pos, boolean roundDown){
        if (roundDown) {
            return pos > 0 ? pos - 0.5f : pos + 0.5f;
        } else {
            return pos > 0 ? pos + 0.5f : pos - 0.5f;
        }
    }

    public static List<Vector3f> fixVectorsWithDirection(Vector3f vec1, Vector3f vec2, Vector3f vec3, Vector3f vec4, Direction dir) {
        Vector3f origin = new Vector3f(vec1);

        java.util.function.BiFunction<Vector3f, boolean[], Vector3f> fix = (v, d) -> new Vector3f(
                fixPosToGrid(v.x, d[0]),
                fixPosToGrid(v.y, d[1]),
                fixPosToGrid(v.z, d[2])
        );

        boolean[][] downs = switch (dir) {
            case EAST -> // +X
                    new boolean[][]{
                            {true, false, true},
                            {true, false, true},
                            {true, false, false},
                            {true, false, false}
                    };
            case WEST -> // -X
                    new boolean[][]{
                            {false, false, false},
                            {false, false, false},
                            {false, false, true},
                            {false, false, true}
                    };
            case SOUTH -> // +Z
                    new boolean[][]{
                            {false, false, true},
                            {false, false, true},
                            {true, false, true},
                            {true, false, true}
                    };
            case NORTH -> // -Z
                    new boolean[][]{
                            {true, false, false},
                            {true, false, false},
                            {false, false, false},
                            {false, false, false}
                    };
            case UP -> // +Y
                    new boolean[][]{
                            {true, false, true},
                            {false, false, true},
                            {false, false, false},
                            {true, false, false}
                    };
            case DOWN -> // -Y
                    new boolean[][]{
                            {false, false, false},
                            {true, true, false},
                            {true, true, true},
                            {false, false, true}
                    };
        };

        Vector3f v1 = fix.apply(vec1, downs[0]);
        Vector3f v2 = fix.apply(vec2, downs[1]);
        Vector3f v3 = fix.apply(vec3, downs[2]);
        Vector3f v4 = fix.apply(vec4, downs[3]);

        v1.sub(origin);
        v2.sub(origin);
        v3.sub(origin);
        v4.sub(origin);
        if(dir == Direction.UP){
            v1.add(0,1,0);
            v4.add(0,1,0);
        }
        return List.of(v1, v2, v3, v4);
    }

    public static Vector3f calculateRelativeCenter(Vector3f firstVector, Vector3f secondVector){
        return new Vector3f(
                firstVector.x + secondVector.x / 2,
                firstVector.y + secondVector.y / 2,
                firstVector.z + secondVector.z / 2
        );
    }

    public static Vector3f roundVector3f(RoundMode mode, Vector3f vector){
        if(mode.equals(RoundMode.Up))
            return new Vector3f(
                    Math.ceil(vector.x),
                    Math.ceil(vector.y),
                    Math.ceil(vector.z)
            );
        else if(mode.equals(RoundMode.Down))
            return new Vector3f(
                Math.floor(vector.x),
                Math.floor(vector.y),
                Math.floor(vector.z));
        else return vector;
    }

    public static Vector3f roundVector3f( Vector3f vector){
        return new Vector3f(
                vector.x > 5 ? Math.ceil(vector.x) : Math.floor(vector.x),
                vector.y > 5 ? Math.ceil(vector.y) : Math.floor(vector.y),
                vector.z > 5 ? Math.ceil(vector.z) : Math.floor(vector.z)
        );
    }

    public enum RoundMode{
        Up,
        Down
    }

    public static Vec3d vector3fToVec3d(Vector3f vector){
        return new Vec3d(vector);
    }
}
