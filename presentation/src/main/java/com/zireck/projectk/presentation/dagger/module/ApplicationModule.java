package com.zireck.projectk.presentation.dagger.module;

import android.content.Context;

import com.zireck.projectk.presentation.AndroidApplication;
import com.zireck.projectk.presentation.helper.Navigator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {

    private final AndroidApplication mApplication;

    public ApplicationModule(AndroidApplication application) {
        mApplication = application;
    }

    @Provides @Singleton
    Context provideApplicationContext() {
        return mApplication;
    }

    @Provides @Singleton
    Navigator provideNavigator() {
        return new Navigator();
    }

}
