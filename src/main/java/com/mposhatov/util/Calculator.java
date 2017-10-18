package com.mposhatov.util;

import org.apache.commons.math3.random.RandomDataGenerator;

public class Calculator {

    public static long calculatePercentageOf(long percent, long value) {
        return percent * value / 100;
    }

    public static long generateNumberFromTo(long from, long to) {
        return from == to ? to : new RandomDataGenerator().nextLong(from, to);
    }

}
