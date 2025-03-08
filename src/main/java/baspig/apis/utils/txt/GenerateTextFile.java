package baspig.apis.utils.txt;

import java.io.File;
import java.io.IOException;

public class GenerateTextFile {


    /**@param fileName This only needs the file name, it will automatically put a txt fill into "config/"
     */
    public static void generateFile(String fileName){
        for (int i = 1; i > 0; i--){
            try {
                File myObj = new File("config/" + fileName +".txt");
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
            }
        }
    }

    /**@param path This is the folders where the file will be put in, example: config/mod/rain
     *                Is not necessary to put the final slash bar.
     * @param fileName This is the file name and don't need to put the extension, example: config
     */
    public static void generateFile(String path, String fileName){
        for (int i = 1; i > 0; i--){
            try {
                File myObj = new File( path + "/" + fileName +".txt");
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
            }
        }
    }
}
