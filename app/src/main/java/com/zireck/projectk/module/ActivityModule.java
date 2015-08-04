package com.zireck.projectk.module;

import android.app.Activity;
import android.content.Context;

import com.zireck.projectk.activity.AddFoodActivity;
import com.zireck.projectk.activity.EditFoodActivity;
import com.zireck.projectk.activity.FoodDetailActivity;
import com.zireck.projectk.activity.MainActivity;

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
                FoodDetailActivity.class
        },
        library = true
)
public final class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides @ActivityContext
    Context provideActivityContext() {
        return activity;
    }
}