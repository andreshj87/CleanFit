package com.zireck.projectk.presentation.enumeration;

import android.content.Context;

import com.zireck.projectk.R;

/**
 * Created by Zireck on 13/07/2015.
 */
public enum ActivityFactor {
    SEDENTARY(1, 1.2, R.string.sedentary),
    LIGHT(2, 1.375, R.string.light),
    MODERATE(3, 1.55, R.string.moderate),
    HEAVY(4, 1.725, R.string.heavy),
    VERY_HEAVY(5, 1.9, R.string.very_heavy);

    private final int intValue;
    private final double doubleValue;
    private final int resourceValue;

    private ActivityFactor(int intValue, double doubleValue, int resourceValue) {
        this.intValue = intValue;
        this.doubleValue = doubleValue;
        this.resourceValue = resourceValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public int getResourceValue() {
        return resourceValue;
    }

    public String toString(Context context) {
        return context.getString(resourceValue);
    }

    public static ActivityFactor fromValue(int intValue) {
        switch (intValue) {
            case 1:
                return ActivityFactor.SEDENTARY;
            case 2:
                return ActivityFactor.LIGHT;
            case 3:
                return ActivityFactor.MODERATE;
            case 4:
                return ActivityFactor.HEAVY;
            case 5:
                return ActivityFactor.VERY_HEAVY;
            default:
                return null;
        }
    }

    public static ActivityFactor fromValue(Context context, String stringValue) {
        if (stringValue.equals(context.getString(ActivityFactor.SEDENTARY.getResourceValue()))) {
            return ActivityFactor.SEDENTARY;
        } else if (stringValue.equals(context.getString(ActivityFactor.LIGHT.getResourceValue()))) {
            return ActivityFactor.LIGHT;
        } else if (stringValue.equals(context.getString(ActivityFactor.MODERATE.getResourceValue()))) {
            return ActivityFactor.MODERATE;
        } else if (stringValue.equals(context.getString(ActivityFactor.HEAVY.getResourceValue()))) {
            return ActivityFactor.HEAVY;
        } else if (stringValue.equals(context.getString(ActivityFactor.VERY_HEAVY.getResourceValue()))) {
            return ActivityFactor.VERY_HEAVY;
        } else {
            return null;
        }
    }

    public static String[] getStringValues(Context context) {
        ActivityFactor[] activityFactors = ActivityFactor.values();

        String[] stringValues = new String[activityFactors.length];
        for (int i=0; i<stringValues.length; i++) {
            stringValues[i] = context.getString(activityFactors[i].getResourceValue());
        }

        return stringValues;
    }
}
