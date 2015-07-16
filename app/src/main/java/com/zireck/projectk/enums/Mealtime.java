package com.zireck.projectk.enums;

/**
 * Created by Zireck on 14/07/2015.
 */
public enum Mealtime {
    BREAKFAST(1, "Breakfast"),
    LUNCH(2, "Lunch"),
    SNACK(3, "Snack"),
    DINNER(4, "Dinner"),
    UNKNOWN(5, "Unknown");

    private final int intValue;
    private final String stringValue;

    private Mealtime(int intValue, String stringValue) {
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
