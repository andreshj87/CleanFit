package com.zireck.projectk.presentation.enumeration;

/**
 * Created by Zireck on 13/07/2015.
 */
public enum ActivityFactor {
    SEDENTARY(1, 1.2, "Sedentary"),
    LIGHT(2, 1.375, "Light"),
    MODERATE(3, 1.55, "Moderate"),
    HEAVY(4, 1.725, "Heavy"),
    VERY_HEAVY(5, 1.9, "Very Heavy");

    private final int intValue;
    private final double doubleValue;
    private final String stringValue;

    private ActivityFactor(int intValue, double doubleValue, String stringValue) {
        this.intValue = intValue;
        this.doubleValue = doubleValue;
        this.stringValue = stringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
