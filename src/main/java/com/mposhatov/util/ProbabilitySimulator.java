package com.mposhatov.util;

import org.apache.commons.math3.random.RandomDataGenerator;

public class ProbabilitySimulator {

    public static boolean isLucky(long probability) {
        boolean result = false;
        if(new RandomDataGenerator().nextLong(0, 100) <= probability) {
            result = true;
        }
        return result;
    }

}
