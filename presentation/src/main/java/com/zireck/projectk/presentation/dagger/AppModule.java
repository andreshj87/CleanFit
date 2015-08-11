package com.zireck.projectk.presentation.dagger;

import android.content.Context;

import com.zireck.projectk.presentation.application.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zireck on 03/08/2015.
 */
@Module(
        injects = App.class,
        includes = {
                InteractorsModule.class
        },
        library = true
)
public class AppModule {

    private App mApp;

    public AppModule(App app) {
        mApp = app;
    }

    @Provides @Singleton
    public Context provideApplicationContext() {
        return mApp;
    }
}
