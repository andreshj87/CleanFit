package com.zireck.projectk.presentation.enumeration;

import android.content.Context;

import com.zireck.projectk.R;

/**
 * Created by Zireck on 13/07/2015.
 */
public enum Goal {
    MAINTAIN(1, R.string.maintain),
    BURN(2, R.string.burn),
    GAIN(3, R.string.gain);

    private final int intValue;
    private final int resourceValue;

    private Goal(int intValue, int resourceValue) {
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

    public static Goal fromValue(int intValue) {
        switch (intValue) {
            case 1:
                return Goal.MAINTAIN;
            case 2:
                return Goal.BURN;
            case 3:
                return Goal.GAIN;
            default:
                return null;
        }
    }

    public static Goal fromValue(Context context, String stringValue) {
        if (stringValue.equals(context.getString(Goal.MAINTAIN.getResourceValue()))) {
            return Goal.MAINTAIN;
        } else if (stringValue.equals(context.getString(Goal.BURN.getResourceValue()))) {
            return Goal.BURN;
        } else if (stringValue.equals(context.getString(Goal.GAIN.getResourceValue()))) {
            return Goal.GAIN;
        } else {
            return null;
        }
    }

    public static String[] getStringValues(Context context) {
        Goal[] goals = Goal.values();

        String[] stringValues = new String[goals.length];
        for (int i=0; i<stringValues.length; i++) {
            stringValues[i] = context.getString(goals[i].getResourceValue());
        }

        return stringValues;
    }
}
