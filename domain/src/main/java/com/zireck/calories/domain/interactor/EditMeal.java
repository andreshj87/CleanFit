package com.zireck.calories.domain.interactor;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Zireck on 24/08/2015.
 */
public class EditMeal extends Interactor {

    private com.zireck.calories.domain.Meal mMeal;
    private com.zireck.calories.domain.repository.MealRepository mMealRepository;

    @Inject
    public EditMeal(com.zireck.calories.domain.repository.MealRepository mealRepository, com.zireck.calories.domain.executor.ThreadExecutor threadExecutor,
                    com.zireck.calories.domain.executor.PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mMealRepository = mealRepository;
    }

    public void setMeal(com.zireck.calories.domain.Meal meal) {
        if (meal == null) {
            throw new IllegalArgumentException("Meal object cannot be null.");
        }

        mMeal = meal;
    }

    @Override
    protected Observable buildInteractorObservable() {
        return mMealRepository.editMeal(mMeal);
    }
}
