package baspig.apis.utils.files;

import baspig.apis.utils.util.BP;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static baspig.apis.utils.util.BP.LOG;

/**
 * This class is for generate, read, and write .txt files
 * In the future I will add json system.
 * @author Baspig_
 */
@SuppressWarnings("unused")
public class TextFile {


    /**@param path This is the folders where the file will be put in, example: mods/my_mod/rain
     *                Is not necessary to put the final slash bar.
     * @param fileName This is the file name and don't need to put the extension, example: season_type
     */
    public static void create(String path, String fileName){
        for (int i = 1; i > 0; i--){
            try {
                File myObj = new File( path + "/" + (fileName.replaceAll("/", "")) + ".txt");
                if (myObj.createNewFile()) {
                    System.out.println("Files created: " + myObj.getName());
                } else {
                    System.out.println("Files already exists.");
                }
            } catch (IOException e) {
                BP.LOG.info("An error occurred while executing fileWriter: {}\nCaused by: {}", e.getMessage(), e.getCause());
            }
        }
    }

    /**
     * @param fileToRead This is the file name to read, don't need path of file extension
     * @return It returns in a String the file content
     */
    public static String read(String fileToRead){
        try {
            FileReader reader = new FileReader("config/" + (fileToRead.replaceAll("/", "")) + ".txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            if((line = bufferedReader.readLine()) != null) {
                return line;
            }
            reader.close();
        } catch (IOException e) {
            BP.LOG.info("An error occurred while executing fileWriter: {}\nCaused by: {}", e.getMessage(), e.getCause());
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
                BP.LOG.info("An error occurred while executing fileWriter: {}\nCaused by: {}", e.getMessage(), e.getCause());
            }
        }else {
            try {
                FileReader reader = new FileReader(path + "/" + (fileToRead.replaceAll("/", "")) + ".txt");
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line;
                if((line = bufferedReader.readLine()) != null) {
                    return line;
                }
                reader.close();
            } catch (IOException e) {
                BP.LOG.info("An error occurred while executing fileWriter: {}\nCaused by: {}", e.getMessage(), e.getCause());
            }
        }
        return null;
    }

    /**This only works with text of a single line
     * @param fileToWrite This is the file name to be written
     * @param textToWrite It will be the text for read, if not found exactly what is assigned it will
     * @param append This means that the text will be added if it's true else will overwrite
     */
    public static void write(String fileToWrite, String textToWrite, boolean append){
        try {
            File myObj = new File("config/" + (fileToWrite.replaceAll("/", "")) + ".txt");
            if (myObj.createNewFile()) {
                BP.LOG.info("File created successfully");
            }
            FileWriter writer = new FileWriter("config/" + (textToWrite.replaceAll("/", "")) + ".txt", append);
            writer.write(textToWrite);
            writer.close();
        } catch (IOException e) {
            BP.LOG.info("An error occurred while executing fileWriter: {}\nCaused by: {}", e.getMessage(), e.getCause());
        }
    }

    /**This only works with text of a single line
     * @param fileToWrite This is the file name to be written
     * @param path This is the path to the file without the final slash bar, example: config/mod/rain
     * @param textToWrite It will be the text for read, if not found exactly what is assigned it will
     * @param append This means that the text will be added if it's true else will overwrite
     */
    public static void write(String path, String fileToWrite, @NotNull String textToWrite, boolean append){
        FileUtils.fixPath(path);
        try {
            File myObj = new File( path + "/" + (fileToWrite.replaceAll("/", "")) + ".txt");
            if (myObj.createNewFile()) {
                BP.LOG.info("|write| File created successfully");
            }
            FileWriter writer = new FileWriter(path + (textToWrite.replaceAll("/", "")) + ".txt", append);
            writer.write(textToWrite);
            writer.close();
        } catch (IOException e) {
            BP.LOG.info("An error occurred while executing fileWriter: {}\nCaused by: {}", e.getMessage(), e.getCause());
        }
    }


    /**
     * Deletes a TXT file if it exists.
     *
     * @param file The actual file to delete.
     * By default, executed into run folder
     */
    public synchronized static void delete(String file) {
        Path txtFile = Paths.get(file + ".txt");
        try {
            Files.delete(txtFile);
            LOG.info("|txt-delete| Successfully deleted file: {}", txtFile.toAbsolutePath());
        } catch (IOException e) {
            LOG.error("|txt-delete| Failed to delete file: {}", txtFile.toAbsolutePath(), e);
        }
    }

    /**
     * Deletes a TXT file if it exists.
     *
     * @param path Path to the folder where the file is located.
     * @param file The actual file to delete.
     */
    public synchronized static void delete(String path, String file) {
        Path txtFile = Paths.get(path, file + ".txt");
        try {
            Files.delete(txtFile);
            LOG.info("|txt-delete | Successfully deleted file: {}", txtFile.toAbsolutePath());
        } catch (IOException e) {
            LOG.error("|txt-delete| Failed to delete file: {}", txtFile.toAbsolutePath(), e);
        }
    }
}
