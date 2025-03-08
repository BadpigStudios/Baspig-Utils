package baspig.apis.utils.generator;

import java.io.File;
import java.io.IOException;

public class TextFile {

    /**
     * @param fileName This only needs the file name, it will automatically put a txt fill into "config/"
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
}
