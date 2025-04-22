package baspig.apis.utils.util;

public class RenderUtils {
    /**
     * This convert two light levels into a unique int value
     *
     * @param skyLight The skyLight level
     * @param blockLight The blockLight level
     * @return A single int to both values
     */
    public static int getVertexLightLevelFromInteger(int skyLight, int blockLight){
        return (skyLight << 20) | (blockLight << 4);
    }

    /**
     * it returns the original light values
     *
     * @param bitCompose The value of the light var
     * @return a new int array with both original light values in it. <p>
     * First to skyLight.
     * Second to blockLight.
     */
    public static int[] getIntegerFromVertexLightLevel(int bitCompose){
        return new int[] {(bitCompose >> 20) & 0xF, (bitCompose >> 4) & 0xF};
    }
}
