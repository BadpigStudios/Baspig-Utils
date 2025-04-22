package baspig.apis.utils.util.nbt;

import net.minecraft.nbt.NbtCompound;

public class NbtUtils {

    /**
     * This method simplifies the process of getting nbt 'Optional' values
     *
     * @param nbtCompound The nbt instance
     * @param key The name of the nbt key
     * @return Returns an int value if there is one, else returns 0
     */
    public static int getIntValue(NbtCompound nbtCompound, String key){
        if(nbtCompound.getInt(key).isPresent()){
            return nbtCompound.getInt(key).get();
        }
        return 0;
    }

    /**
     * This method simplifies the process of getting nbt 'Optional' values
     *
     * @param nbtCompound The nbt instance
     * @param key The name of the nbt key
     * @return Returns a byte value if there is one, else returns 0
     */
    public static int getByteValue(NbtCompound nbtCompound, String key){
        if(nbtCompound.getByte(key).isPresent()){
            return nbtCompound.getByte(key).get();
        }
        return 0;
    }

    /**
     * This method simplifies the process of getting nbt 'Optional' values
     *
     * @param nbtCompound The nbt instance
     * @param key The name of the nbt key
     * @return Returns a String value if there is one, else returns {@code null}
     */
    public static String getStringValue(NbtCompound nbtCompound, String key){
        if(nbtCompound.getString(key).isPresent()){
            return nbtCompound.getString(key).get();
        }
        return null;
    }

    /**
     * This method simplifies the process of getting nbt 'Optional' values
     * <p>
     * It will never return {@code null}
     *
     * @param nbtCompound The nbt instance
     * @param key The name of the nbt key
     * @return Returns a String value if there is one, else returns "NotData"
     */
    public static String getStringValueNotNull(NbtCompound nbtCompound, String key){
        if(nbtCompound.getString(key).isPresent()){
            return nbtCompound.getString(key).get();
        }
        return "NotData";
    }
}
