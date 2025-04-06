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
        return (random.nextFloat() * 100 > rate);
    }

    /**
     * Check the probability against a random float.
     *
     * @param rate The rate to check.
     * @return True if a random value is lower than the rate value.
     */
    public static Boolean probabilityLessThan(float rate){
        return (random.nextFloat() * 100 < rate);
    }

    /**
     * Check the probability against a random float.
     *
     * @param rate The rate to check.
     * @return True if a random value is higher than the rate value.
     */
    public static Boolean probabilityMoreThan(float rate){
        return (random.nextFloat() * 100 > rate);
    }
}
