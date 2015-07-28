package com.zireck.projectk.util;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Zireck on 28/07/2015.
 */
public class SnackbarUtils {

    private SnackbarUtils() {

    }

    public static void showShortMessage(View view, String message) {
        getSnackbar(view, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLongMessage(View view, String message) {
        getSnackbar(view, message, Toast.LENGTH_LONG).show();
    }

    public static void showError(final View view, final String message) {
        getSnackbar(view, message).show();
    }

    private static Snackbar getSnackbar(View view, String message) {
        return Snackbar.make(view, message, Snackbar.LENGTH_LONG);
    }

    private static Snackbar getSnackbar(View view, String message, int length) {
        return Snackbar.make(view, message, length);
    }

}
