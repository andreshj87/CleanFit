package com.zireck.calories.presentation.dagger.component;

import android.content.Context;

import com.zireck.calories.presentation.view.activity.BaseActivity;
import com.zireck.calories.domain.executor.ThreadExecutor;
import com.zireck.calories.domain.repository.FoodRepository;
import com.zireck.calories.domain.repository.MealRepository;
import com.zireck.calories.domain.repository.UserRepository;
import com.zireck.calories.presentation.dagger.module.ApplicationModule;
import com.zireck.calories.presentation.mapper.FoodModelDataMapper;
import com.zireck.calories.presentation.mapper.MealModelDataMapper;
import com.zireck.calories.presentation.mapper.UserModelDataMapper;
import com.zireck.calories.presentation.navigation.Navigator;

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
    com.zireck.calories.domain.executor.PostExecutionThread postExecutionThread();
    FoodRepository foodRepository();
    MealRepository mealRepository();
    UserRepository userRepository();
    FoodModelDataMapper foodModelDataMapper();
    MealModelDataMapper mealModelDataMapper();
    UserModelDataMapper userModelDataMapper();
}
