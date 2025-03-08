package baspig.apis.utils.txt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class ReadTextFile {

    /**
     * @param fileToRead This is the file name to read, don't need path of file extension
     * @return It returns in a String the file content
     */
    public static String fileRead(String fileToRead){
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
    public static String fileRead(String path, String fileToRead){
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
}
