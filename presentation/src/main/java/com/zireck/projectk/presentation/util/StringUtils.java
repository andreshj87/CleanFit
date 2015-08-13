package com.zireck.projectk.presentation.util;

/**
 * Some utility methods related with the String class.
 */
public class StringUtils {

    private static final String EMPTY_STRING = "";

    private StringUtils() {
        //Empty
    }

    public static boolean isNullOrEmpty(final String string) {
        return string == null || EMPTY_STRING.equals(string);
    }
}
