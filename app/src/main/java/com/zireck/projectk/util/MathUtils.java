package com.zireck.projectk.util;

/**
 * Created by Zireck on 16/07/2015.
 */
public class MathUtils {

    public static String formatDouble(double d) {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }
}
