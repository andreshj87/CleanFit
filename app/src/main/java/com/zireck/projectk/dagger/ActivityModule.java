package com.zireck.projectk.dagger;

import android.app.Activity;
import android.content.Context;

import com.zireck.projectk.activity.AddFoodActivity;
import com.zireck.projectk.activity.AddMealActivity;
import com.zireck.projectk.activity.EditFoodActivity;
import com.zireck.projectk.activity.FoodDetailActivity;
import com.zireck.projectk.activity.MainActivity;
import com.zireck.projectk.helper.Navigator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module created to provide some common activity scope dependencies as @ActivityContext.
 * This module is going to be added to the graph generated for every activity while the activity
 * creation lifecycle.
 *
 * @author Pedro Vicente G�mez S�nchez
 */
@Module(
        injects = {
                MainActivity.class,
                AddFoodActivity.class,
                EditFoodActivity.class,
                FoodDetailActivity.class,
                AddMealActivity.class
        },
        library = true
)
public final class ActivityModule {

    private final Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides @ActivityContext
    public Context provideActivityContext() {
        return mActivity;
    }

    @Provides @Singleton
    public Activity provideActivity() {
        return mActivity;
    }

    @Provides @Singleton
    public Navigator provideNavigator() {
        return new Navigator(mActivity);
    }
}