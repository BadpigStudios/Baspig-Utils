package baspig.apis.utils.files;

import baspig.apis.utils.util.BP;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is made for quick and fast json file management
 *
 * @author Baspig_
 */
@SuppressWarnings("unused")
public class FastJSON {

    private static final int JSON_SIMPLE_TYPE = JSONParser.MODE_JSON_SIMPLE;
    private static final JSONStyle spaced = JSONStyle.NO_COMPRESS;

    /**
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys for the file in list. "key":"value"
     * @param values All the values of the file in list. "key":"value"
     */
    public static void write(String path, String file, List<String> keys, List<Object> values) {
        if (keys.size() != values.size()) {
            throw new IllegalArgumentException("Lists must have the same length");
        }

        try {
            // Ensure the directory exists
            File directory = new File(path);
            if (!directory.exists() && !directory.mkdirs()) {
                BP.LOG.info("Failed to create directory: {}", directory.getAbsolutePath());
                return;
            }

            // Maintain the order of JSON elements
            Map<String, Object> orderedMap = new LinkedHashMap<>();
            for (int i = 0; i < keys.size(); i++) {
                Object value = values.get(i);

                // Convert JSONObject to a compatible format (Map) before storing
                if (value instanceof JSONObject) {
                    value = new ObjectMapper().readValue(value.toString(), Map.class);
                }

                orderedMap.put(keys.get(i), value);
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            // Determine the correct file path
            String fullPath = path.isEmpty() ? file + ".json" : path + "/" + file + ".json";
            File outputFile = new File(fullPath);

            // Write to JSON file
            mapper.writeValue(outputFile, orderedMap);

            BP.LOG.info("Successfully wrote JSON file: {}", outputFile.getAbsolutePath());

        } catch (IOException e) {
            BP.LOG.info("Failed to write JSON: {}", e.getMessage());
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
     */
    public static void replace(String path, String file, List<String> keys, List<Object> values) {
        if (!path.isEmpty()) {
            path = path + "/";
        }
        if (keys.size() != values.size()) {
            throw new IllegalArgumentException("Keys and values lists must have the same length");
        }
        try {
            // Ensure the folder exists (if not, create it)
            File directory = new File(path);
            File jsonFile = new File(directory, file + ".json");

            // If the file doesn't exist, do nothing
            if (!jsonFile.exists()) {
                BP.LOG.info("JSON file {} does not exist. No action taken.", jsonFile.getAbsolutePath());
                return;
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT); // For pretty printing

            // Read existing JSON into a LinkedHashMap to preserve order
            Map<String, Object> jsonMap = mapper.readValue(jsonFile, new TypeReference<LinkedHashMap<String, Object>>() {});

            // Replace values for the provided keys
            for (int i = 0; i < keys.size(); i++) {
                jsonMap.put(keys.get(i), values.get(i));
            }

            // Write the updated JSON back to the file

            File outputFile = new File(path + file + ".json");
            mapper.writeValue(outputFile, jsonMap);


            // Re-read and log updated content for verification
            Map<String, Object> updatedMap = mapper.readValue(jsonFile, new TypeReference<LinkedHashMap<String, Object>>() {});

        } catch (IOException e) {
            BP.LOG.info("Failed to write JSON file: {}", e.getMessage());
        }
    }

    /**
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys that will be replaced in list. "key":"value"
     * @param values All the values that will be replaced in list. "key":"value"
     */
    public static void replaceString(String path, String file, List<String> keys, List<String> values) {
        if (keys.size() != values.size()) {
            throw new IllegalArgumentException("Keys and values lists must have the same length");
        }
        try {
            JSONParser parser = new JSONParser(JSON_SIMPLE_TYPE);
            JSONObject jsonObject;

            try (FileReader reader = new FileReader(path + file + ".json")) {
                jsonObject = (JSONObject) parser.parse(reader);
            } catch (IOException | ParseException e) {
                jsonObject = new JSONObject();
            }

            for (int i = 0; i < keys.size(); i++) {
                jsonObject.put(keys.get(i), values.get(i));
            }

            try (FileWriter writer = new FileWriter(path + file + ".json", false)) {
                writer.write(jsonObject.toJSONString(spaced));
            }

        } catch (IOException e) {
            BP.LOG.info("Failed to write String into JSON file: {}", e.getMessage());
        }
    }

    /**
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys that will be replaced in list. "key":"value"
     * @param values All the values that will be replaced in list. "key":"value"
     */
    public static void replaceInt(String path, String file, List<String> keys, List<Integer> values) {
        if (keys.size() != values.size()) {
            throw new IllegalArgumentException("Keys and values lists must have the same length");
        }

        try {
            JSONParser parser = new JSONParser(JSON_SIMPLE_TYPE);
            JSONObject jsonObject;

            try (FileReader reader = new FileReader(path + file + ".json")) {
                jsonObject = (JSONObject) parser.parse(reader);
            } catch (IOException | ParseException e) {
                jsonObject = new JSONObject();
            }

            for (int i = 0; i < keys.size(); i++) {
                jsonObject.put(keys.get(i), values.get(i));
            }

            try (FileWriter writer = new FileWriter(path + file + ".json", false)) {
                writer.write(jsonObject.toJSONString(spaced));
            }

        } catch (IOException e) {
            BP.LOG.info("Failed to write Integer into JSON file: {}", e.getMessage());
        }
    }

    /**
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys that will be replaced in list. "key":"value"
     * @param values All the values that will be replaced in list. "key":"value"
     */
    public static void replaceShort(String path, String file, List<String> keys, List<Short> values) {
        if (keys.size() != values.size()) {
            throw new IllegalArgumentException("Keys and values lists must have the same length");
        }

        try {
            JSONParser parser = new JSONParser(JSON_SIMPLE_TYPE);
            JSONObject jsonObject;

            try (FileReader reader = new FileReader(path + file + ".json")) {
                jsonObject = (JSONObject) parser.parse(reader);
            } catch (IOException | ParseException e) {
                jsonObject = new JSONObject();
            }

            for (int i = 0; i < keys.size(); i++) {
                jsonObject.put(keys.get(i), values.get(i));
            }

            try (FileWriter writer = new FileWriter(path + file + ".json", false)) {
                writer.write(jsonObject.toJSONString(spaced));
            }
        } catch (IOException e) {
            BP.LOG.info("Failed to write Short into JSON file: {}", e.getMessage());
        }
    }

    /**
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys that will be replaced in list. "key":"value"
     * @param values All the values that will be replaced in list. "key":"value"
     */
    public static void replaceByte(String path, String file, List<String> keys, List<Byte> values) {
        if (keys.size() != values.size()) {
            throw new IllegalArgumentException("Keys and values lists must have the same length");
        }

        try {
            JSONParser parser = new JSONParser(JSON_SIMPLE_TYPE);
            JSONObject jsonObject;

            try (FileReader reader = new FileReader(path + file + ".json")) {
                jsonObject = (JSONObject) parser.parse(reader);
            } catch (IOException | ParseException e) {
                jsonObject = new JSONObject();
            }

            for (int i = 0; i < keys.size(); i++) {
                jsonObject.put(keys.get(i), values.get(i));
            }

            try (FileWriter writer = new FileWriter(path + file + ".json", false)) {
                writer.write(jsonObject.toJSONString(spaced));
            }
        } catch (IOException e) {
            BP.LOG.info("Failed to write Byte into JSON file: {}", e.getMessage());
        }
    }

    /**
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys that will be replaced in list. "key":"value"
     * @param values All the values that will be replaced in list. "key":"value"
     */
    public static void replaceFloat(String path, String file, List<String> keys, List<Float> values) {
        if (keys.size() != values.size()) {
            throw new IllegalArgumentException("Keys and values lists must have the same length");
        }

        try {
            JSONParser parser = new JSONParser(JSON_SIMPLE_TYPE);
            JSONObject jsonObject;

            try (FileReader reader = new FileReader(path + file + ".json")) {
                jsonObject = (JSONObject) parser.parse(reader);
            } catch (IOException | ParseException e) {
                jsonObject = new JSONObject();
            }

            for (int i = 0; i < keys.size(); i++) {
                jsonObject.put(keys.get(i), values.get(i));
            }

            try (FileWriter writer = new FileWriter(path + file + ".json", false)) {
                writer.write(jsonObject.toJSONString(spaced));
            }
        } catch (IOException e) {
            BP.LOG.info("Failed to write Float into JSON file: {}", e.getMessage());
        }
    }

    /**
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys that will be replaced in list. "key":"value"
     * @param values All the values that will be replaced in list. "key":"value"
     */
    public static void replaceDouble(String path, String file, List<String> keys, List<Double> values) {
        if (keys.size() != values.size()) {
            throw new IllegalArgumentException("Keys and values lists must have the same length");
        }

        try {
            JSONParser parser = new JSONParser(JSON_SIMPLE_TYPE);
            JSONObject jsonObject;

            try (FileReader reader = new FileReader(path + file + ".json")) {
                jsonObject = (JSONObject) parser.parse(reader);
            } catch (IOException | ParseException e) {
                jsonObject = new JSONObject();
            }

            for (int i = 0; i < keys.size(); i++) {
                jsonObject.put(keys.get(i), values.get(i));
            }

            try (FileWriter writer = new FileWriter(path + file + ".json", false)) {
                writer.write(jsonObject.toJSONString(spaced));
            }
        } catch (IOException e) {
            BP.LOG.info("Failed to write Double into JSON file: {}", e.getMessage());
        }
    }

    /**
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys that will be replaced in list. "key":"value"
     * @param values All the values that will be replaced in list. "key":"value"
     */
    public static void replaceJSONObject(String path, String file, List<String> keys, List<JSONObject> values) {
        if (keys.size() != values.size()) {
            throw new IllegalArgumentException("Keys and values lists must have the same length");
        }

        try {
            JSONParser parser = new JSONParser(JSON_SIMPLE_TYPE);
            JSONObject jsonObject;

            try (FileReader reader = new FileReader(path + file + ".json")) {
                jsonObject = (JSONObject) parser.parse(reader);
            } catch (IOException | ParseException e) {
                jsonObject = new JSONObject();
            }

            for (int i = 0; i < keys.size(); i++) {
                jsonObject.put(keys.get(i), values.get(i));
            }

            try (FileWriter writer = new FileWriter(path + file + ".json", false)) {
                writer.write(jsonObject.toJSONString(spaced));
            }
        } catch (IOException e) {
            BP.LOG.info("Failed to write JSONObject into JSON file: {}", e.getMessage());
        }
    }

    /**
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys that will be replaced in list. "key":"value"
     * @param values All the values that will be replaced in list. "key":"value"
     */
    private static void replaceJSONArray(String path, String file, List<String> keys, List<JSONArray> values) {
        if (keys.size() != values.size()) {
            throw new IllegalArgumentException("Keys and values lists must have the same length");
        }

        try {
            JSONParser parser = new JSONParser(JSON_SIMPLE_TYPE);
            JSONObject jsonObject;

            try (FileReader reader = new FileReader(path + file + ".json")) {
                jsonObject = (JSONObject) parser.parse(reader);
            } catch (IOException | ParseException e) {
                jsonObject = new JSONObject();
            }

            for (int i = 0; i < keys.size(); i++) {
                jsonObject.put(keys.get(i), values.get(i));
            }

            try (FileWriter writer = new FileWriter(path + file + ".json", false)) {
                writer.write(jsonObject.toJSONString(spaced));
            }
        } catch (IOException e) {
            BP.LOG.info("Failed to write JSONArray into JSON file: {}", e.getMessage());
        }
    }

    /**
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys that will be read in list. "key":"value"
     */
    public static Object readObject(String path, String file, String keys) {
        if (!path.isEmpty()) {
            path = path + "/";
        }
        try {
            JSONParser parser = new JSONParser(JSON_SIMPLE_TYPE);
            JSONObject reference = (JSONObject) parser.parse(
                    new FileReader(path + file +".json")
            );

            Object value = reference.get(keys);

            if (value == null) {
                BP.LOG.info("Key not found: {}", keys);
                return null;
            }

            return switch (value) {
                case String s -> s;
                case Number number -> number;
                case Boolean b -> b;
                case JSONObject jsonObject -> jsonObject;
                case JSONArray jsonArray -> jsonArray;

                default -> value;
            };

        } catch (IOException | ParseException e) {
            BP.LOG.info("Failed to read JSON file: {}", e.getMessage());
        }
        return null;
    }

    /**
     * This method returns a String, if it isn't a String, it will return "No key string found"
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys that will be read in list. "key":"value"
     */
    public static String readString(String path, String file, String keys){
        Object result = readObject(path, file, keys);

        return switch (result) {
            case null -> "No key string found";

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
     */
    public static byte readByte(String path, String file, String keys){
        Object result = readObject(path, file, keys);
        if (result instanceof Double) {
            return ((Double) result).byteValue();
        } else if (result instanceof Integer) {
            return (byte) result;
        }
        return 0;
    }

    /**
     * This method returns a Short, if it isn't a Short, it will return 0
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys that will be read in list. "key":"value"
     */
    public static short readShort(String path, String file, String keys){
        Object result = readObject(path, file, keys);
        if (result instanceof Double) {
            return ((Double) result).shortValue();
        } else if (result instanceof Integer) {
            return (short) result;
        }
        return 0;
    }

    /**
     * This method returns an Integer, if it isn't an Integer, it will return 0
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys that will be read in list. "key":"value"
     */
    public static int readInt(String path, String file, String keys){
        Object result = readObject(path, file, keys);
        if (result instanceof Double) {
            return ((Double) result).intValue();
        } else if (result instanceof Integer) {
            return (Integer) result;
        }
        return 0;
    }

    /**
     * This method returns a Float, if it isn't a Float, it will return 0
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys that will be read in list. "key":"value"
     */
    public static float readFloat(String path, String file, String keys){
        Object result = readObject(path, file, keys);
        if (result instanceof Double) {
            return ((Double) result).floatValue();
        } else if (result instanceof Float) {
            return (Float) result;
        }
        return 0;
    }

    /**
     * This method returns a Boolean, if it isn't a Boolean, it will return false
     * @param path Path to the file.
     * @param file The actual file name.
     * @param keys All the keys that will be read in list. "key":"value"
     */
    public static boolean readBoolean(String path, String file, String keys){
        Object result = readObject(path, file, keys);
        if(result != null){
            return (boolean) result;
        } else {
            BP.LOG.info("Boolean value from readBoolean is null!");
            return false;
        }
    }



    /**
     * Creates a JSONObject from a list of keys and values.
     *
     * @param keys   List of keys for the JSONObject.
     * @param values List of values corresponding to the keys.
     * @return A JSONObject containing the specified data.
     */
    public static Object writeNestedObject(List<String> keys, List<Object> values) {
        if (keys.size() != values.size()) {
            throw new IllegalArgumentException("Keys and values lists must have the same length");
        }

        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < keys.size(); i++) {
            jsonObject.put(keys.get(i), values.get(i));
        }
        return jsonObject;
    }

    /**
     * Reads a specific value from a nested object in a JSON file.JSON
     *
     * @param path      Path to the file.
     * @param file      The actual file name.
     * @param objectKey The key of the nested object.
     * @param searchKey The key to read inside the nested object.
     * @return The value found, or null if not found.
     */
    public static Object readNestedObject(String path, String file, String objectKey, String searchKey) {
        if (!path.isEmpty()) {
            path = path + "/";
        }
        try {
            JSONParser parser = new JSONParser(JSON_SIMPLE_TYPE);
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(path + file + ".json"));

            Object nestedObject = jsonObject.get(objectKey);

            if (nestedObject instanceof JSONObject jsonNested) {
                return jsonNested.getOrDefault(searchKey, null);
            } else {
                BP.LOG.info("Key '{}' is not a valid JSON object", objectKey);
            }
        } catch (IOException | ParseException e) {
            BP.LOG.info("Failed to read JSON file: {}", e.getMessage());
        }
        return null;
    }


    /**
     * Replaces the value of a specific key inside a nested JSON object within a file.
     *
     * @param path The path to the file.
     * @param file The actual file name (without .json extension).
     * @param parentKey The key of the parent object (e.g., "free").
     * @param nestedKey The key within the nested object to replace (e.g., "stringer").
     * @param newValue The new value to set.
     */
    public static void replaceNestedValue(String path, String file, String parentKey, String nestedKey, Object newValue) {
        if (!path.isEmpty()) {
            path = path + "/";
        }

        try {
            File jsonFile = new File(path + file + ".json");

            if (!jsonFile.exists()) {
                System.out.println("JSON file does not exist. No action taken.");
                return;
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            Map<String, Object> jsonMap = mapper.readValue(jsonFile, new TypeReference<Map<String, Object>>() {});

            Object parentObject = jsonMap.get(parentKey);
            if (parentObject instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> nestedObject = (Map<String, Object>) parentObject;

                if (nestedObject.containsKey(nestedKey)) {
                    nestedObject.put(nestedKey, newValue);
                    mapper.writeValue(jsonFile, jsonMap);
                } else {
                    System.out.println("Key '" + nestedKey + "' not found inside '" + parentKey + "'.");
                }
            } else {
                System.out.println("The value for key '" + parentKey + "' is not a nested object.");
            }
        } catch (IOException e) {
            System.out.println("Failed to modify JSON file: " + e.getMessage());
        }
    }


    /**
     * Creates a JSON array with values, to be used inside a nested JSON.
     *
     * @param values List of values to be added.
     * @return A JSONArray containing the provided values.
     */
    public static JSONArray writeList(List<Object> values) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(values);
        return jsonArray;
    }

    /**
     * Replaces a value inside a JSON object within a list in a JSON file.
     *
     * @param path     Path to the file.
     * @param file     The actual file name.
     * @param listKey  The key of the list that contains the objects.
     * @param searchKey The key to search for inside the objects.
     * @param newValue The new value to set.
     */
    @SuppressWarnings("unchecked")
    private static void replaceListValue(String path, String file, String listKey, String searchKey, Object newValue) {
        if (!path.isEmpty()) {
            path = path + "/";
        }

        try {
            File jsonFile = new File(path + file + ".json");

            if (!jsonFile.exists()) {
                BP.LOG.info("JSON file {} does not exist. No action taken. ", jsonFile.getAbsolutePath());
                return;
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            Map<String, Object> jsonMap = mapper.readValue(jsonFile, new TypeReference<LinkedHashMap<String, Object>>() {});

            Object listObject = jsonMap.get(listKey);

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
                    BP.LOG.info("Key '{}' not found into list '{}'", searchKey, listKey);
                    return;
                }
            } else {
                BP.LOG.info("Key '{}' is not a list:", listKey);
                return;
            }
            mapper.writeValue(jsonFile, jsonMap);

        } catch (IOException e) {
            BP.LOG.info("|replaceListByKey| Failed to modify JSON file: {}", e.getMessage());
        }
    }

    /**
     * Reads and returns the entire list from a JSON object inside a list within a JSON file.
     *
     * @param path     Path to the file.
     * @param file     The actual file name.
     * @param listKey  The key of the list that contains the objects.
     * @return The list found, null if not found.
     */
    public static JSONArray readList(String path, String file, String listKey) {
        if (!path.isEmpty()) {
            path = path + "/";
        }
        try {
            JSONParser parser = new JSONParser(JSON_SIMPLE_TYPE);
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(path + file + ".json"));

            Object listObject = jsonObject.get(listKey);

            if (listObject instanceof JSONArray jsonArray) {
                return jsonArray;
            } else {
                BP.LOG.info("Key '{}' is not a list", listKey);
            }
        } catch (IOException | ParseException e) {
            BP.LOG.info("|readList| Failed to read JSON file: {}", e.getMessage());
        }
        return null;
    }


    /**
     * Deletes a JSON file if it exists.
     *
     * @param path Path to the folder where the file is located.
     * @param file The actual file name.
     */
    public static void delete(String path, String file) {
        Path jsonFile = Paths.get(path, file + ".json");
        try {
            Files.delete(jsonFile);
            BP.LOG.info("Successfully deleted JSON file: {}", jsonFile.toAbsolutePath());
        } catch (IOException e) {
            BP.LOG.error("Failed to delete JSON file: {}", jsonFile.toAbsolutePath(), e);
        }
    }
}