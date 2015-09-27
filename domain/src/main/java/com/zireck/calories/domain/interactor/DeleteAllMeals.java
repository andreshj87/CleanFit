package com.zireck.calories.domain.interactor;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Zireck on 26/08/2015.
 */
public class DeleteAllMeals extends Interactor {

    private com.zireck.calories.domain.repository.MealRepository mMealRepository;

    @Inject
    public DeleteAllMeals(com.zireck.calories.domain.repository.MealRepository mealRepository, com.zireck.calories.domain.executor.ThreadExecutor threadExecutor, com.zireck.calories.domain.executor.PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mMealRepository = mealRepository;
    }

    @Override
    protected Observable buildInteractorObservable() {
        return mMealRepository.deleteAllMeals();
    }
}
