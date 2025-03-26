package baspig.apis.utils.files;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings("unused")
public class FileUtils {
    /**
     * This method quickly trims and checks that if the that isn't empty, adds a "/" at the right of the path
     * @param path the path to be checked
     * @return The fixed path
     * <pre>{@code
     * //If path isn't empty
     * String path = "config/mod Id";
     * path = fixPath(path);
     *
     * //Result: "config/modId/"
     *
     * //If path is empty
     * String path = "";
     * path = fixPath(path);
     *
     * //Result: ""
     * }</pre>
     */
    public static String fixPath(String path){
        if (!path.isEmpty()) {
            return path.trim() + "/";
        }
        return "";
    }

    /**
     * It checks two Object lists (for JSON), if their length are the same does nothing, else throws an exception.
     *
     * @param keys The first list, keys to check
     * @param values The second list, values to check <p>
     * @throws  IllegalArgumentException If lists doesn't have the same length
     */
    public static void checkJSONArgsLength(@NotNull List<String> keys, @NotNull List<Object> values){
        if (keys.size() != values.size()) {
            throw new IllegalArgumentException("|Baspig-Utils:generic-check| Keys and values lists must have the same length");
        }
    }

    /**
     * It checks two Object lists (for JSON), if their length are the same does nothing, else throws an exception.
     *
     * @param keys The first list, keys to check
     * @param values The second list, values to check <p>
     * @param logTitle It adds a title to the log message, E.g: <p>
     * <pre>{@code "|generic-check| Keys and values lists must have the same length"}</pre>
     *
     * @throws IllegalArgumentException If lists doesn't have the same length
     */
    public static void checkJSONArgsLength(List<String> keys, List<Object> values, String logTitle){
        if (keys.size() != values.size()) {
            throw new IllegalArgumentException("|" + logTitle + "| Keys and values lists must have the same length");
        }
    }
}
