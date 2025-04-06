package baspig.apis.utils.files;

import baspig.apis.utils.util.BP;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static baspig.apis.utils.util.BP.LOG;

/**
 * Instantiable version of the {@link EasyJSON} class
 * 
 * @Author Baspig_
 */
@SuppressWarnings("unused")
public class EasyJSONInstance {

    private final String className;
    private final String PATH;
    private final String FILE;
    private final String XOR_EncryptKey;

    private final SerializationFeature SERIALIZATION_OUTPUT_INDENT = SerializationFeature.INDENT_OUTPUT;

    private final int JSON_SIMPLE_TYPE = JSONParser.MODE_JSON_SIMPLE;

    private String LOGGER;

    /**
     * This is an instantiated version of EasyJSON utility class.
     *
     *
     * @param path The path to the file. like: config/my_mod_folder
     * @param file The actual file to create/modify/read or delete.
     * @param loggerId The id for the log when something goes wrong.
     */
    public EasyJSONInstance(String path, String file, @Nullable String loggerId){
        this.XOR_EncryptKey = null;
        this.className = loggerId != null ? "EasyJSONInstance-" + loggerId : "EasyJSONInstance";
        this.LOGGER = loggerId;
        this.PATH = FileUtils.fixPath(path);
        this.FILE = file;
    }

    public EasyJSONInstance(String path, String file, @Nullable String loggerId, String encrypt_key){
        this.XOR_EncryptKey = encrypt_key;
        this.className = loggerId != null ? "EasyJSONInstance-" + loggerId : "EasyJSONInstance";
        this.LOGGER = loggerId;
        this.PATH = FileUtils.fixPath(path);
        this.FILE = file;
    }

    public EasyJSONInstance(Class<?> Class,String path, String file, @Nullable String loggerId){
        this.XOR_EncryptKey = null;
        this.className = loggerId != null ? Class.getName() + "-" + loggerId : "EasyJSONInstance";
        this.PATH = FileUtils.fixPath(path);
        this.FILE = file;
    }

    public EasyJSONInstance(Class<?> Class,String path, String file, @Nullable String loggerId, String encrypt_key){
        this.XOR_EncryptKey = encrypt_key;
        this.className = loggerId != null ? Class.getName() + "-" + loggerId : "EasyJSONInstance";
        this.LOGGER = loggerId;
        this.PATH = FileUtils.fixPath(path);
        this.FILE = file;
    }

    public void changeLoggerId(String loggerId){
        LOGGER = loggerId;
    }

    public String getLOGGER_ID(){
        return LOGGER;
    }

    public String getPATH(){
        return PATH;
    }

    public String getFILE(){
        return FILE;
    }

    /**
     * @param keys All the keys for the file in list. "key":"value"
     * @param values All the values of the file in list. "key":"value"
     *
     * @throws NullPointerException
     * If any argument is {@code null}
     */
    public synchronized void write(List<String> keys, List<Object> values) {
        FileUtils.checkJSONArgsLength(keys, values, LOGGER + ": write");
        try {
            File directory = new File(PATH);
            if (!directory.exists() && !directory.mkdirs() && !PATH.isEmpty()) {

                BP.fancyFileLog(className, "write", "Already created - don't worry", directory.getAbsolutePath());
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

            File outputFile = new File(PATH + FILE + ".json");

            if (outputFile.exists()) {
                BP.fancyFileLog(className, "write", "File already exists", directory.getAbsolutePath());
                return;
            }

            mapper.writeValue(outputFile, orderedMap);
            BP.fancyFileLog(className, "write", "Successfully wrote JSON file", directory.getAbsolutePath());

        } catch (IOException e) {
            BP.fancyLog(className, "write", "Successfully wrote JSON file", e.getMessage());
        }
    }

    /**
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
    public synchronized void write(List<String> keys, List<Object> values, UpdateMode mode) {
        FileUtils.checkJSONArgsLength(keys, values, LOGGER + ": write");

        try {
            File directory = new File(PATH);
            if (!directory.exists() && !directory.mkdirs() && !PATH.isEmpty()) {
                BP.fancyFileLog(className, "write", "Failed to create directory", directory.getAbsolutePath());
                return;
            }

            String fullPath = PATH + FILE + ".json";
            File outputFile = new File(fullPath);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SERIALIZATION_OUTPUT_INDENT);

            if (mode == UpdateMode.NOTHING && outputFile.exists()) {
                BP.fancyFileLog(className, "write", "File already exists, skipping due to NOTHING mode", directory.getAbsolutePath());
                return;
            }

            Map<String, Object> currentData = new LinkedHashMap<>();

            if (outputFile.exists() && mode != UpdateMode.REPLACE) {
                try {
                    String content = new String(Files.readAllBytes(outputFile.toPath()));
                    currentData = mapper.readValue(content, new TypeReference<>() {});
                } catch (IOException e) {
                    BP.fancyFileLog(className, "write", "Could not read existing JSON", directory.getAbsolutePath());
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
                BP.fancyFileLog(className, "write", "No changes detected, skipping write.", directory.getAbsolutePath());
                return;
            }

            mapper.writeValue(outputFile, currentData);
            BP.fancyFileLog(className, "write", "Successfully wrote JSON file", directory.getAbsolutePath());
        } catch (IOException e) {
            BP.fancyLog(className, "write", "Failed to write JSON", e.getMessage());
        }
    }

    /**
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
    public synchronized void write(List<String> keys, List<Object> values, UpdateMode mode, List<String> ignoredKeys) {
        String title = LOGGER + ": write-advanced";
        FileUtils.checkJSONArgsLength(keys, values, title);

        try {
            File directory = new File(PATH);
            if (!directory.exists() && !directory.mkdirs() && !PATH.isEmpty()) {
                BP.fancyFileLog(className, "write", "Failed to create directory", directory.getAbsolutePath());
                return;
            }

            String fullPath = PATH + FILE + ".json";
            File outputFile = new File(fullPath);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SERIALIZATION_OUTPUT_INDENT);

            if (mode == UpdateMode.NOTHING && outputFile.exists()) {
                BP.fancyFileLog(className, "write", "File already exists, skipping due to NOTHING mode", directory.getAbsolutePath());
                return;
            }

            Map<String, Object> currentData = new LinkedHashMap<>();

            if (outputFile.exists() && mode != UpdateMode.REPLACE) {
                try {
                    String content = new String(Files.readAllBytes(outputFile.toPath()));
                    currentData = mapper.readValue(content, new TypeReference<>() {});
                } catch (IOException e) {
                    BP.fancyFileLog(className, "write", "Could not read existing JSON", directory.getAbsolutePath());
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
                BP.fancyLog(className, "write", "No changes detected, skipping write.", directory.getAbsolutePath());
                return;
            }

            mapper.writeValue(outputFile, currentData);
            BP.fancyFileLog(className, "write", "Successfully wrote JSON file.", directory.getAbsolutePath());
        } catch (IOException e) {
            BP.fancyLog(className, "write", "Failed to write JSON", e.getMessage());
        }
    }

    /**
     * Replaces values in an existing JSON file while keeping the order of keys.
     * If the file does not exist, a new one will be created.
     *
     * @param keys   List of keys to be replaced in the JSON file.
     * @param values List of values to be replaced in the JSON file. <p>
     * <p>
     * Example: <pre>{@code key : "sound.id.namespace", "sound.id.path"
     * value: "minecraft", "entity.blaze.death"}</pre>
     */
    public synchronized void replace(List<String> keys,List<Object> values) {
        FileUtils.checkJSONArgsLength(keys, values, "replace-multiple");

        try {
            File directory = new File(PATH);
            File jsonFile = new File(directory, FILE + ".json");

            if (!jsonFile.exists()) {
                BP.fancyFileLog(className, "replace", "JSON file does not exist. No action taken", directory.getAbsolutePath());
                return;
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SERIALIZATION_OUTPUT_INDENT);

            Map<String, Object> jsonMap = mapper.readValue(jsonFile, new TypeReference<LinkedHashMap<String, Object>>() {});

            for (int i = 0; i < keys.size(); i++) {
                String[] keyPath = keys.get(i).split("\\.");
                setValue(jsonMap, keyPath, values.get(i));
            }

            File outputFile = new File(PATH + FILE + ".json");
            mapper.writeValue(outputFile, jsonMap);

        } catch (IOException e) {
            BP.fancyLog(className, "replace", "Failed to write JSON file", e.getMessage());
        }
    }

    /**
     * Replaces a value in an existing JSON file while keeping the order of keys.
     * If the file does not exist, a new one will be created.
     *.
     * @param key    The key to be replaced in the JSON file.
     * @param value  The value to replace the existing value for the key.
     */
    public synchronized void replace(String key, Object value) {

        try {
            File directory = new File(PATH);
            File jsonFile = new File(directory, FILE + ".json");

            if (!jsonFile.exists()) {
                BP.fancyFileLog(className, "replace", "JSON file does not exist. No action taken", directory.getAbsolutePath());
                return;
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SERIALIZATION_OUTPUT_INDENT);

            Map<String, Object> jsonMap = mapper.readValue(jsonFile, new TypeReference<LinkedHashMap<String, Object>>() {});

            String[] keyPath = key.split("\\.");
            setValue(jsonMap, keyPath, value);

            File outputFile = new File(PATH + FILE + ".json");
            mapper.writeValue(outputFile, jsonMap);

        } catch (IOException e) {
            BP.fancyLog(className, "replace", "Failed to write JSON file", e.getMessage());
        }
    }

    /**
     * Replaces a value in an existing JSON file while keeping the order of keys.
     * If the file does not exist, a new one will be created.
     *
     * @param key    The key to be replaced in the JSON file.
     * @param value  The value to replace the existing value for the key.
     * @param encryptKey The encryption key used to decrypt and re-encrypt the JSON file.
     */
    public synchronized void replace(String key, Object value, String encryptKey) {
        try {
            File directory = new File(PATH);
            File jsonFile = new File(directory, FILE + ".json");

            if (!jsonFile.exists()) {
                BP.fancyFileLog(className, "replace-encrypt", "JSON file does not exist. No action taken", directory.getAbsolutePath());
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
            BP.fancyLog(className, "replace-encrypt", "Failed to write JSON file", e.getMessage());
        }
    }


    @SuppressWarnings("unchecked")
    private synchronized void setValue(Map<String, Object> jsonMap, String @NotNull [] keys, Object value) {
        Map<String, Object> currentMap = jsonMap;
        for (int i = 0; i < keys.length - 1; i++) {
            currentMap = (Map<String, Object>) currentMap.get(keys[i]);
        }
        currentMap.put(keys[keys.length - 1], value);
    }


    /**
     * @param key The key whose value needs to be read. "key":"value"
     *
     * @return The value associated with the key, or null if not found.
     */
    public synchronized @Nullable Object read( @NotNull String key) {
        try {
            JSONParser parser = new JSONParser(JSON_SIMPLE_TYPE);
            Object current = parser.parse(new FileReader(PATH + FILE + ".json"));

            String[] keyParts = key.split("\\.");
            for (String k : keyParts) {
                if (current instanceof JSONObject jsonObj) {
                    current = jsonObj.get(k);
                } else {
                    BP.fancyLog(className, "read", "Key '" + k + "' is not a valid JSON object");
                    return null;
                }
                if (current == null) {
                    BP.fancyLog(className, "read", "No key found: " + k);
                    return null;
                }
            }
            return current;
        } catch (IOException | ParseException e) {
            BP.fancyLog(className, "read", "Failed to read JSON file");
        }
        return null;
    }

    /**

     * @param key The key whose value needs to be read. "key":"value"
     *
     * @return The value associated with the key, or null if not found.
     */
    private synchronized @Nullable Object readObject(String key) {
        try {
            JSONParser parser = new JSONParser(JSON_SIMPLE_TYPE);
            Object current = parser.parse(new FileReader(PATH + FILE + ".json"));

            if (current instanceof JSONObject jsonNested) {
                current = jsonNested.get(key);
            } else {
                BP.fancyFileLog(className, "readObject", "is not a valid JSON object: " + key);
                return null;
            }

            if (current == null) {
                BP.fancyFileLog(className, "readObject", "is not a valid JSON object: " + key);
                return null;
            }
            return current;

        } catch (IOException | ParseException e) {
            BP.fancyLog(className, "readObject", "Failed to read JSON file: ", e.getMessage());
        }
        return null;
    }

    /**
     * This method returns a String, if it isn't a String, it will return "No key string found"
     * @param keys All the keys that will be read in list. "key":"value"
     */
    public synchronized String readString(String keys){
        Object result = read(keys);
        if (result == null) {
            return BP.Return.fancyLog(className, "readString", "No key string found");
        }
        return switch (result) {
            case String s -> s;
            case JSONObject jsonObject -> jsonObject.toJSONString();
            default -> String.valueOf(result);
        };
    }

    /**
     * This method returns a Byte, if it isn't a Byte, it will return 0

     * @param keys All the keys that will be read in list. "key":"value"
     */
    public synchronized byte readByte(String keys){
        Object result = read(keys);
        if (result instanceof Integer) {
            return ((Integer) result).byteValue();
        }else if (result instanceof Number) {
            return ((Number) result).byteValue();
        }else {
            BP.fancyLog(className, "readByte", "something went wrong");
            return 0;
        }
    }

    /**
     * This method returns a Short, if it isn't a Short, it will return 0
     * @param keys All the keys that will be read in list. "key":"value"
     */
    public synchronized short readShort(String keys){
        Object result = read(keys);
        if (result instanceof Integer) {
            return ((Integer) result).shortValue();
        }else if (result instanceof Number) {
            return ((Number) result).shortValue();
        }else {
            BP.fancyLog(className, "readShort", "something went wrong");
            return 0;
        }
    }

    /**
     * This method returns an Integer, if it isn't an Integer, it will return 0
     * @param keys All the keys that will be read in list. "key":"value"
     */
    public synchronized int readInt(String keys){
        Object result = read(keys);
        if (result instanceof Integer) {
            return (Integer) result;
        }else if (result instanceof Number) {
            return ((Number) result).intValue();
        }else {
            BP.fancyLog(className, "readInt", "something went wrong");
            return 0;
        }
    }

    /**
     * This method returns a Float, if it isn't a Float, it will return 0
     * @param keys All the keys that will be read in list. "key":"value"
     */
    public float readFloat(String keys){
        Object result = read(keys);
        if (result instanceof Integer) {
            return ((Integer) result).floatValue();
        }else if (result instanceof Number) {
            return ((Number) result).floatValue();
        }else {
            BP.fancyLog(className, "readFloat", "something went wrong");
            return 0;
        }
    }

    /**
     * This method returns a Boolean, if it isn't a Boolean, it will return false
     * @param keys All the keys that will be read in list. "key":"value"
     */
    public boolean readBoolean(String keys){
        Object result = read(keys);
        if(result != null){
            return (boolean) result;
        } else {
            BP.fancyLog(className, "readBoolean", "Boolean value from readBoolean is null...\n switched to false!");
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
    public synchronized @NotNull Object nestObject(List<String> keys, List<Object> values) {
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
    public synchronized @NotNull JSONArray writeList(List<Object> values) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(values);
        return jsonArray;
    }

    /**
     * Replaces a value inside a JSON object within a list in a JSON file.
     *
     * @param listKeys  Path of keys of the list that contains the objects.
     * @param searchKey The key to search for inside the objects.
     * @param newValue The new value to set.
     */
    @SuppressWarnings("unchecked")
    private synchronized void replaceListValue(String searchKey, Object newValue, String... listKeys) {
        try {
            File jsonFile = new File(PATH + FILE + ".json");

            if (!jsonFile.exists()) {
                BP.fancyFileLog(className, "replaceListValue", "JSON file does not exist. No action taken", jsonFile.getAbsolutePath());
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
                    BP.fancyFileLog(className, "replaceListValue", "Key is "+ key +" not a valid JSON object", jsonFile.getAbsolutePath());
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
                    BP.fancyFileLog(className, "replaceListValue", "Key "+ searchKey +" not found in list " + listKeys[listKeys.length - 1], jsonFile.getAbsolutePath());
                    return;
                }
            } else {
                BP.fancyFileLog(className, "replaceListValue", "The key " + listKeys[listKeys.length - 1] + " does not point to a list", jsonFile.getAbsolutePath());
                return;
            }

            mapper.writeValue(jsonFile, jsonMap);
            BP.fancyFileLog(className, "replaceListValue", "The key " + listKeys[listKeys.length - 1] + " does not point to a list", jsonFile.getAbsolutePath());

            LOG.info("Successfully updated the JSON file.");

        } catch (IOException e) {
            BP.fancyLog(className, "replaceListValue", "Failed to modify JSON file", e.getMessage());
        }
    }

    /**
     * Reads and returns the entire list from a JSON object inside a list within a JSON file.
     *
     * @param keys  The key of the list that contains the objects.
     * @return The list found, null if not found.
     */
    public synchronized JSONArray readList(String... keys) {
        try {
            JSONParser parser = new JSONParser(JSON_SIMPLE_TYPE);
            Object listObject = parser.parse(new FileReader(PATH + FILE + ".json"));

            for (String key : keys) {
                if (listObject instanceof JSONObject jsonNested) {
                    listObject = jsonNested.get(key);
                } else {
                    BP.fancyFileLog(className, "readList", "Key is "+ key +" not a valid JSON object");
                    return null;
                }

                if (listObject == null) {
                    BP.fancyFileLog(className, "readList", "No key found: " + key);
                    return null;
                }
            }

            if (listObject instanceof JSONArray jsonArray) {
                return jsonArray;
            } else {
                BP.fancyFileLog(className, "readList", "The final object is not a JSONArray");
                return null;
            }
        } catch (IOException | ParseException e) {
            BP.fancyLog(className, "readList", "Failed to read JSON file", e.getMessage());
        }
        return null;
    }


    /**
     * Deletes the JSON file if it exists.
     */
    public synchronized void delete() {
        Path jsonFile = Paths.get(PATH, FILE + ".json");
        try {
            Files.delete(jsonFile);
            BP.fancyFileLog(className, "delete", "Successfully deleted JSON file", String.valueOf(jsonFile.toAbsolutePath()));
        } catch (IOException e) {
            BP.fancyLog(className, "delete", "Failed to delete JSON file", String.valueOf(jsonFile.toAbsolutePath()), e.getMessage());
        }
    }

    private synchronized @NotNull String xorEncryptAndDecrypt(@NotNull String input, String key) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            output.append((char)(input.charAt(i) ^ key.charAt(i % key.length())));
        }
        return output.toString();
    }

    /**
     * It just ensures your objects can't be accessed by the user
     *
     * @param keys All the keys for the file in list. "key":"value"
     * @param values All the values of the file in list. "key":"value"
     * @param encryptKey Special key for file low security encryption
     */
    public synchronized void write_encrypt(List<String> keys, List<Object> values, String encryptKey) {
        FileUtils.checkJSONArgsLength(keys, values, "writeAndEncrypt");
        try {
            File directory = new File(PATH);
            if (!directory.exists() && !directory.mkdirs()) {
                BP.fancyFileLog(className, "write_encrypt", "Failed to create directory", directory.getAbsolutePath());
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

            String fullPath = PATH + FILE + ".json";
            File outputFile = new File(fullPath);

            LOG.info(orderedMap.toString());

            File outFile = new File(fullPath);

            Files.writeString(outputFile.toPath(), encryptedJson);

            BP.fancyFileLog(className, "write_encrypt", "Successfully wrote JSON encrypted file", directory.getAbsolutePath());

        } catch (IOException e) {
            BP.fancyLog(className, "write_encrypt", "Failed to write encrypted JSON", e.getMessage());
        }
    }

    /**
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
    public synchronized void write_encrypt(List<String> keys, List<Object> values, UpdateMode mode, String encryptionKey) {

        String title = "writeAndEncrypt";
        FileUtils.checkJSONArgsLength(keys, values, title);

        try {
            File directory = new File(PATH);
            if (!directory.exists() && !directory.mkdirs()) {
                BP.fancyFileLog(className, "write_encrypt", "Failed to create directory", directory.getAbsolutePath());
                return;
            }

            String fullPath = PATH + FILE + ".json";
            File outputFile = new File(fullPath);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SERIALIZATION_OUTPUT_INDENT);

            if (mode == UpdateMode.NOTHING && outputFile.exists()) {

                BP.fancyFileLog(className, "write_encrypt", "File already exists, skipping due to NOTHING mode", fullPath);
                return;
            }

            Map<String, Object> currentData = new LinkedHashMap<>();

            if (outputFile.exists() && mode != UpdateMode.REPLACE) {
                try {
                    String encryptedContent = new String(Files.readAllBytes(outputFile.toPath()));
                    String decryptedContent = xorEncryptAndDecrypt(encryptedContent, encryptionKey);
                    currentData = mapper.readValue(decryptedContent, new TypeReference<>() {});
                } catch (IOException e) {
                    BP.fancyLog(className, "write_encrypt", "Could not read existing JSON", e.getMessage());
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
                BP.fancyFileLog(className, "write_encrypt", "No changes detected, skipping write", directory.getAbsolutePath());
                return;
            }

            String jsonString = mapper.writeValueAsString(currentData);
            String encryptedJson = xorEncryptAndDecrypt(jsonString, encryptionKey);
            Files.write(outputFile.toPath(), encryptedJson.getBytes());
            BP.fancyFileLog(className, "write_encrypt", "Successfully wrote JSON encrypted file", directory.getAbsolutePath());
        } catch (IOException e) {
            BP.fancyLog(className, "write_encrypt", "Failed to write encrypted JSON", e.getMessage());
        }
    }

    /**
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
    public synchronized void write_encrypt(List<String> keys, List<Object> values, UpdateMode mode, List<String> ignoredKeys, String encryptionKey) {
        String title = "write-encrypt";
        FileUtils.checkJSONArgsLength(keys, values, title);

        try {
            File directory = new File(PATH);
            if (!directory.exists() && !directory.mkdirs()) {
                BP.fancyFileLog(className, "write_encrypt", "Failed to create directory", directory.getAbsolutePath());
                return;
            }

            String fullPath = PATH + FILE + ".json";
            File outputFile = new File(fullPath);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SERIALIZATION_OUTPUT_INDENT);

            if (mode == UpdateMode.NOTHING && outputFile.exists()) {
                BP.fancyFileLog(className, "write_encrypt", "File already exists, skipping due to NOTHING mode", fullPath);
                return;
            }

            Map<String, Object> currentData = new LinkedHashMap<>();

            if (outputFile.exists() && mode != UpdateMode.REPLACE) {
                try {
                    String encryptedContent = new String(Files.readAllBytes(outputFile.toPath()));
                    String decryptedContent = xorEncryptAndDecrypt(encryptedContent, encryptionKey);
                    currentData = mapper.readValue(decryptedContent, new TypeReference<>() {});
                } catch (IOException e) {
                    BP.fancyLog(className, "write_encrypt", "Could not read existing JSON", e.getMessage());
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
                BP.fancyFileLog(className, "write_encrypt", "No changes detected, skipping write", fullPath);
                return;
            }

            String jsonString = mapper.writeValueAsString(currentData);
            String encryptedJson = xorEncryptAndDecrypt(jsonString, encryptionKey);
            Files.write(outputFile.toPath(), encryptedJson.getBytes());
            BP.fancyFileLog(className, "write_encrypt", "Successfully wrote JSON encrypted file", directory.getAbsolutePath());
        } catch (IOException e) {
            BP.fancyLog(className, "write_encrypt", "Failed to write encrypted JSON", e.getMessage());
        }
    }


    /**
     * Reads the content of the encrypted file
     *
     * @param key All the keys that will be read in list. "key":"value"
     * @param encryptKey The encryption key used to decrypt and re-encrypt the JSON file.
     */
    public synchronized @Nullable Object read(@NotNull String key, String encryptKey) {
        try {
            String filePath = PATH + FILE + ".json";

            String encryptedContent = Files.readString(Paths.get(filePath));

            String decryptedContent = xorEncryptAndDecrypt(encryptedContent, encryptKey);

            JSONParser parser = new JSONParser(JSONParser.MODE_PERMISSIVE);
            Object current = parser.parse(decryptedContent);

            String[] keyParts = key.split("\\.");
            for (String k : keyParts) {
                if (current instanceof JSONObject jsonObj) {
                    current = jsonObj.get(k);
                } else {
                    BP.fancyFileLog(className, "read", "Key " + k + " is not a valid JSON object");
                    return null;
                }
                if (current == null) {
                    BP.fancyFileLog(className, "read", "Not found key " + k);
                    return null;
                }
            }
            return current;
        } catch (IOException | ParseException e) {
            BP.fancyLog(className, "read", "Failed to read encrypted JSON file", e.getMessage());
        }
        return null;
    }

    /**
     * Reads the content of the encrypted file
     * @param key All the keys that will be read in list. "key":"value"
     * @param encryptKey The encryption key used to decrypt and re-encrypt the JSON file.
     */
    public synchronized @Nullable Object readObject(String key,@NotNull String encryptKey) {
        try {
            String encryptedContent = Files.readString(Paths.get(PATH + FILE + ".json"));

            String decryptedContent = xorEncryptAndDecrypt(encryptedContent, encryptKey);

            JSONParser parser = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
            JSONObject reference = (JSONObject) parser.parse(decryptedContent);

            Object value = reference.get(key);

            if (value == null) {
                BP.fancyFileLog(className, "readObject", "Encrypted key not found: " + key);
                return null;
            }
            return value;

        } catch (IOException | ParseException e) {
            BP.fancyFileLog(className, "readObject", "Failed to read encrypted JSON file: " + e.getMessage());
        }
        return null;
    }


    /**
     * This method returns a String, if it isn't a String, it will return "No key string found"
     * @param keys All the keys that will be read in list. "key":"value"
     * @param encryptKey The encryption key used to decrypt and re-encrypt the JSON file.
     */
    public synchronized String readString(String keys,String encryptKey){
        Object result = read(keys, encryptKey);

        return switch (result) {
            case null -> "|readString-Encrypted|";

            case String s -> s;

            case JSONObject jsonObject -> jsonObject.toJSONString();
            default ->
                    String.valueOf(result);
        };
    }

    /**
     * This method returns a Byte, if it isn't a Byte, it will return 0
     * @param keys All the keys that will be read in list. "key":"value"
     * @param encryptKey The encryption key used to decrypt and re-encrypt the JSON file.
     */
    public synchronized byte readByte(String keys, String encryptKey){
        Object result = read(keys, encryptKey);
        if (result instanceof Integer) {
            return ((Integer) result).byteValue();
        }else if (result instanceof Number) {
            return ((Number) result).byteValue();
        }
        return 0;
    }

    /**
     * This method returns a Short, if it isn't a Short, it will return 0
     * @param keys All the keys that will be read in list. "key":"value"
     * @param encryptKey The encryption key used to decrypt and re-encrypt the JSON file.
     */
    public synchronized short readShort(String keys, String encryptKey){
        Object result = read(keys, encryptKey);
        if (result instanceof Integer) {
            return ((Integer) result).shortValue();
        }else if (result instanceof Number) {
            return ((Number) result).shortValue();
        }
        return 0;
    }

    /**
     * This method returns an Integer, if it isn't an Integer, it will return 0
     * @param keys All the keys that will be read in list. "key":"value"
     * @param encryptKey The encryption key used to decrypt and re-encrypt the JSON file.
     */
    public synchronized int readInt(String keys, String encryptKey){
        Object result = read(keys, encryptKey);
        if (result instanceof Integer) {
            return (Integer) result;
        }else if (result instanceof Number) {
            return ((Number) result).intValue();
        }
        return 0;
    }

    /**
     * This method returns a Float, if it isn't a Float, it will return 0
     * @param keys All the keys that will be read in list. "key":"value"
     * @param encryptKey The encryption key used to decrypt and re-encrypt the JSON file.
     */
    public synchronized float readFloat(String keys, String encryptKey){
        Object result = read(keys, encryptKey);
        if (result instanceof Integer) {
            return ((Integer) result).floatValue();
        }else if (result instanceof Number) {
            return ((Number) result).floatValue();
        }
        return 0;
    }

    /**
     * This method returns a Boolean, if it isn't a Boolean, it will return false
     * @param keys All the keys that will be read in list. "key":"value"
     */
    public synchronized boolean readBoolean(String keys, String encryptKey){
        Object result = read(keys, encryptKey);
        if(result != null){
            return (boolean) result;
        } else {
            BP.fancyLog(className, "readBoolean", "Boolean value from encrypted readBoolean is null!");
            return false;
        }
    }

    /**
     * Replaces values in an existing JSON file while keeping the order of keys.
     * If the file does not exist, a new one will be created.
     *
     * @param keys   List of keys to be replaced in the JSON file.
     * @param values List of values to be replaced in the JSON file.
     * @param encryptKey The encryption key used to decrypt and re-encrypt the JSON file.<p>
     * <p>
     * Example: <pre>{@code key : "sound.id.namespace", "sound.id.path"
     * value: "minecraft", "entity.blaze.death"}</pre>
     */
    public synchronized void replace(List<String> keys, List<Object> values, String encryptKey) {
        FileUtils.checkJSONArgsLength(keys, values, "replace");
        try {
            File directory = new File(PATH);
            File jsonFile = new File(directory, FILE + ".json");

            if (!jsonFile.exists()) {
                BP.fancyLog(className, "replace", "JSON file does not exist. No action taken.", jsonFile.getAbsolutePath());
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

            BP.fancyLog(className, "replace", "Successfully updated and encrypted JSON file", jsonFile.getAbsolutePath());

        } catch (IOException e) {
            BP.fancyLog(className, "replace", "Failed to write encrypted JSON file", e.getMessage());
        }
    }
}
