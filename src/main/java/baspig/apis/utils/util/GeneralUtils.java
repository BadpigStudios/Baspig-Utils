package baspig.apis.utils.util;

import java.util.Random;

public class GeneralUtils {

    private static final Random random = new Random();

    /**
     * Check the probability against a random float.
     *
     * @param rate The rate to check.
     * @return True if it's mayor to the random float.
     */
    public static Boolean probability(float rate){
        return (random.nextFloat() * 100 < rate);
    }
}
