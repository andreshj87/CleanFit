package com.zireck.calories.presentation.dagger.component;

import android.app.Activity;

import com.zireck.calories.presentation.dagger.PerActivity;
import com.zireck.calories.presentation.dagger.module.ActivityModule;

import dagger.Component;

/**
 * A base component upon which fragment's components may depend.
 * Activity-level components should extend this component.
 *
 * Subtypes of ActivityComponent should be decorated with annotation:
 * {@link PerActivity}
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    //Exposed to sub-graphs.
    Activity activity();
}