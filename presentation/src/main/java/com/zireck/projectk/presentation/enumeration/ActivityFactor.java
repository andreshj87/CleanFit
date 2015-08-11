package com.zireck.projectk.presentation.enumeration;

/**
 * Created by Zireck on 13/07/2015.
 */
public enum ActivityFactor {
    SEDENTARY(1, 1.2),
    LIGHT(2, 1.375),
    MODERATE(3, 1.55),
    HEAVY(4, 1.725),
    VERY_HEAVY(5, 1.9);

    private final double intValue;
    private final double doubleValue;

    private ActivityFactor(int intValue, double doubleValue) {
        this.intValue = intValue;
        this.doubleValue = doubleValue;
    }

    public double getIntValue() {
        return intValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }
}
