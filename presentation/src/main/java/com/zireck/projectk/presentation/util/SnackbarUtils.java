package com.zireck.projectk.presentation.util;

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

    public static void showShortMessageWithElevation(View view, String message, int elevation) {
        getSnackbarWithElevation(view, message, elevation, Snackbar.LENGTH_SHORT).show();
    }

    public static void showLongMessageWithElevation(View view, String message, int elevation) {
        getSnackbarWithElevation(view, message, elevation, Snackbar.LENGTH_LONG).show();
    }

    private static Snackbar getSnackbarWithElevation(View view, String message, int elevation, int length) {
        Snackbar snackbar = Snackbar.make(view, message, length);
        snackbar.getView().setElevation(elevation);
        return snackbar;
    }

}
