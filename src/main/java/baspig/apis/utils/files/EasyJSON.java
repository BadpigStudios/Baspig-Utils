package baspig.apis.utils.files;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static baspig.apis.utils.util.BP.*;

/**
 * This class is made for quick and fast json file management.
 *
 * @author Baspig_
 */
@SuppressWarnings("unused")
public class EasyJSON {

    private static final SerializationFeature SERIALIZATION_OUTPUT_INDENT = SerializationFeature.INDENT_OUTPUT;

    private static final int JSON_SIMPLE_TYPE = JSONParser.MODE_JSON_SIMPLE;

    /**
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys for the file in list. "key":"value"
     * @param values All the values of the file in list. "key":"value"
     *
     * @throws NullPointerException
     * If any argument is {@code null}
     */
    public synchronized static void write(String path, String file, List<String> keys, List<Object> values) {
        path = FileUtils.fixPath(path);
        FileUtils.checkJSONArgsLength(keys, values, "write");

        try {
            File directory = new File(path);
            if (!directory.exists() && !directory.mkdirs()) {
                LOG.info("Already created - don't worry: {}", directory.getAbsolutePath());
                return;
            }

            Map<String, Object> orderedMap = new LinkedHashMap<>();
            for (int i = 0; i < keys.size(); i++) {
                Object value = values.get(i);

                if (value instanceof JSONObject) {
                    value = new ObjectMapper().readValue(value.toString(), Map.class);
                }
                orderedMap.put(keys.get(i), value);
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SERIALIZATION_OUTPUT_INDENT);

            File outputFile = new File(path + file + ".json");

            if (outputFile.exists()) {
                LOG.info("|write| File already exists: {}", outputFile.getAbsolutePath());
                return;
            }

            mapper.writeValue(outputFile, orderedMap);

            LOG.info("|write| Successfully wrote JSON file: {}", outputFile.getAbsolutePath());

        } catch (IOException e) {
            LOG.info("|write| Failed to write JSON: {}", e.getMessage());
        }
    }

    /**
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys for the file in list. "key":"value"
     * @param values All the values of the file in list. "key":"value"
     * @param mode <pre>{@code
     * UpdateMode:
     * ADD - Adds the new keys
     * UPDATE - Updates existing keys
     * REPLACE - Replaces the whole file
     * NOTHING - Does nothing
     * }</pre>
     *
     * @throws NullPointerException
     * If any argument is {@code null}
     *
     * @exception IllegalArgumentException If lists doesn't have the same length
     */
    public synchronized static void write(String path, String file, List<String> keys, List<Object> values, UpdateMode mode) {
        path = FileUtils.fixPath(path);
        FileUtils.checkJSONArgsLength(keys, values, "write");

        try {
            File directory = new File(path);
            if (!directory.exists() && !directory.mkdirs()) {
                LOG.info("|write-special| Failed to create directory: {}", directory.getAbsolutePath());
                return;
            }

            String fullPath = path + file + ".json";
            File outputFile = new File(fullPath);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SERIALIZATION_OUTPUT_INDENT);

            if (mode == UpdateMode.NOTHING && outputFile.exists()) {
                LOG.info("|write-special| File already exists, skipping due to NOTHING mode: {}", fullPath);
                return;
            }

            Map<String, Object> currentData = new LinkedHashMap<>();

            if (outputFile.exists() && mode != UpdateMode.REPLACE) {
                try {
                    String content = new String(Files.readAllBytes(outputFile.toPath()));
                    currentData = mapper.readValue(content, new TypeReference<>() {});
                } catch (IOException e) {
                    LOG.warn("|write-special| Could not read existing JSON: {}", e.getMessage());
                }
            }

            Map<String, Object> newData = new LinkedHashMap<>();
            for (int i = 0; i < keys.size(); i++) {
                newData.put(keys.get(i), values.get(i));
            }

            boolean changesDetected = false;

            switch (mode) {
                case UPDATE:
                    for (Map.Entry<String, Object> entry : newData.entrySet()) {
                        if (currentData.containsKey(entry.getKey()) && !currentData.get(entry.getKey()).equals(entry.getValue())) {
                            currentData.put(entry.getKey(), entry.getValue());
                            changesDetected = true;
                        }
                    }
                    break;
                case ADD:
                    for (Map.Entry<String, Object> entry : newData.entrySet()) {
                        if (!currentData.containsKey(entry.getKey())) {
                            currentData.put(entry.getKey(), entry.getValue());
                            changesDetected = true;
                        }
                    }
                    break;
                case ADD_AND_UPDATE:
                    for (Map.Entry<String, Object> entry : newData.entrySet()) {
                        if (!currentData.containsKey(entry.getKey()) || !currentData.get(entry.getKey()).equals(entry.getValue())) {
                            currentData.put(entry.getKey(), entry.getValue());
                            changesDetected = true;
                        }
                    }
                    break;
                case REPLACE:
                    currentData = newData;
                    changesDetected = true;
                    break;
            }

            if (!changesDetected) {
                LOG.info("|write-special| No changes detected, skipping write.");
                return;
            }

            mapper.writeValue(outputFile, currentData);
            LOG.info("|write-special| Successfully wrote JSON file: {}", fullPath);
        } catch (IOException e) {
            LOG.warn("|write-special| Failed to write JSON: {}", e.getMessage());
        }
    }

    /**
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys for the file in list. "key":"value"
     * @param values All the values of the file in list. "key":"value"
     * @param mode <pre>{@code
     * UpdateMode:
     * ADD - Adds the new keys
     * UPDATE - Updates existing keys
     * REPLACE - Replaces the whole file
     * NOTHING - Does nothing
     * }</pre>
     * @param ignoredKeys Keys that will be ignored if the file already has them
     *
     * @throws NullPointerException
     * If any argument is {@code null}
     *
     * @exception IllegalArgumentException If lists doesn't have the same length
     */
    public synchronized static void write(String path, String file, List<String> keys, List<Object> values, UpdateMode mode, List<String> ignoredKeys) {
        path = FileUtils.fixPath(path);
        String title = "write-advanced";
        FileUtils.checkJSONArgsLength(keys, values, title);

        try {
            File directory = new File(path);
            if (!directory.exists() && !directory.mkdirs()) {
                LOG.info("{} Failed to create directory: {}", title, directory.getAbsolutePath());
                return;
            }

            String fullPath = path + file + ".json";
            File outputFile = new File(fullPath);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SERIALIZATION_OUTPUT_INDENT);

            if (mode == UpdateMode.NOTHING && outputFile.exists()) {
                LOG.info("{} File already exists, skipping due to NOTHING mode: {}", title, fullPath);
                return;
            }

            Map<String, Object> currentData = new LinkedHashMap<>();

            if (outputFile.exists() && mode != UpdateMode.REPLACE) {
                try {
                    String content = new String(Files.readAllBytes(outputFile.toPath()));
                    currentData = mapper.readValue(content, new TypeReference<>() {});
                } catch (IOException e) {
                    LOG.warn("{} Could not read existing JSON: {}", title, e.getMessage());
                }
            }

            Map<String, Object> newData = new LinkedHashMap<>();
            for (int i = 0; i < keys.size(); i++) {
                if (ignoredKeys.contains(keys.get(i)) && currentData.containsKey(keys.get(i))) {
                    continue;
                }
                newData.put(keys.get(i), values.get(i));
            }

            boolean changesDetected = false;

            switch (mode) {
                case UPDATE:
                    for (Map.Entry<String, Object> entry : newData.entrySet()) {
                        if (currentData.containsKey(entry.getKey()) && !currentData.get(entry.getKey()).equals(entry.getValue())) {
                            currentData.put(entry.getKey(), entry.getValue());
                            changesDetected = true;
                        }
                    }
                    break;
                case ADD:
                    for (Map.Entry<String, Object> entry : newData.entrySet()) {
                        if (!currentData.containsKey(entry.getKey())) {
                            currentData.put(entry.getKey(), entry.getValue());
                            changesDetected = true;
                        }
                    }
                    break;
                case ADD_AND_UPDATE:
                    for (Map.Entry<String, Object> entry : newData.entrySet()) {
                        if (!currentData.containsKey(entry.getKey()) || !currentData.get(entry.getKey()).equals(entry.getValue())) {
                            currentData.put(entry.getKey(), entry.getValue());
                            changesDetected = true;
                        }
                    }
                    break;
                case REPLACE:
                    currentData = newData;
                    changesDetected = true;
                    break;
            }

            if (!changesDetected) {
                LOG.info("{} No changes detected, skipping write.", title);
                return;
            }

            mapper.writeValue(outputFile, currentData);
            LOG.info("{} Successfully wrote JSON file: {}", title, fullPath);
        } catch (IOException e) {
            LOG.warn("{} Failed to write JSON: {}", title, e.getMessage());
        }
    }

    /**
     * Replaces values in an existing JSON file while keeping the order of keys.
     * If the file does not exist, a new one will be created.
     *
     * @param path   Path to the file.
     * @param file   The actual file name (without extension).
     * @param keys   List of keys to be replaced in the JSON file.
     * @param values List of values to be replaced in the JSON file. <p>
     * <p>
     * Example: <pre>{@code key : "sound.id.namespace", "sound.id.path"
     * value: "minecraft", "entity.blaze.death"}</pre>
     */
    public synchronized static void replace(String path, String file,List<String> keys,List<Object> values) {
        path = FileUtils.fixPath(path);
        FileUtils.checkJSONArgsLength(keys, values, "replace-multiple");

        try {
            File directory = new File(path);
            File jsonFile = new File(directory, file + ".json");

            if (!jsonFile.exists()) {
                LOG.info("|replace-multiple| JSON file {} does not exist. No action taken.", jsonFile.getAbsolutePath());
                return;
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SERIALIZATION_OUTPUT_INDENT);

            Map<String, Object> jsonMap = mapper.readValue(jsonFile, new TypeReference<LinkedHashMap<String, Object>>() {});

            for (int i = 0; i < keys.size(); i++) {
                String[] keyPath = keys.get(i).split("\\.");
                setValue(jsonMap, keyPath, values.get(i));
            }

            File outputFile = new File(path + file + ".json");
            mapper.writeValue(outputFile, jsonMap);

        } catch (IOException e) {
            LOG.warn("|replace-multiple|Failed to write JSON file: {}", e.getMessage());
        }
    }

    /**
     * Replaces a value in an existing JSON file while keeping the order of keys.
     * If the file does not exist, a new one will be created.
     *
     * @param path   Path to the file.
     * @param file   The actual file name (without extension).
     * @param key    The key to be replaced in the JSON file.
     * @param value  The value to replace the existing value for the key.
     */
    public synchronized static void replace(String path, String file, String key, Object value) {
        path = FileUtils.fixPath(path);

        try {
            File directory = new File(path);
            File jsonFile = new File(directory, file + ".json");

            if (!jsonFile.exists()) {
                LOG.warn("|replace-single|JSON file {} does not exist. No action taken.", jsonFile.getAbsolutePath());
                return;
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SERIALIZATION_OUTPUT_INDENT);

            Map<String, Object> jsonMap = mapper.readValue(jsonFile, new TypeReference<LinkedHashMap<String, Object>>() {});

            String[] keyPath = key.split("\\.");
            setValue(jsonMap, keyPath, value);

            File outputFile = new File(path + file + ".json");
            mapper.writeValue(outputFile, jsonMap);

        } catch (IOException e) {
            LOG.warn("|replace-single| Failed to write JSON file: {}", e.getMessage());
        }
    }

    /**
     * Replaces a value in an existing JSON file while keeping the order of keys.
     * If the file does not exist, a new one will be created.
     *
     * @param path   Path to the file.
     * @param file   The actual file name (without extension).
     * @param key    The key to be replaced in the JSON file.
     * @param value  The value to replace the existing value for the key.
     * @param encryptKey The encryption key used to decrypt and re-encrypt the JSON file.
     */
    public synchronized static void replace(String path, String file, String key, Object value, String encryptKey) {
        path = FileUtils.fixPath(path);
        try {
            File directory = new File(path);
            File jsonFile = new File(directory, file + ".json");

            if (!jsonFile.exists()) {
                LOG.warn("|replace-multiple-encrypt|JSON file {} does not exist. No action taken.", jsonFile.getAbsolutePath());
                return;
            }

            String encryptedContent = Files.readString(jsonFile.toPath());
            String decryptedContent = xorEncryptAndDecrypt(encryptedContent, encryptKey);

            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            Map<String, Object> jsonMap = mapper.readValue(decryptedContent, new TypeReference<LinkedHashMap<String, Object>>() {});

            String[] keyPath = key.split("\\.");
            setValue(jsonMap, keyPath, value);

            String updatedJson = mapper.writeValueAsString(jsonMap);

            String encryptedJson = xorEncryptAndDecrypt(updatedJson, encryptKey);
            Files.writeString(jsonFile.toPath(), encryptedJson);

        } catch (IOException e) {
            LOG.warn("|replace-multiple-encrypt| Failed to write JSON file: {}", e.getMessage());
        }
    }


    @SuppressWarnings("unchecked")
    private synchronized static void setValue(Map<String, Object> jsonMap, String @NotNull [] keys, Object value) {
        Map<String, Object> currentMap = jsonMap;
        for (int i = 0; i < keys.length - 1; i++) {
            currentMap = (Map<String, Object>) currentMap.get(keys[i]);
        }
        currentMap.put(keys[keys.length - 1], value);
    }


    /**
     * @param path Path to the file.
     * @param file The actual file name.
     * @param key The key whose value needs to be read. "key":"value"
     *
     * @return The value associated with the key, or null if not found.
     */
    public synchronized static @Nullable Object read(String path, String file, @NotNull String key) {
        path = FileUtils.fixPath(path);
        try {
            JSONParser parser = new JSONParser(JSON_SIMPLE_TYPE);
            Object current = parser.parse(new FileReader(path + file + ".json"));

            String[] keyParts = key.split("\\.");
            for (String k : keyParts) {
                if (current instanceof JSONObject jsonObj) {
                    current = jsonObj.get(k);
                } else {
                    LOG.warn("|read| Key '{}' is not a valid JSON object", k);
                    return null;
                }
                if (current == null) {
                    LOG.warn("|read| No key found: {}", k);
                    return null;
                }
            }
            return current;
        } catch (IOException | ParseException e) {
            LOG.warn("|read| Failed to read JSON file: {}", e.getMessage());
        }
        return null;
    }

    /**
     * @param path Path to the file.
     * @param file The actual file name.
     * @param key The key whose value needs to be read. "key":"value"
     *
     * @return The value associated with the key, or null if not found.
     */
    private synchronized static @Nullable Object readObject(String path, String file, String key) {
        path = FileUtils.fixPath(path);
        try {
            JSONParser parser = new JSONParser(JSON_SIMPLE_TYPE);

            Object current = parser.parse(new FileReader(path + file + ".json"));

            if (current instanceof JSONObject jsonNested) {
                current = jsonNested.get(key);
            } else {
                LOG.warn("|readObject| Key '{}' is not a valid JSON object", key);
                return null;
            }

            if (current == null) {
                LOG.warn("|readObject| Key '{}' is not a valid JSON object", key);
                return null;
            }
            return current;

        } catch (IOException | ParseException e) {
            LOG.warn("|readObject| Failed to read JSON file: {}", e.getMessage());
        }
        return null;
    }

    /**
     * This method returns a String, if it isn't a String, it will return "No key string found"
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys that will be read in list. "key":"value"
     */
    public synchronized static String readString(String path, String file, String keys){
        Object result = read(path, file, keys);
        if (result == null) {
            return "|readString| No key string found";
        }
        return switch (result) {
            case String s -> s;
            case JSONObject jsonObject -> jsonObject.toJSONString();
            default -> String.valueOf(result);
        };
    }

    /**
     * This method returns a Byte, if it isn't a Byte, it will return 0
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys that will be read in list. "key":"value"
     */
    public synchronized static byte readByte(String path, String file, String keys){
        Object result = read(path, file, keys);
        if (result instanceof Integer) {
            return ((Integer) result).byteValue();
        }else if (result instanceof Number) {
            return ((Number) result).byteValue();
        }else {
            LOG.warn("|readByte| something went wrong");
            return 0;
        }
    }

    /**
     * This method returns a Short, if it isn't a Short, it will return 0
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys that will be read in list. "key":"value"
     */
    public synchronized static short readShort(String path, String file, String keys){
        Object result = read(path, file, keys);
        if (result instanceof Integer) {
            return ((Integer) result).shortValue();
        }else if (result instanceof Number) {
            return ((Number) result).shortValue();
        }else {
            LOG.warn("|readShort| something went wrong");
            return 0;
        }
    }

    /**
     * This method returns an Integer, if it isn't an Integer, it will return 0
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys that will be read in list. "key":"value"
     */
    public synchronized static int readInt(String path, String file, String keys){
        Object result = read(path, file, keys);
        if (result instanceof Integer) {
            return (Integer) result;
        }else if (result instanceof Number) {
            return ((Number) result).intValue();
        }else {
            LOG.warn("|readInt| something went wrong");
            return 0;
        }
    }

    /**
     * This method returns a Float, if it isn't a Float, it will return 0
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys that will be read in list. "key":"value"
     */
    public static float readFloat(String path, String file, String keys){
        Object result = read(path, file, keys);
        if (result instanceof Integer) {
            return ((Integer) result).floatValue();
        }else if (result instanceof Number) {
            return ((Number) result).floatValue();
        }else {
            LOG.warn("|readFloat| something went wrong");
            return 0;
        }
    }

    /**
     * This method returns a Boolean, if it isn't a Boolean, it will return false
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys that will be read in list. "key":"value"
     */
    public static boolean readBoolean(String path, String file, String keys){
        Object result = read(path, file, keys);
        if(result != null){
            return (boolean) result;
        } else {
            LOG.info("Boolean value from readBoolean is null!");
            return false;
        }
    }

    /**
     * Creates a JSONObject from a list of keys and values into a parent key.
     *
     * @param keys   List of keys for the JSONObject.
     * @param values List of values corresponding to the keys.
     * @return A JSONObject containing the specified data.
     *
     * Method created to avoid
     */
    public synchronized static @NotNull Object nestObject(List<String> keys, List<Object> values) {
        FileUtils.checkJSONArgsLength(keys, values, "nestObject");
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < keys.size(); i++) {
            jsonObject.put(keys.get(i), values.get(i));
        }
        return jsonObject;
    }

    /**
     * Creates a JSON array with values, to be used inside a nested JSON.
     *
     * @param values List of values to be added.
     * @return A JSONArray containing the provided values.
     */
    public synchronized static @NotNull JSONArray writeList(List<Object> values) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(values);
        return jsonArray;
    }

    /**
     * Replaces a value inside a JSON object within a list in a JSON file.
     *
     * @param path     Path to the file.
     * @param file     The actual file name.
     * @param listKeys  Path of keys of the list that contains the objects.
     * @param searchKey The key to search for inside the objects.
     * @param newValue The new value to set.
     */
    @SuppressWarnings("unchecked")
    private synchronized static void replaceListValue(String path, String file, String searchKey, Object newValue, String... listKeys) {
        path = FileUtils.fixPath(path);

        try {
            File jsonFile = new File(path + file + ".json");

            if (!jsonFile.exists()) {
                LOG.info("JSON file {} does not exist. No action taken.", jsonFile.getAbsolutePath());
                return;
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SERIALIZATION_OUTPUT_INDENT);

            Map<String, Object> jsonMap = mapper.readValue(jsonFile, new TypeReference<LinkedHashMap<String, Object>>() {});

            Object listObject = jsonMap;
            for (String key : listKeys) {
                if (listObject instanceof Map<?, ?> map) {
                    listObject = map.get(key);
                } else {
                    LOG.info("|replaceListValue| Key '{}' is not a valid JSON object.", key);
                    return;
                }
            }

            if (listObject instanceof List<?> list) {
                boolean updated = false;

                for (Object obj : list) {
                    if (obj instanceof Map<?, ?> map && map.containsKey(searchKey)) {
                        ((Map<String, Object>) map).put(searchKey, newValue);
                        updated = true;
                        break;
                    }
                }
                if (!updated) {
                    LOG.info("Key '{}' not found in list '{}'", searchKey, listKeys[listKeys.length - 1]);
                    return;
                }
            } else {
                LOG.info("|replaceListValue| The key '{}' does not point to a list.", listKeys[listKeys.length - 1]);
                return;
            }

            mapper.writeValue(jsonFile, jsonMap);
            LOG.info("Successfully updated the JSON file.");

        } catch (IOException e) {
            LOG.info("|replaceListValue| Failed to modify JSON file: {}", e.getMessage());
        }
    }

    /**
     * Reads and returns the entire list from a JSON object inside a list within a JSON file.
     *
     * @param path     Path to the file.
     * @param file     The actual file name.
     * @param keys  The key of the list that contains the objects.
     * @return The list found, null if not found.
     */
    public synchronized static JSONArray readList(String path, String file, String... keys) {
        path = FileUtils.fixPath(path);
        try {
            JSONParser parser = new JSONParser(JSON_SIMPLE_TYPE);

            Object listObject = parser.parse(new FileReader(path + file + ".json"));

            for (String key : keys) {
                if (listObject instanceof JSONObject jsonNested) {
                    listObject = jsonNested.get(key);
                } else {
                    LOG.info("|readList| Key '{}' is not a valid JSON object", key);
                    return null;
                }

                if (listObject == null) {
                    System.out.println("No key found: " + key);
                    return null;
                }
            }

            if (listObject instanceof JSONArray jsonArray) {
                return jsonArray;
            } else {
                LOG.info("|readList| The final object is not a JSONArray.");
                return null;
            }
        } catch (IOException | ParseException e) {
            LOG.info("|readList| Failed to read JSON file: {}", e.getMessage());
        }
        return null;
    }


    /**
     * Deletes a JSON file if it exists.
     *
     * @param path Path to the folder where the file is located.
     * @param file The actual file name.
     */
    public synchronized static void delete(String path, String file) {
        Path jsonFile = Paths.get(path, file + ".json");
        try {
            Files.delete(jsonFile);
            LOG.info("Successfully deleted JSON file: {}", jsonFile.toAbsolutePath());
        } catch (IOException e) {
            LOG.error("Failed to delete JSON file: {}", jsonFile.toAbsolutePath(), e);
        }
    }


    private synchronized static @NotNull String xorEncryptAndDecrypt(@NotNull String input, String key) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            output.append((char)(input.charAt(i) ^ key.charAt(i % key.length())));
        }
        return output.toString();
    }

    /**
     * It just ensures your objects can't be accessed by the user
     *
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys for the file in list. "key":"value"
     * @param values All the values of the file in list. "key":"value"
     * @param encryptKey Special key for file low security encryption
     */
    public synchronized static void write_encrypt(String path, String file, List<String> keys, List<Object> values, String encryptKey) {
        path = FileUtils.fixPath(path);
        FileUtils.checkJSONArgsLength(keys, values, "writeAndEncrypt");

        try {
            File directory = new File(path);
            if (!directory.exists() && !directory.mkdirs()) {
                LOG.info("Failed to create special directory: {}", directory.getAbsolutePath());
                return;
            }

            Map<String, Object> orderedMap = new LinkedHashMap<>();
            for (int i = 0; i < keys.size(); i++) {
                Object value = values.get(i);

                if (value instanceof JSONObject) {
                    value = new ObjectMapper().readValue(value.toString(), Map.class);
                }
                orderedMap.put(keys.get(i), value);
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SERIALIZATION_OUTPUT_INDENT);
            String jsonString = mapper.writeValueAsString(orderedMap);

            String encryptedJson = xorEncryptAndDecrypt(jsonString, encryptKey);

            String fullPath = path + file + ".json";
            File outputFile = new File(fullPath);

            LOG.info(orderedMap.toString());

            File outFile = new File(fullPath);

            Files.writeString(outputFile.toPath(), encryptedJson);

            LOG.info("Successfully wrote JSON encrypted file: {}", outputFile.getAbsolutePath());

        } catch (IOException e) {
            LOG.info("Failed to write encrypted JSON: {}", e.getMessage());
        }
    }

    /**
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys for the file in list. "key":"value"
     * @param values All the values of the file in list. "key":"value"
     * @param mode <pre>{@code
     * UpdateMode:
     * ADD - Adds the new keys
     * UPDATE - Updates existing keys
     * REPLACE - Replaces the whole file
     * NOTHING - Does nothing
     * }</pre>
     * @param encryptionKey Special key for file low level encryption
     *
     * @throws NullPointerException
     * If any argument is {@code null}
     *
     * @exception IllegalArgumentException If lists doesn't have the same length
     */
    public synchronized static void write_encrypt(String path, String file, List<String> keys, List<Object> values, UpdateMode mode, String encryptionKey) {
        path = FileUtils.fixPath(path);
        String title = "writeAndEncrypt";
        FileUtils.checkJSONArgsLength(keys, values, title);

        try {
            File directory = new File(path);
            if (!directory.exists() && !directory.mkdirs()) {
                LOG.info("|{}| Failed to create directory: {}", title, directory.getAbsolutePath());
                return;
            }

            String fullPath = path + file + ".json";
            File outputFile = new File(fullPath);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SERIALIZATION_OUTPUT_INDENT);

            if (mode == UpdateMode.NOTHING && outputFile.exists()) {
                LOG.info("|{}| File already exists, skipping due to NOTHING mode: {}", title, fullPath);
                return;
            }

            Map<String, Object> currentData = new LinkedHashMap<>();

            if (outputFile.exists() && mode != UpdateMode.REPLACE) {
                try {
                    String encryptedContent = new String(Files.readAllBytes(outputFile.toPath()));
                    String decryptedContent = xorEncryptAndDecrypt(encryptedContent, encryptionKey);
                    currentData = mapper.readValue(decryptedContent, new TypeReference<>() {});
                } catch (IOException e) {
                    LOG.warn("|{}| Could not read existing JSON: {}", title, e.getMessage());
                }
            }

            Map<String, Object> newData = new LinkedHashMap<>();
            for (int i = 0; i < keys.size(); i++) {
                newData.put(keys.get(i), values.get(i));
            }

            boolean changesDetected = false;

            switch (mode) {
                case UPDATE:
                    for (Map.Entry<String, Object> entry : newData.entrySet()) {
                        if (currentData.containsKey(entry.getKey()) && !currentData.get(entry.getKey()).equals(entry.getValue())) {
                            currentData.put(entry.getKey(), entry.getValue());
                            changesDetected = true;
                        }
                    }
                    break;
                case ADD:
                    for (Map.Entry<String, Object> entry : newData.entrySet()) {
                        if (!currentData.containsKey(entry.getKey())) {
                            currentData.put(entry.getKey(), entry.getValue());
                            changesDetected = true;
                        }
                    }
                    break;
                case ADD_AND_UPDATE:
                    for (Map.Entry<String, Object> entry : newData.entrySet()) {
                        if (!currentData.containsKey(entry.getKey()) || !currentData.get(entry.getKey()).equals(entry.getValue())) {
                            currentData.put(entry.getKey(), entry.getValue());
                            changesDetected = true;
                        }
                    }
                    break;
                case REPLACE:
                    currentData = newData;
                    changesDetected = true;
                    break;
            }

            if (!changesDetected) {
                LOG.info("|{}| No changes detected, skipping write.", title);
                return;
            }

            String jsonString = mapper.writeValueAsString(currentData);
            String encryptedJson = xorEncryptAndDecrypt(jsonString, encryptionKey);
            Files.write(outputFile.toPath(), encryptedJson.getBytes());
            LOG.info("|{}| Successfully wrote encrypted JSON file: {}", title, fullPath);
        } catch (IOException e) {
            LOG.warn("|{}| Failed to write JSON: {}", title, e.getMessage());
        }
    }

    /**
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys for the file in list. "key":"value"
     * @param values All the values of the file in list. "key":"value"
     * @param mode <pre>{@code
     * UpdateMode:
     * ADD - Adds the new keys
     * UPDATE - Updates existing keys
     * REPLACE - Replaces the whole file
     * NOTHING - Does nothing
     * }</pre>
     * @param ignoredKeys A list of keys that will be ignored
     * @param encryptionKey Special key for file low level encryption
     *
     * @throws NullPointerException
     * If any argument is {@code null}
     *
     * @exception IllegalArgumentException If lists doesn't have the same length
     */
    public synchronized static void write_encrypt(String path, String file, List<String> keys, List<Object> values, UpdateMode mode, List<String> ignoredKeys, String encryptionKey) {
        path = FileUtils.fixPath(path);
        String title = "write-encrypt";
        FileUtils.checkJSONArgsLength(keys, values, title);

        try {
            File directory = new File(path);
            if (!directory.exists() && !directory.mkdirs()) {
                LOG.info("{} Failed to create directory: {}", title, directory.getAbsolutePath());
                return;
            }

            String fullPath = path + file + ".json";
            File outputFile = new File(fullPath);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SERIALIZATION_OUTPUT_INDENT);

            if (mode == UpdateMode.NOTHING && outputFile.exists()) {
                LOG.info("{} File already exists, skipping due to NOTHING mode: {}", title, fullPath);
                return;
            }

            Map<String, Object> currentData = new LinkedHashMap<>();

            if (outputFile.exists() && mode != UpdateMode.REPLACE) {
                try {
                    String encryptedContent = new String(Files.readAllBytes(outputFile.toPath()));
                    String decryptedContent = xorEncryptAndDecrypt(encryptedContent, encryptionKey);
                    currentData = mapper.readValue(decryptedContent, new TypeReference<>() {});
                } catch (IOException e) {
                    LOG.warn("{} Could not read existing JSON: {}", title, e.getMessage());
                }
            }

            Map<String, Object> newData = new LinkedHashMap<>();
            for (int i = 0; i < keys.size(); i++) {
                if (ignoredKeys.contains(keys.get(i)) && currentData.containsKey(keys.get(i))) {
                    continue;
                }
                newData.put(keys.get(i), values.get(i));
            }

            boolean changesDetected = false;

            switch (mode) {
                case UPDATE:
                    for (Map.Entry<String, Object> entry : newData.entrySet()) {
                        if (currentData.containsKey(entry.getKey()) && !currentData.get(entry.getKey()).equals(entry.getValue())) {
                            currentData.put(entry.getKey(), entry.getValue());
                            changesDetected = true;
                        }
                    }
                    break;
                case ADD:
                    for (Map.Entry<String, Object> entry : newData.entrySet()) {
                        if (!currentData.containsKey(entry.getKey())) {
                            currentData.put(entry.getKey(), entry.getValue());
                            changesDetected = true;
                        }
                    }
                    break;
                case ADD_AND_UPDATE:
                    for (Map.Entry<String, Object> entry : newData.entrySet()) {
                        if (!currentData.containsKey(entry.getKey()) || !currentData.get(entry.getKey()).equals(entry.getValue())) {
                            currentData.put(entry.getKey(), entry.getValue());
                            changesDetected = true;
                        }
                    }
                    break;
                case REPLACE:
                    currentData = newData;
                    changesDetected = true;
                    break;
            }

            if (!changesDetected) {
                LOG.info("{} No changes detected, skipping write.", title);
                return;
            }

            String jsonString = mapper.writeValueAsString(currentData);
            String encryptedJson = xorEncryptAndDecrypt(jsonString, encryptionKey);
            Files.write(outputFile.toPath(), encryptedJson.getBytes());
            LOG.info("{} Successfully wrote encrypted JSON file: {}", title, fullPath);
        } catch (IOException e) {
            LOG.warn("{} Failed to write JSON: {}", title, e.getMessage());
        }
    }


    /**
     * Reads the content of the encrypted file
     *
     * @param path Path to the file.
     * @param file The actual file name.
     * @param key All the keys that will be read in list. "key":"value"
     * @param encryptKey The encryption key used to decrypt and re-encrypt the JSON file.
     */
    public synchronized static @Nullable Object read(String path, String file, @NotNull String key, String encryptKey) {
        path = FileUtils.fixPath(path);
        try {
            String filePath = path + file + ".json";

            String encryptedContent = Files.readString(Paths.get(filePath));

            String decryptedContent = xorEncryptAndDecrypt(encryptedContent, encryptKey);

            JSONParser parser = new JSONParser(JSONParser.MODE_PERMISSIVE);
            Object current = parser.parse(decryptedContent);

            String[] keyParts = key.split("\\.");
            for (String k : keyParts) {
                if (current instanceof JSONObject jsonObj) {
                    current = jsonObj.get(k);
                } else {
                    LOG.warn("|read-Encrypted| Key '{}' is not a valid JSON object", k);
                    return null;
                }
                if (current == null) {
                    LOG.warn("|read-Encrypted| Not found key: {}", k);
                    return null;
                }
            }
            return current;
        } catch (IOException | ParseException e) {
            LOG.warn("|read-Encrypted| Failed to read encrypted JSON file: {}", e.getMessage());
        }
        return null;
    }

    /**
     * Reads the content of the encrypted file
     *
     * @param path Path to the file.
     * @param file The actual file name.
     * @param key All the keys that will be read in list. "key":"value"
     * @param encryptKey The encryption key used to decrypt and re-encrypt the JSON file.
     */
    public synchronized static @Nullable Object readObject(String path, String file, String key,@NotNull String encryptKey) {
        path = FileUtils.fixPath(path);
        try {
            String encryptedContent = Files.readString(Paths.get(path + file + ".json"));

            String decryptedContent = xorEncryptAndDecrypt(encryptedContent, encryptKey);

            JSONParser parser = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
            JSONObject reference = (JSONObject) parser.parse(decryptedContent);

            Object value = reference.get(key);

            if (value == null) {
                LOG.info("|readObject-Encrypted| Encrypted key not found: {}", key);
                return null;
            }
            return value;

        } catch (IOException | ParseException e) {
            LOG.info("|readObject-Encrypted| Failed to read encrypted JSON file: {}", e.getMessage());
        }
        return null;
    }


    /**
     * This method returns a String, if it isn't a String, it will return "No key string found"
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys that will be read in list. "key":"value"
     * @param encryptKey The encryption key used to decrypt and re-encrypt the JSON file.
     */
    public synchronized static String readString(String path, String file, String keys,String encryptKey){
        Object result = read(path, file, keys, encryptKey);

        return switch (result) {
            case null -> "|readString-Encrypted|No key string found";

            case String s -> s;

            case JSONObject jsonObject -> jsonObject.toJSONString();
            default ->
                    String.valueOf(result);
        };
    }

    /**
     * This method returns a Byte, if it isn't a Byte, it will return 0
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys that will be read in list. "key":"value"
     * @param encryptKey The encryption key used to decrypt and re-encrypt the JSON file.
     */
    public synchronized static byte readByte(String path, String file, String keys, String encryptKey){
        Object result = read(path, file, keys, encryptKey);
        if (result instanceof Integer) {
            return ((Integer) result).byteValue();
        }else if (result instanceof Number) {
            return ((Number) result).byteValue();
        }
        return 0;
    }

    /**
     * This method returns a Short, if it isn't a Short, it will return 0
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys that will be read in list. "key":"value"
     * @param encryptKey The encryption key used to decrypt and re-encrypt the JSON file.
     */
    public synchronized static short readShort(String path, String file, String keys, String encryptKey){
        Object result = read(path, file, keys, encryptKey);
        if (result instanceof Integer) {
            return ((Integer) result).shortValue();
        }else if (result instanceof Number) {
            return ((Number) result).shortValue();
        }
        return 0;
    }

    /**
     * This method returns an Integer, if it isn't an Integer, it will return 0
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys that will be read in list. "key":"value"
     * @param encryptKey The encryption key used to decrypt and re-encrypt the JSON file.
     */
    public synchronized static int readInt(String path, String file, String keys, String encryptKey){
        Object result = read(path, file, keys, encryptKey);
        if (result instanceof Integer) {
            return (Integer) result;
        }else if (result instanceof Number) {
            return ((Number) result).intValue();
        }
        return 0;
    }

    /**
     * This method returns a Float, if it isn't a Float, it will return 0
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys that will be read in list. "key":"value"
     * @param encryptKey The encryption key used to decrypt and re-encrypt the JSON file.
     */
    public synchronized static float readFloat(String path, String file, String keys, String encryptKey){
        Object result = read(path, file, keys, encryptKey);
        if (result instanceof Integer) {
            return ((Integer) result).floatValue();
        }else if (result instanceof Number) {
            return ((Number) result).floatValue();
        }
        return 0;
    }

    /**
     * This method returns a Boolean, if it isn't a Boolean, it will return false
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys that will be read in list. "key":"value"
     */
    public synchronized static boolean readBoolean(String path, String file, String keys, String encryptKey){
        Object result = read(path, file, keys, encryptKey);
        if(result != null){
            return (boolean) result;
        } else {
            LOG.info("Boolean value from encrypted readBoolean is null!");
            return false;
        }
    }

    /**
     * Replaces values in an existing JSON file while keeping the order of keys.
     * If the file does not exist, a new one will be created.
     *
     * @param path   Path to the file.
     * @param file   The actual file name (without extension).
     * @param keys   List of keys to be replaced in the JSON file.
     * @param values List of values to be replaced in the JSON file.
     * @param encryptKey The encryption key used to decrypt and re-encrypt the JSON file.<p>
     * <p>
     * Example: <pre>{@code key : "sound.id.namespace", "sound.id.path"
     * value: "minecraft", "entity.blaze.death"}</pre>
     */
    public synchronized static void replace(String path, String file, List<String> keys, List<Object> values, String encryptKey) {
        path = FileUtils.fixPath(path);
        FileUtils.checkJSONArgsLength(keys, values, "replace");
        try {
            File directory = new File(path);
            File jsonFile = new File(directory, file + ".json");

            if (!jsonFile.exists()) {
                LOG.info("|replace| JSON file {} does not exist. No action taken.", jsonFile.getAbsolutePath());
                return;
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SERIALIZATION_OUTPUT_INDENT);

            String encryptedContent = Files.readString(jsonFile.toPath());
            String decryptedContent = xorEncryptAndDecrypt(encryptedContent, encryptKey);

            Map<String, Object> jsonMap = mapper.readValue(decryptedContent, new TypeReference<LinkedHashMap<String, Object>>() {});

            for (int i = 0; i < keys.size(); i++) {
                String[] keyPath = keys.get(i).split("\\.");
                setValue(jsonMap, keyPath, values.get(i));
            }

            String updatedJson = mapper.writeValueAsString(jsonMap);
            String encryptedJson = xorEncryptAndDecrypt(updatedJson, encryptKey);

            Files.writeString(jsonFile.toPath(), encryptedJson);

            LOG.info("|replace| Successfully updated and encrypted JSON file: {}", jsonFile.getAbsolutePath());
        } catch (IOException e) {
            LOG.info("|replace| Failed to write encrypted JSON file: {}", e.getMessage());
        }
    }

}