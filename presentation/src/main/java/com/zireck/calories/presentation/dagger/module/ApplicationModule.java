package com.zireck.calories.presentation.dagger.module;

import android.content.Context;

import com.zireck.calories.data.executor.JobExecutor;
import com.zireck.calories.data.repository.MealDataRepository;
import com.zireck.calories.presentation.AndroidApplication;
import com.zireck.calories.presentation.mapper.FoodModelDataMapper;
import com.zireck.calories.presentation.mapper.MealModelDataMapper;
import com.zireck.calories.presentation.mapper.UserModelDataMapper;
import com.zireck.calories.presentation.navigation.Navigator;
import com.zireck.calories.data.repository.FoodDataRepository;
import com.zireck.calories.data.repository.UserDataRepository;
import com.zireck.calories.presentation.UIThread;

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

    @Provides @Singleton
    com.zireck.calories.domain.executor.ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides @Singleton
    com.zireck.calories.domain.executor.PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides @Singleton
    com.zireck.calories.domain.repository.FoodRepository provideFoodRepository(FoodDataRepository foodDataRepository) {
        return foodDataRepository;
    }

    @Provides @Singleton
    com.zireck.calories.domain.repository.MealRepository provideMealRepository(MealDataRepository mealDataRepository) {
        return mealDataRepository;
    }

    @Provides @Singleton
    com.zireck.calories.domain.repository.UserRepository provideUserRepository(UserDataRepository userDataRepository) {
        return userDataRepository;
    }

    @Provides @Singleton
    FoodModelDataMapper provideFoodModelDataMapper() {
        return new FoodModelDataMapper();
    }

    @Provides @Singleton
    MealModelDataMapper provideMealModelDataMapper(FoodModelDataMapper foodModelDataMapper) {
        return new MealModelDataMapper(foodModelDataMapper);
    }

    @Provides @Singleton
    UserModelDataMapper provideUserModelDataMapper() {
        return new UserModelDataMapper();
    }
}
