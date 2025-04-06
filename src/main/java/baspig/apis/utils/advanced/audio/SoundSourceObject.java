package baspig.apis.utils.advanced.audio;

import baspig.apis.utils.files.EasyJSON;
import baspig.apis.utils.files.UpdateMode;
import baspig.apis.utils.util.Dimension;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;

import java.util.List;

/**
 * Manages the info about the source object and writes to save it into the minecraft folder
 */
public class SoundSourceObject {

    private static final String specialKeyAddition = "xor_Baspig_utils"; ///For a minimum default encrypt hardness

    private static String NAME, ///Name of the source Object
                          ENCRYPT_KEY; ///Encrypt and Decrypt key

    private static BlockPos CORDS; ///COORDINATES

    private static String PATH; ///Path to the file, especial folders
    private static SoundEvent SOUND; ///sound
    private static Dimension DIMENSION; ///sound

    private static SoundCategory SOUND_CATEGORY;
    private static int DURATION;


    /***
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
    protected SoundSourceObject(String modId, String name, String encryptKey,
                                           int x, int y, int z,
                                           SoundEvent sound, SoundCategory category, int durationInTicks,
                                           Dimension dimension){
        NAME = name + "-sound-source";
        ENCRYPT_KEY = encryptKey + specialKeyAddition;
        PATH = "config/" + modId + "/objects/sources/sound";

        CORDS = new BlockPos(x,y,z);
        SOUND = sound;
        DURATION = durationInTicks;
        DIMENSION = dimension;

        SOUND_CATEGORY = category;
        write();
    }

    /**
     * This writes/saves the info about the object using my own built JSON system with an encrypt key.
     */
    private void write(){
        EasyJSON.write_encrypt(PATH, NAME,
                List.of(
                        "position",
                        "dimension",
                        "sound",
                        "sound_category",
                        "max_ticks",
                        "repeatable"
                ),
                List.of(EasyJSON.nestObject(
                        List.of("x","y","z"), List.of(CORDS.getX(), CORDS.getY(), CORDS.getZ())),
                        DIMENSION,
                        SOUND,
                        SOUND_CATEGORY,
                        DURATION,
                        false
                ), UpdateMode.ADD_AND_UPDATE, ENCRYPT_KEY);
    }
}