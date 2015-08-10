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

    public static String[] getValues() {
        String[] values = new String[Mealtime.values().length];

        Mealtime[] mealtimes = Mealtime.values();
        for (int i=0; i<values.length; i++) {
            values[i] = mealtimes[i].getStringValue();
        }

        return values;
    }

    public static int getIntValueForString(String string) {
        for (int i=0; i<getValues().length; i++) {
            if (getValues()[i].equalsIgnoreCase(string)) {
                return i;
            }
        }

        return -1;
    }
}
