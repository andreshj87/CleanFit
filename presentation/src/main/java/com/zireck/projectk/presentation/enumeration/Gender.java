package com.zireck.projectk.presentation.enumeration;

/**
 * Created by Zireck on 13/07/2015.
 */
public enum Gender {
    MALE(1, "Male"),
    FEMALE(2, "Female");

    private final int intValue;
    private final String stringValue;

    private Gender(int intValue, String stringValue) {
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
