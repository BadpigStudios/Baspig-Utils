package baspig.apis.utils.advanced.audio;

import baspig.apis.utils.files.EasyJSON;
import baspig.apis.utils.util.Dimension;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class SoundSourceRun {
    protected SoundSourceRun(String MOD_ID, String name, String encrypt_key){
        String ENCRYPT_KEY = encrypt_key + "xor_Baspig_utils";
        String I_PATH = "config/" + MOD_ID + "/objects/sources";
        String I_NAME = name + "-sound-source";

        String dimension = (String) EasyJSON.read(I_PATH, I_NAME, "dimension", ENCRYPT_KEY);

        assert dimension != null;
        Dimension dimension1 = Dimension.valueOf(dimension.toUpperCase());

        String modId = EasyJSON.readString(I_PATH, I_NAME, "sound.id.namespace", ENCRYPT_KEY);
        String soundId = EasyJSON.readString(I_PATH, I_NAME, "sound.id.path", ENCRYPT_KEY);

        int maxTicksResult = EasyJSON.readInt(I_PATH, I_NAME, "max_ticks", ENCRYPT_KEY);
        boolean repeatable = EasyJSON.readBoolean(I_PATH, I_NAME, "repeatable", ENCRYPT_KEY);

        SoundEvent sound = SoundEvent.of(Identifier.of(modId, soundId));

        String soundCategory_object = (String) EasyJSON.read(I_PATH, I_NAME, "sound_category", ENCRYPT_KEY);
        assert soundCategory_object != null;
        SoundCategory soundCategory = SoundCategory.valueOf(soundCategory_object.toUpperCase());

        SourceObjectLoader.addSourceObject(loadPos(I_PATH, I_NAME, ENCRYPT_KEY),dimension1, sound, soundCategory, maxTicksResult, repeatable);
    }

    private static BlockPos loadPos(String path, String name, String ENCRYPT_KEY){
        int x = EasyJSON.readInt(path, name, "position.x", ENCRYPT_KEY);
        int y = EasyJSON.readInt(path, name, "position.y", ENCRYPT_KEY);
        int z = EasyJSON.readInt(path, name, "position.z", ENCRYPT_KEY);
        return new BlockPos(x, y, z);
    }
}