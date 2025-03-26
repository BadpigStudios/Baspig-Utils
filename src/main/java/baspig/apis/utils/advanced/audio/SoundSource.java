package baspig.apis.utils.advanced.audio;

import baspig.apis.utils.util.Dimension;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

@SuppressWarnings("unused")
public class SoundSource {

    public static void registerAndRun(String modId, String name, String encryptKey,
                                int x, int y, int z,
                                SoundEvent sound, SoundCategory category, int durationInTicks,
                                Dimension dimension){
        new SoundSourceObject(
                modId, name, encryptKey,
                x, y, z,
                sound, category,
                durationInTicks,
                dimension);

        SoundSourceInitRegister.register(name,modId, encryptKey);
    }

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

    public static void run(String name, String MOD_ID, String encryptKey){
        SoundSourceInitRegister.register(name,MOD_ID, encryptKey);
    }
}