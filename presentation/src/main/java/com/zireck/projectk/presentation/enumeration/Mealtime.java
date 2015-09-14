package com.zireck.projectk.presentation.enumeration;

import android.content.Context;

import com.zireck.projectk.R;

/**
 * Created by Zireck on 14/07/2015.
 */
public enum Mealtime {
    BREAKFAST(1, R.string.breakfast),
    LUNCH(2, R.string.lunch),
    SNACK(3, R.string.snack),
    DINNER(4, R.string.dinner),
    OTHER(5, R.string.other);

    private final int intValue;
    private final int resourceValue;

    private Mealtime(int intValue, int resourceValue) {
        this.intValue = intValue;
        this.resourceValue = resourceValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public int getResourceValue() {
        return resourceValue;
    }

    public String toString(Context context) {
        return context.getString(resourceValue);
    }

    public static Mealtime fromValue(int intValue) {
        switch (intValue) {
            case 1:
                return Mealtime.BREAKFAST;
            case 2:
                return Mealtime.LUNCH;
            case 3:
                return Mealtime.SNACK;
            case 4:
                return Mealtime.DINNER;
            case 5:
                return Mealtime.OTHER;
            default:
                return null;
        }
    }

    public static Mealtime fromValue(Context context, String stringValue) {
        if (stringValue.equals(context.getString(Mealtime.BREAKFAST.getResourceValue()))) {
            return Mealtime.BREAKFAST;
        } else if (stringValue.equals(context.getString(Mealtime.LUNCH.getResourceValue()))) {
            return Mealtime.LUNCH;
        } else if (stringValue.equals(context.getString(Mealtime.SNACK.getResourceValue()))) {
            return Mealtime.SNACK;
        } else if (stringValue.equals(context.getString(Mealtime.DINNER.getResourceValue()))) {
            return Mealtime.DINNER;
        } else if (stringValue.equals(context.getString(Mealtime.OTHER.getResourceValue()))) {
            return Mealtime.OTHER;
        } else {
            return null;
        }
    }

    public static String[] getStringValues(Context context) {
        Mealtime[] mealtimes = Mealtime.values();

        String[] stringValues = new String[mealtimes.length];
        for (int i=0; i<stringValues.length; i++) {
            stringValues[i] = context.getString(mealtimes[i].getResourceValue());
        }

        return stringValues;
    }
}
