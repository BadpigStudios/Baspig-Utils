package baspig.apis.utils.txt;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

/**
 * This class is for fast .txt files writing
 * Json support in the future
 */
@SuppressWarnings("unused")
public class WriteTextFile {

    /**This only works with text of a single line
     * @param fileToWrite This is the file name to be written
     * @param textToWrite It will be the text for read, if not found exactly what is assigned it will
     * @param append This means that the text will be added if it's true else will overwrite
     */
    public static void fileWriter(String fileToWrite, String textToWrite, boolean append){
        try {
            FileWriter writer = new FileWriter("config/" + fileToWrite + ".txt", append);
            writer.write(textToWrite);
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while executing fileWriter");
        }
    }

    /**This only works with text of a single line
     * @param fileToWrite This is the file name to be written
     * @param path This is the path to the file without the final slash bar, example: config/mod/rain
     * @param textToWrite It will be the text for read, if not found exactly what is assigned it will
     * @param append This means that the text will be added if it's true else will overwrite
     */
    public static void fileWriter(String path, String fileToWrite, String textToWrite, boolean append){
        if(Objects.equals(path, "")){
            try {
                FileWriter writer = new FileWriter(path + fileToWrite + ".txt", append);
                writer.write(textToWrite);
                writer.close();
            } catch (IOException e) {
                System.out.println("An error occurred while executing fileWriter");
            }
        }else {
            try {
                FileWriter writer = new FileWriter(path + "/" + fileToWrite + ".txt", append);
                writer.write(textToWrite);
                writer.close();
            } catch (IOException e) {
                System.out.println("An error occurred while executing fileWriter");
            }
        }
    }
}
