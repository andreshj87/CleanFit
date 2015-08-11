package com.zireck.projectk.presentation.enumeration;

/**
 * Created by Zireck on 13/07/2015.
 */
public enum Goal {
    MAINTAIN(1, "Maintain"),
    BURN(2, "Burn"),
    GAIN(3, "Gain");

    private final int intValue;
    private final String stringValue;

    private Goal(int intValue, String stringValue) {
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
