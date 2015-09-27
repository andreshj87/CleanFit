package com.zireck.calories.presentation.dagger.module;

import com.zireck.calories.presentation.dagger.PerActivity;
import com.zireck.calories.domain.interactor.AddMeal;
import com.zireck.calories.domain.interactor.DeleteAllMeals;
import com.zireck.calories.domain.interactor.EditMeal;
import com.zireck.calories.domain.interactor.GetMealList;
import com.zireck.calories.domain.repository.MealRepository;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zireck on 25/08/2015.
 */
@Module
public class MealModule {

    private long mMealId = -1;
    private com.zireck.calories.domain.Meal mMeal;

    public MealModule() {

    }

    public MealModule(long mealId) {
        mMealId = mealId;
    }

    public MealModule(com.zireck.calories.domain.Meal meal) {
        mMeal = meal;
    }

    @Provides @PerActivity
    @Named("mealList")
    com.zireck.calories.domain.interactor.Interactor provideGetMealListInteractor(GetMealList getMealList) {
        return getMealList;
    }

    @Provides @PerActivity @Named("mealListForDate")
    com.zireck.calories.domain.interactor.GetMealListForDate provideGetMealListForDateInteractor(MealRepository mealRepository, com.zireck.calories.domain.executor.ThreadExecutor threadExecutor, com.zireck.calories.domain.executor.PostExecutionThread postExecutionThread) {
    //Interactor provideGetMealListForDateInteractor(GetMealListForDate getMealListForDate) {
        return new com.zireck.calories.domain.interactor.GetMealListForDate(mealRepository, threadExecutor, postExecutionThread);
        //return getMealListForDate;
    }

    @Provides @PerActivity @Named("mealDetails")
    com.zireck.calories.domain.interactor.Interactor provideGetMealDetailsInteractor(MealRepository mealRepository,
                        com.zireck.calories.domain.executor.ThreadExecutor threadExecutor, com.zireck.calories.domain.executor.PostExecutionThread postExecutionThread) {
        return new com.zireck.calories.domain.interactor.GetMealDetails(mMeal, mealRepository, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity @Named("addMeal")
    AddMeal provideAddMealInteractor(MealRepository mealRepository,
                        com.zireck.calories.domain.executor.ThreadExecutor threadExecutor, com.zireck.calories.domain.executor.PostExecutionThread postExecutionThread) {
        return new AddMeal(mealRepository, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity @Named("editMeal")
    com.zireck.calories.domain.interactor.Interactor provideEditMealInteractor(MealRepository mealRepository,
                        com.zireck.calories.domain.executor.ThreadExecutor threadExecutor, com.zireck.calories.domain.executor.PostExecutionThread postExecutionThread) {
        return new EditMeal(mealRepository, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity @Named("deleteMeal")
    com.zireck.calories.domain.interactor.DeleteMeal provideDeleteMealInteractor(MealRepository mealRepository,
                        com.zireck.calories.domain.executor.ThreadExecutor threadExecutor, com.zireck.calories.domain.executor.PostExecutionThread postExecutionThread) {
        return new com.zireck.calories.domain.interactor.DeleteMeal(mealRepository, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity @Named("deleteAllMeals")
    com.zireck.calories.domain.interactor.Interactor provideDeleteAllMealsInteractor(MealRepository mealRepository, com.zireck.calories.domain.executor.ThreadExecutor threadExecutor, com.zireck.calories.domain.executor.PostExecutionThread postExecutionThread) {
        return new DeleteAllMeals(mealRepository, threadExecutor, postExecutionThread);
    }
}
