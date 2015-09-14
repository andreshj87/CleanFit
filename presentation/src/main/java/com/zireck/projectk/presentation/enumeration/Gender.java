package com.zireck.projectk.presentation.enumeration;

import android.content.Context;

import com.zireck.projectk.R;

/**
 * Created by Zireck on 13/07/2015.
 */
public enum Gender {
    MALE(1, R.string.male),
    FEMALE(2, R.string.female);

    private final int intValue;
    private final int resourceValue;

    private Gender(int intValue, int resourceValue) {
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

    public static Gender fromValue(int intValue) {
        switch (intValue) {
            case 1:
                return Gender.MALE;
            case 2:
                return Gender.FEMALE;
            default:
                return null;
        }
    }

    public static Gender fromValue(Context context, String stringValue) {
        if (stringValue.equals(context.getString(Gender.MALE.getResourceValue()))) {
            return Gender.MALE;
        } else if (stringValue.equals(context.getString(Gender.FEMALE.getResourceValue()))) {
            return Gender.FEMALE;
        } else {
            return null;
        }
    }

    public static String[] getStringValues(Context context) {
        Gender[] genders = Gender.values();

        String[] stringValues = new String[genders.length];
        for (int i=0; i<stringValues.length; i++) {
            stringValues[i] = context.getString(genders[i].getResourceValue());
        }

        return stringValues;
    }
}
