package baspig.apis.utils.advanced.audio;

import baspig.apis.utils.util.Dimension;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

/**
 * This is a short way to register and run all the system for sound source objects
 *
 * @Author Baspig_
 */
@SuppressWarnings("unused")
public class SoundSource {

    /***
     * This just is an intermediary to the writer/saver function.
     *
     * @param modId The id of the mod that creates new source objects.
     * @param name Name of the source object.
     * @param encryptKey The key to make sure user can't modify it. Al least easily.
     * @param x The X coordinate of the object in the world.
     * @param y The Y coordinate of the object in the world.
     * @param z The Z coordinate of the object in the world.
     * @param sound The actual sound that will be played in the world in the specified coordinates.
     * @param category Category of the given sound.
     * @param durationInTicks Offset in ticks of the sound delay between each play.
     * @param dimension Dimension where the sound will be played.
     */
    public static void registerAndRun(String modId, String name, String encryptKey,
                                int x, int y, int z,
                                SoundEvent sound, SoundCategory category, int durationInTicks,
                                Dimension dimension){
        /// Creates a new SoundSourceObject with the given values.
        new SoundSourceObject(
                modId, name, encryptKey,
                x, y, z,
                sound, category,
                durationInTicks,
                dimension);

        /// Initializes, the Source Object
        SoundSourceInitRegister.register(name,modId, encryptKey);
    }

    /// A separated version of registerAndRun
    public static void register(String modId, String name, String encryptKey,
                              int x, int y, int z,
                              SoundEvent sound, SoundCategory category, int durationInTicks,
                              Dimension dimension){
        new  SoundSourceObject(
                modId, name, encryptKey,
                x, y, z,
                sound, category,
                durationInTicks,
                dimension
        );
    }

    /// A separated version of registerAndRun
    public static void run(String name, String MOD_ID, String encryptKey){
        SoundSourceInitRegister.register(name,MOD_ID, encryptKey);
    }
}