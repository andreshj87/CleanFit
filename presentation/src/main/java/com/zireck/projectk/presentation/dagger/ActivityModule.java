package com.zireck.projectk.presentation.dagger;

import android.app.Activity;
import android.content.Context;

import com.zireck.projectk.presentation.activity.AddFoodActivity;
import com.zireck.projectk.presentation.activity.AddMealActivity;
import com.zireck.projectk.presentation.activity.EditFoodActivity;
import com.zireck.projectk.presentation.activity.FoodDetailActivity;
import com.zireck.projectk.presentation.activity.MainActivity;
import com.zireck.projectk.presentation.helper.Navigator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module created to provide some common activity scope dependencies as @ActivityContext.
 * This module is going to be added to the graph generated for every activity while the activity
 * creation lifecycle.
 *
 * @author Pedro Vicente Gómez Sánchez
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