package com.zireck.projectk.presentation.application;

import android.app.Application;

import com.zireck.projectk.presentation.dagger.AppModule;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by Zireck on 16/07/2015.
 */
public class App extends Application {

    private ObjectGraph mObjectGraph;

    private static App mInstance;

    public App() {
        mInstance = this;
    }

    public static App getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mObjectGraph = ObjectGraph.create(getModules().toArray());
        mObjectGraph.inject(this);
    }

    private List<Object> getModules() {
        return Arrays.<Object>asList(new AppModule(this));
    }

    public ObjectGraph plus(List<Object> modules) {
        return mObjectGraph.plus(modules.toArray());
    }
}
