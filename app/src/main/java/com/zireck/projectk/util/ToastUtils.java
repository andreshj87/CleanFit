package com.zireck.projectk.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Zireck on 16/07/2015.
 */
public class ToastUtils {

    private ToastUtils() {

    }

    public static void showShortMessage(Context context, String message) {
        getToast(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLongMessage(Context context, String message) {
        getToast(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showError(final Context context, final String message) {
        getToast(context, message).show();
    }

    private static Toast getToast(Context context, String message) {
        return Toast.makeText(context, message, Toast.LENGTH_LONG);
    }

    private static Toast getToast(Context context, String message, int length) {
        return Toast.makeText(context, message, length);
    }

}
