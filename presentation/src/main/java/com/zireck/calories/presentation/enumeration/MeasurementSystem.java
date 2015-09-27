package com.zireck.calories.presentation.enumeration;

/**
 * Created by Zireck on 13/07/2015.
 */
@Deprecated
public enum MeasurementSystem {
    METRIC(1, "Metric"),
    IMPERIAL(2, "Imperial");

    private final int intValue;
    private final String stringValue;

    private MeasurementSystem(int intValue, String stringValue) {
        this.intValue = intValue;
        this.stringValue = stringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
