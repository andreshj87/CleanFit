package com.zireck.projectk.presentation.dagger.module;

import com.zireck.projectk.domain.Meal;
import com.zireck.projectk.domain.executor.PostExecutionThread;
import com.zireck.projectk.domain.executor.ThreadExecutor;
import com.zireck.projectk.domain.interactor.AddMeal;
import com.zireck.projectk.domain.interactor.DeleteAllMeals;
import com.zireck.projectk.domain.interactor.DeleteMeal;
import com.zireck.projectk.domain.interactor.EditMeal;
import com.zireck.projectk.domain.interactor.GetMealDetails;
import com.zireck.projectk.domain.interactor.GetMealList;
import com.zireck.projectk.domain.interactor.GetMealListForDate;
import com.zireck.projectk.domain.interactor.Interactor;
import com.zireck.projectk.domain.repository.MealRepository;
import com.zireck.projectk.presentation.dagger.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zireck on 25/08/2015.
 */
@Module
public class MealModule {

    private long mMealId = -1;
    private Meal mMeal;

    public MealModule() {

    }

    public MealModule(long mealId) {
        mMealId = mealId;
    }

    public MealModule(Meal meal) {
        mMeal = meal;
    }

    @Provides @PerActivity @Named("mealList")
    Interactor provideGetMealListInteractor(GetMealList getMealList) {
        return getMealList;
    }

    @Provides @PerActivity @Named("mealListForDate")
    GetMealListForDate provideGetMealListForDateInteractor(MealRepository mealRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    //Interactor provideGetMealListForDateInteractor(GetMealListForDate getMealListForDate) {
        return new GetMealListForDate(mealRepository, threadExecutor, postExecutionThread);
        //return getMealListForDate;
    }

    @Provides @PerActivity @Named("mealDetails")
    Interactor provideGetMealDetailsInteractor(MealRepository mealRepository,
                        ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetMealDetails(mMeal, mealRepository, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity @Named("addMeal")
    AddMeal provideAddMealInteractor(MealRepository mealRepository,
                        ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new AddMeal(mealRepository, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity @Named("editMeal")
    Interactor provideEditMealInteractor(MealRepository mealRepository,
                        ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new EditMeal(mealRepository, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity @Named("deleteMeal")
    Interactor provideDeleteMealInteractor(MealRepository mealRepository,
                        ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new DeleteMeal(mMeal, mealRepository, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity @Named("deleteAllMeals")
    Interactor provideDeleteAllMealsInteractor(MealRepository mealRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new DeleteAllMeals(mealRepository, threadExecutor, postExecutionThread);
    }
}
