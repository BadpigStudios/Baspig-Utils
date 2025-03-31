package baspig.apis.utils.util;

import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;


@SuppressWarnings("unused")
public class DimensionData {

    /**
     * Checks if the given Dimension parameter corresponds to the dimension of the provided. `ServerWorld`.
     *
     * @param dimension    The target dimension to check against (e.g., OVERWORLD, NETHER, END).
     * @param serverWorld  The server world whose dimension is to be validated.
     * @return true if the specified `dimension` matches the dimension of the `serverWorld`, otherwise false.
     */
    public static boolean checkIf(Dimension dimension, ServerWorld serverWorld){
        RegistryKey<World> dimensionKey = serverWorld.getRegistryKey();

        return dimensionKey == World.OVERWORLD && dimension == Dimension.OVERWORLD ||
                dimensionKey == World.NETHER && dimension == Dimension.NETHER ||
                dimensionKey == World.END && dimension == Dimension.END;
    }

    /**
     * Checks if the given Dimension parameter corresponds to the dimension of the provided. `World`.
     *
     * @param dimension    The target dimension to check against (e.g., OVERWORLD, NETHER, END).
     * @param world  The server world whose dimension is to be validated.
     * @return true if the specified `dimension` matches the dimension of the `serverWorld`, otherwise false.
     */
    public static boolean checkIf(Dimension dimension, World world){
        RegistryKey<World> dimensionKey = world.getRegistryKey();

        return dimensionKey == World.OVERWORLD && dimension == Dimension.OVERWORLD ||
                dimensionKey == World.NETHER && dimension == Dimension.NETHER ||
                dimensionKey == World.END && dimension == Dimension.END;
    }

    /**
     * Checks if the dimension in the given world has skylight
     *
     * @param world The current world to check the dimension
     * @return True if it has skylight, false if not
     */
    public static boolean hasSkyLight(World world){
        return world.getDimension().hasSkyLight();
    }

    /**
     * Checks if the dimension in the given world has ceiling
     *
     * @param world The current world to check the dimension
     * @return True if it has ceiling, false if not
     */
    public static boolean hasCeiling(World world){
        return world.getDimension().hasCeiling();
    }

    /**
     * Checks if the dimension in the given world the beds work (Do not explode)
     *
     * @param world The current world to check the dimension
     * @return True if the beds work in it, false if it doesn't
     */
    public static boolean bedWorks(World world){
        return world.getDimension().bedWorks();
    }

    /**
     * Checks if the dimension in the given world is ultra warn dimension.
     *
     * @param world The current world to check the dimension
     * @return True if is ultra warn dimension, false if not.
     */
    public static boolean ultraWarm(World world){
        return world.getDimension().ultrawarm();
    }

    /**
     * Checks if the dimension in the given world has raids.
     *
     * @param world The current world to check the dimension
     * @return True if it has raids, false if not
     */
    public static boolean hasRaids(World world){
        return world.getDimension().hasRaids();
    }
}
