package baspig.apis.utils.util;

import net.minecraft.util.math.Direction;
import org.joml.Vector3f;

public class BPString {

    /**
     * This converts a Vector3f into a new String like <b>packetStringDataToString</b>
     *
     * @param character The character to put into each String
     * @param vector3f The vector to convert into a String
     * @return a new String made of the Vector3f
     */
    public static String packVector3fToString(char character ,Vector3f vector3f){
        return packetFloatsDataToString(character, vector3f.x, vector3f.y, vector3f.z);
    }

    /**
     * This obtains the String value of de given index value
     *
     * @param dataString The String to search in
     * @param index The index of the data into the String
     * @return the specified String into the first String
     */
    public static String getStringArgument(String dataString, short index){
        String[] data = dataString.split(";");
        return index > 0 || index < data.length ? data[index] : "";
    }

    /**
     * This obtains the String value of de given index value and split by the given character
     *
     * @param dataString The String to search in
     * @param index The index of the data into the String
     * @param character Is the character to "split" the given String
     * @return the specified String into the first String
     */
    public static String getStringArgument(String dataString, short index, String character){
        String[] data = dataString.split(character);
        return index > 0 || index < data.length ? data[index] : "";
    }

    /**
     * This obtains the String value of de given index value and X,Y or Z index of a 3 vector separated by ':'
     *
     * @param dataString The String to search in
     * @param index The index of the data into the String
     * @param axis This specifies the Axis of a 3 vector string to read(x,y,z)
     * @return the specified String into the first String
     */
    public static String getStringArgument(String dataString, short index, Direction.Axis axis){
        String splitData = dataString.split(";")[index];

        switch (axis){
            case X -> {
                return splitData.split(":")[0];
            }
            case Y -> {
                return splitData.split(":")[1];
            }
            case Z -> {
                return splitData.split(":")[2];
            }
            case null, default -> {
                return dataString.split(";")[0].split(":")[0] != null
                        ? dataString.split(";")[0].split(":")[0]
                        : "no_data";
            }
        }
    }

    public static Vector3f getVector3fFromStringArgument(String dataString, short index){
        return new Vector3f(
                Float.parseFloat(getStringArgument(dataString, index, Direction.Axis.X)),
                Float.parseFloat(getStringArgument(dataString, index, Direction.Axis.Y)),
                Float.parseFloat(getStringArgument(dataString, index, Direction.Axis.Z))
        );
    }

    /**
     * This builds a new String from given String arguments already converted to String.
     * <p>
     * Specially made for Entity track data, to transfer more info
     * <p>
     * Make sure to check the final string output
     *
     * <pre>{@code
     * Example:
     * LOGGER.info(
     *      BPString.packetDataToString(
     *          ';', // The character to separate the String parameters
     *          "1", "aa", "555") //All the parameters to convert into the list
     * );
     * }</pre>
     * @param stringSeparator Is the character that separates the string parameters
     * @param strings The arguments that will be converted into a new String
     * @return the built String
     */
    public static String packetStringDataToString(Character stringSeparator, String... strings){
        StringBuilder finalString = new StringBuilder();
        for (String string : strings){
            if(finalString.isEmpty()){
                finalString.append(string);
            }else {
                finalString.append(stringSeparator).append(string);
            }
        }
        return !finalString.isEmpty() ? finalString.toString() : "can't create string";
    }

    /**
     * This builds a new String from given Integers already converted to String.
     * <p>
     * Specially made for Entity track data, to transfer more info
     * <p>
     * Make sure to check the final string output
     *
     * <pre>{@code
     * Example:
     * LOGGER.info(
     *      BPString.packetDataToString(
     *          ';', // The character to separate the String parameters
     *          1, 85, 555) //All the parameters to convert into the list
     * );
     * }</pre>
     * @param stringSeparator Is the character that separates the string parameters
     * @param ints The Integer arguments that will be converted into a new String
     * @return the built String
     */
    public static String packeIntDataToString(Character stringSeparator, int... ints){
        StringBuilder finalString = new StringBuilder();
        for (int integer : ints){
            if(finalString.isEmpty()){
                finalString.append(integer);
            }else {
                finalString.append(stringSeparator).append(integer);
            }
        }
        return !finalString.isEmpty() ? finalString.toString() : "can't create string";
    }

    /**
     * This builds a new String from given Floats already converted to String.
     * <p>
     * Specially made for Entity track data, to transfer more info
     * <p>
     * Make sure to check the final string output
     *
     * <pre>{@code
     * Example:
     * LOGGER.info(
     *      BPString.packetDataToString(
     *          ';', // The character to separate the String parameters
     *          1, 0.58f, 555.5f) //All the parameters to convert into the list
     * );
     * }</pre>
     * @param stringSeparator Is the character that separates the string parameters
     * @param ints The Float arguments that will be converted into a new String
     * @return the built String
     */
    public static String packetFloatsDataToString(Character stringSeparator, float... ints){
        StringBuilder finalString = new StringBuilder();
        for (float integer : ints){
            if(finalString.isEmpty()){
                finalString.append(integer);
            }else {
                finalString.append(stringSeparator).append(integer);
            }
        }
        return !finalString.isEmpty() ? finalString.toString() : "can't create string";
    }

    /**
     * This builds a new String from given Objects converting them to string.
     * <p>
     * Specially made for Entity track data, to transfer more info
     * <p>
     * Make sure to check the final string output
     *
     * <pre>{@code
     * Example:
     * LOGGER.info(
     *      BPString.packetDataToString(
     *          ';', // The character to separate the String parameters
     *          1, true," 555.5f") //All the parameters to convert into the list
     * );
     * }</pre>
     * @param stringSeparator Is the character that separates the string parameters
     * @param objects The Float arguments that will be converted into a new String
     * @return the built String
     */
    public static String packetObjectDataToString(Character stringSeparator, Object... objects){
        StringBuilder finalString = new StringBuilder();
        for (Object object : objects){
            object = String.valueOf(object);

            if(finalString.isEmpty()){
                finalString.append(object);
            }else {
                finalString.append(stringSeparator).append(object);
            }
        }
        return !finalString.isEmpty() ? finalString.toString() : "can't create string";
    }

}
