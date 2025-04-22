package baspig.apis.utils.util;

import java.util.Random;

public class GeneralUtils {

    private static final Random random = new Random();

    /**
     * Check the probability against a random float.
     *
     * @param rate The rate to check.
     * @return True if a random value is higher than the rate value.
     */
    public static Boolean probability(float rate){
        return (random.nextDouble() < rate / 100);
    }

    /**
     * Check the probability against a random float.
     *
     * @param rate The rate to check.
     * @return True if a random value is higher than the rate value.
     */
    public static Boolean probabilityInverse(float rate){
        return (random.nextFloat() * 100 > rate);
    }

    /**
     * This always returns a positive number
     * <p>
     * <pre>{@code like: '-64' inverted: '64'}</pre>
     *
     * @param numberToInvert The number to switch sign.
     * @return The converted value.
     */
    public static byte switchToPositive(byte numberToInvert){
        return (byte) (numberToInvert < 0 ? -numberToInvert : numberToInvert);
    }

    /**
     * This always returns a positive number
     * <p>
     * <pre>{@code like: '-64' inverted: '64'}</pre>
     *
     * @param numberToInvert The number to switch sign.
     * @return The converted value.
     */
    public static short switchToPositive(short numberToInvert){
        return (short) (numberToInvert < 0 ? -numberToInvert : numberToInvert);
    }

    /**
     * This always returns a positive number
     * <p>
     * <pre>{@code like: '-64' inverted: '64'}</pre>
     *
     * @param numberToInvert The number to switch sign.
     * @return The converted value.
     */
    public static int switchToPositive(int numberToInvert){
        return (numberToInvert < 0 ? -numberToInvert : numberToInvert);
    }

    /**
     * This always returns a positive number
     * <p>
     * <pre>{@code like: '-64' inverted: '64'}</pre>
     *
     * @param numberToInvert The number to switch sign.
     * @return The converted value.
     */
    public static long switchToPositive(long numberToInvert){
        return (numberToInvert < 0 ? -numberToInvert : numberToInvert);
    }

    /**
     * This always returns a positive number
     * <p>
     * <pre>{@code like: '-64' inverted: '64'}</pre>
     *
     * @param numberToInvert The number to switch sign.
     * @return The converted value.
     */
    public static float switchToPositive(float numberToInvert){
        return (numberToInvert < 0 ? -numberToInvert : numberToInvert);
    }

    /**
     * This always returns a positive number
     * <p>
     * <pre>{@code like: '-64' inverted: '64'}</pre>
     *
     * @param numberToInvert The number to switch sign.
     * @return The converted value.
     */
    public static double switchToPositive(double numberToInvert){
        return (numberToInvert < 0 ? -numberToInvert : numberToInvert);
    }


    /**
     * This always returns a negative number
     * <p>
     * <pre>{@code like: '64' inverted: '-64'}</pre>
     *
     * @param numberToInvert The number to switch sign.
     * @return The converted value.
     */
    public static byte switchToNegative(byte numberToInvert){
        return (byte) (numberToInvert > 0 ? -numberToInvert : numberToInvert);
    }

    /**
     * This always returns a negative number
     * <p>
     * <pre>{@code like: '64' inverted: '-64'}</pre>
     *
     * @param numberToInvert The number to switch sign.
     * @return The converted value.
     */
    public static short switchToNegative(short numberToInvert){
        return (short) (numberToInvert < 0 ? -numberToInvert : numberToInvert);
    }

    /**
     * This always returns a negative number
     * <p>
     * <pre>{@code like: '64' inverted: '-64'}</pre>
     *
     * @param numberToInvert The number to switch sign.
     * @return The converted value.
     */
    public static int switchToNegative(int numberToInvert){
        return (numberToInvert < 0 ? -numberToInvert : numberToInvert);
    }

    /**
     * This always returns a negative number
     * <p>
     * <pre>{@code like: '64' inverted: '-64'}</pre>
     *
     * @param numberToInvert The number to switch sign.
     * @return The converted value.
     */
    public static long switchToNegative(long numberToInvert){
        return (numberToInvert < 0 ? -numberToInvert : numberToInvert);
    }

    /**
     * This always returns a negative number
     * <p>
     * <pre>{@code like: '64' inverted: '-64'}</pre>
     *
     * @param numberToInvert The number to switch sign.
     * @return The converted value.
     */
    public static float switchToNegative(float numberToInvert){
        return (numberToInvert < 0 ? -numberToInvert : numberToInvert);
    }

    /**
     * This always returns a negative number
     * <p>
     * <pre>{@code like: '64' inverted: '-64'}</pre>
     *
     * @param numberToInvert The number to switch sign.
     * @return The converted value.
     */
    public static double switchToNegative(double numberToInvert){
        return (numberToInvert < 0 ? -numberToInvert : numberToInvert);
    }
}
