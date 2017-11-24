package com.mposhatov.util;

import org.apache.commons.math3.random.RandomDataGenerator;

public class Calculator {

    public static int calculatePercentageOf(int percent, int value) {
        return percent * value / 100;
    }

    public static int generateNumberFromTo(int from, int to) {
        return from == to ? to : new RandomDataGenerator().nextInt(from, to);
    }

}
