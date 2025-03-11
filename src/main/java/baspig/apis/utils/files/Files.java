package baspig.apis.utils.files;

import baspig.apis.utils.Baspig_utils;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
public class Files {
    /**
     * It will automatically try to create a file if it can't will log that it exist.
     * @param path Path the file. E.g: "config/my_mod"
     * @param file Name of the file. E.g; "should_spawn_spiders.txt"
     */
    private void create(String path ,String file){
        try {
            File myObj = new File(path + "/"+ file);
            if (myObj.createNewFile()) {
                Baspig_utils.LOGGER.error("File created: {}", myObj.getName());
            } else {
                Baspig_utils.LOGGER.error("File already exists.");
            }
        } catch (IOException ignore) {
            Baspig_utils.LOGGER.error("An error occurred with file: {}{}", path, file);
        }
    }
}
