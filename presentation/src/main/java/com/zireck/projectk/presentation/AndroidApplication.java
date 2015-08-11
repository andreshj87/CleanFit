package com.zireck.projectk.presentation;

import android.app.Application;

import com.zireck.projectk.presentation.dagger.component.ApplicationComponent;

/**
 * Android Main Application
 */
public class AndroidApplication extends Application {

    //private ObjectGraph mObjectGraph;
    private ApplicationComponent mApplicationComponent;

    //private static AndroidApplication mInstance;

    /*
    public AndroidApplication() {
        mInstance = this;
    }

    public static AndroidApplication getInstance() {
        return mInstance;
    }*/

    @Override
    public void onCreate() {
        super.onCreate();

        initializeInjector();
        //mObjectGraph = ObjectGraph.create(getModules().toArray());
        //mObjectGraph.inject(this);
    }

    /*
    private List<Object> getModules() {
        return Arrays.<Object>asList(new AppModule(this));
    }

    public ObjectGraph plus(List<Object> modules) {
        return mObjectGraph.plus(modules.toArray());
    }*/

    private void initializeInjector() {
        /*mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();*/
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
