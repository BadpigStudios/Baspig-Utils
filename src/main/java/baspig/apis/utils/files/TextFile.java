package baspig.apis.utils.files;

import java.io.*;
import java.util.Objects;

/**
 * This class is for generate, read, and write .txt files
 * In the future I will add json system.
 * @author Baspig_
 */
@SuppressWarnings("unused")
public class TextFile {

    /**@param fileName This only needs the file name, it will automatically put a .txt fill into "config/"
     */
    public static void create(String fileName){
        for (int i = 1; i > 0; i--){
            try {
                File myObj = new File("config/" + fileName +".txt");
                if (myObj.createNewFile()) {
                    System.out.println("Files created: " + myObj.getName());
                } else {
                    System.out.println("Files already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
            }
        }
    }

    /**@param path This is the folders where the file will be put in, example: mods/my_mod/rain
     *                Is not necessary to put the final slash bar.
     * @param fileName This is the file name and don't need to put the extension, example: season_type
     */
    public static void create(String path, String fileName){
        for (int i = 1; i > 0; i--){
            try {
                File myObj = new File( path + "/" + fileName +".txt");
                if (myObj.createNewFile()) {
                    System.out.println("Files created: " + myObj.getName());
                } else {
                    System.out.println("Files already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
            }
        }
    }

    /**
     * @param fileToRead This is the file name to read, don't need path of file extension
     * @return It returns in a String the file content
     */
    public static String read(String fileToRead){
        try {
            FileReader reader = new FileReader("config/" + fileToRead + ".txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            if((line = bufferedReader.readLine()) != null) {
                return line;
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred while executing fileRead");
        }
        return null;
    }


    /**
     * @param fileToRead This is the file name to read, don't need path of file extension
     * @param path This is the folders where the file will be read, example: config/mod/rain
     *             Is not necessary to put the final slash bar.
     * @return It returns in a String the file content
     */
    public static String read(String path, String fileToRead){
        if(Objects.equals(path, "")){
            try {
                FileReader reader = new FileReader(path + fileToRead+ ".txt");
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line;
                if((line = bufferedReader.readLine()) != null) {
                    return line;
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("An error occurred while executing fileRead");
            }
        }else {
            try {
                FileReader reader = new FileReader(path + "/" + fileToRead+ ".txt");
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line;
                if((line = bufferedReader.readLine()) != null) {
                    return line;
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("An error occurred while executing fileRead");
            }
        }
        return null;
    }

    /**This only works with text of a single line
     * @param fileToWrite This is the file name to be written
     * @param textToWrite It will be the text for read, if not found exactly what is assigned it will
     * @param append This means that the text will be added if it's true else will overwrite
     */
    public static void writer(String fileToWrite, String textToWrite, boolean append){
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
    public static void writer(String path, String fileToWrite, String textToWrite, boolean append){
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
