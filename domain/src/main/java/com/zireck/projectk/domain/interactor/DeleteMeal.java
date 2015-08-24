package com.zireck.projectk.domain.interactor;

import com.zireck.projectk.domain.Meal;
import com.zireck.projectk.domain.executor.PostExecutionThread;
import com.zireck.projectk.domain.executor.ThreadExecutor;
import com.zireck.projectk.domain.repository.MealRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Zireck on 24/08/2015.
 */
public class DeleteMeal extends Interactor {

    private final Meal mMeal;
    private MealRepository mMealRepository;

    @Inject
    public DeleteMeal(final Meal meal, MealRepository mealRepository, ThreadExecutor threadExecutor,
                      PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mMeal = meal;
        mMealRepository = mealRepository;
    }

    @Override
    protected Observable buildInteractorObservable() {
        return mMealRepository.deleteMeal(mMeal);
    }
}
