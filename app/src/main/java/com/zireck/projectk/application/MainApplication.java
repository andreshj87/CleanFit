package com.zireck.projectk.application;

import android.app.Application;

/**
 * Created by Zireck on 16/07/2015.
 */
public class MainApplication extends Application {

    private static MainApplication mInstance;

    public MainApplication() {
        mInstance = this;
    }

    public static MainApplication getInstance() {
        return mInstance;
    }
}
