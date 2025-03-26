package baspig.apis.utils.util;

import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;


@SuppressWarnings("unused")
public class DimensionData {
    public static boolean check(Dimension dimension, ServerWorld serverWorld){

        RegistryKey<World> dimensionKey = serverWorld.getRegistryKey();

        return dimensionKey == World.OVERWORLD && dimension == Dimension.OVERWORLD ||
                dimensionKey == World.NETHER && dimension == Dimension.NETHER ||
                dimensionKey == World.END && dimension == Dimension.END;
    }

    public static boolean check(Dimension dimension, World world){

        RegistryKey<World> dimensionKey = world.getRegistryKey();

        return dimensionKey == World.OVERWORLD && dimension == Dimension.OVERWORLD ||
                dimensionKey == World.NETHER && dimension == Dimension.NETHER ||
                dimensionKey == World.END && dimension == Dimension.END;
    }
}
