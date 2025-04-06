package baspig.apis.utils.util;

import baspig.apis.utils.Baspig_utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Quick logger.
 * <p>
 * Is a way to know what is happening. <p>
 * What class is giving errors?
 * @author Baspig_
 */
@SuppressWarnings("unused")
public class BP {

    public static final Logger LOG = LoggerFactory.getLogger(Baspig_utils.MOD_ID);

    public static final String classColor = ConsoleColors.YELLOW;

    public static final String enumColor = ConsoleColors.PURPLE;

    public static final String methodColor = ConsoleColors.GREEN;

    public static final String numberColor = ConsoleColors.CYAN_BRIGHT;

    public static final String stringColor = ConsoleColors.GREEN_BRIGHT;

    public static final String booleanColor = ConsoleColors.YELLOW_BRIGHT;

    public static final String errorColor = ConsoleColors.RED;

    public static final String nullColor = ConsoleColors.RED_BRIGHT;

    /**
     * It's a faster way to test it. Recommendable to use object colors into it ny hand.
     *
     * @param Class The class that must be related to the log message.
     * @param methodName The name of the method that uses this log.
     * @param errorMessage The message to be shown on log throw.
     */
    public static void fancyLog(Class<?> Class, String methodName, String... errorMessage){

        StringBuilder messageAdditions = new StringBuilder(); //To add the log messages into one variable.

        /// We search for each error message in the given arguments.
        for(String string : errorMessage){
            messageAdditions.append(".\n ").append(string); //concatenation for each message.
        }
        /// Uses inner class LOGGER to log set up the message.
        LOG.warn("[{} - {}]-- {}", Class.getName(), methodName, messageAdditions);
    }

    /**
     * It's a faster way to test it. Recommendable to use object colors into it ny hand.
     *
     * @param Class The class that must be related to the log message.
     * @param methodName The name of the method that uses this log.
     * @param errorMessage The message to be shown on log throw.
     */
    public static void fancyLog(String Class, String methodName, String... errorMessage){
        StringBuilder messageAdditions = new StringBuilder(); //To add the log messages into one variable.

        /// We search for each error message in the given arguments.
        for(String string : errorMessage){
            messageAdditions.append(".\n ").append(string); //concatenation for each message.
        }
        /// Uses inner class LOGGER to log set up the message.
        LOG.warn("[{} - \"{}\"]-- {}", Class, methodName, messageAdditions);
    }

    /**
     * It's a faster way to test it. Recommendable to use object colors into it ny hand.
     *
     * @param Class The class that must be related to the log message.
     * @param exception The exception/info to log.
     * @param returnEmptyType The type of value you configured into your method to return safety, it also will be shown on top the log.
     */
    public static void fancyReturnLog(Class<?> Class, Exception exception, ReturnType returnEmptyType){
        /// Gets the returnType name from the given Enum argument 'ReturnType'.
        String data = returnEmptyType.name().toLowerCase();

        /// Uses inner class LOGGER to log set up the message.
        LOG.warn("[{}]-- Returned {} value!\n {}", Class.getName(), data, exception.getMessage());
    }

    /**
     * It's a faster way to test it.
     *
     * @param Class The class that must be related to the log message.
     * @param methodName Is the name of the method that is being executed.
     * @param exception The exception/info to log.
     * @param returnEmptyType The type of value you configured into your method to return safety, it also will be shown on top the log.
     */
    public static void fancyReturnLog(String methodName ,Class<?> Class, Exception exception, ReturnType returnEmptyType){
        /// Gets the returnType name from the given Enum argument 'ReturnType'
        String data = returnEmptyType.name().toLowerCase();

        /// Uses inner class LOGGER to log set up the message.
        LOG.warn("[{} in {}]-- Returned {} value!\n {}", methodName,Class.getName(), data, exception.getMessage());
    }

    /**
     * It's a faster way to test it. Recommendable to use object colors into it ny hand.
     *
     * @param Class The class that must be related to the log message.
     * @param methodName The name of the method that uses this log.
     * @param errorMessage The message to be shown on log throw.
     * @param filePath the absolute path to the file that logs error.
     */
    public static void fancyFileLog(Class<?> Class, String methodName, String errorMessage,String filePath){
        /// Uses inner class LOGGER to log set up the message.
        LOG.warn("[{} - {}]-- {} :{}", Class.getName(), methodName, errorMessage, filePath);
    }

    /**
     * It's a faster way to test it. Recommendable to use object colors into it ny hand.
     *
     * @param Class The class that must be related to the log message.
     * @param methodName The name of the method that uses this log.
     * @param errorMessage The message to be shown on log throw.
     * @param filePath the absolute path to the file that logs error.
     */
    public static void fancyFileLog(String Class, String methodName, String errorMessage,String filePath){
        /// Uses inner class LOGGER to log set up the message.
        LOG.warn("[{} - \"{}\"]-- {} :{}", Class, methodName, errorMessage, filePath);
    }

    /**
     * It's a faster way to test it. Recommendable to use object colors into it ny hand.
     *
     * @param methodName The name of the method that uses this log.
     * @param errorMessage The message to be shown on log throw.
     * @param filePath the absolute path to the file that logs error.
     */
    public static void fancyFileLog(String methodName, String errorMessage,String filePath){
        /// Uses inner class LOGGER to log set up the message.
        LOG.warn("[{}]-- {} :{}", methodName, errorMessage, filePath);
    }

    public static class Return{

        /**
         * It's a faster way to test it. Recommendable to use object colors into it ny hand.
         *
         * @param Class The class that must be related to the log message.
         * @param methodName The name of the method that uses this log.
         * @param errorMessage The message to be shown on log throw.
         */
        public static String fancyLog(Class<?> Class, String methodName, String... errorMessage){

            StringBuilder messageAdditions = new StringBuilder(); //To add the log messages into one variable.

            /// We search for each error message in the given arguments.
            for(String string : errorMessage){
                messageAdditions.append(".\n ").append(string); //concatenation for each message.
            }
            /// Uses inner class LOGGER to log set up the message.
            return "[" + Class.getName() +" - " + methodName +"]-- " + messageAdditions;
        }

        /**
         * It's a faster way to test it. Recommendable to use object colors into it ny hand.
         *
         * @param Class The class that must be related to the log message.
         * @param methodName The name of the method that uses this log.
         * @param errorMessage The message to be shown on log throw.
         */
        public static String fancyLog(String Class, String methodName, String... errorMessage){

            StringBuilder messageAdditions = new StringBuilder(); //To add the log messages into one variable.

            /// We search for each error message in the given arguments.
            for(String string : errorMessage){
                messageAdditions.append(".\n ").append(string); //concatenation for each message.
            }
            /// Uses inner class LOGGER to log set up the message.
            return "[" + Class +" - " + methodName +"]-- " + messageAdditions;

        }
    }

    private interface LogOptions{
        void hasLog();
    }
}