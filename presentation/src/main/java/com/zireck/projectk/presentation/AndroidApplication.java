package com.zireck.projectk.presentation;

import android.app.Application;

import com.zireck.projectk.presentation.dagger.component.ApplicationComponent;
import com.zireck.projectk.presentation.dagger.component.DaggerApplicationComponent;
import com.zireck.projectk.presentation.dagger.module.ApplicationModule;

/**
 * Android Main Application
 */
public class AndroidApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initializeInjector();
    }

    private void initializeInjector() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
