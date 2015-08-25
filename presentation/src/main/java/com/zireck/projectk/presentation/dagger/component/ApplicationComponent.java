package com.zireck.projectk.presentation.dagger.component;

import android.content.Context;

import com.zireck.projectk.domain.executor.PostExecutionThread;
import com.zireck.projectk.domain.executor.ThreadExecutor;
import com.zireck.projectk.domain.repository.FoodRepository;
import com.zireck.projectk.domain.repository.MealRepository;
import com.zireck.projectk.presentation.dagger.module.ApplicationModule;
import com.zireck.projectk.presentation.mapper.FoodModelDataMapper;
import com.zireck.projectk.presentation.mapper.MealModelDataMapper;
import com.zireck.projectk.presentation.navigation.Navigator;
import com.zireck.projectk.presentation.view.activity.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    // Exposed to sub-graphs.
    Context context();
    Navigator navigator();
    ThreadExecutor threadExecutor();
    PostExecutionThread postExecutionThread();
    FoodRepository foodRepository();
    MealRepository mealRepository();
    FoodModelDataMapper foodModelDataMapper();
    MealModelDataMapper mealModelDataMapper();
}
