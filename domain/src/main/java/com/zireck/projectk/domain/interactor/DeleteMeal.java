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

    private Meal mMeal;
    private MealRepository mMealRepository;

    @Inject
    public DeleteMeal(MealRepository mealRepository, ThreadExecutor threadExecutor,
                      PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mMealRepository = mealRepository;
    }

    public void setMeal(Meal meal) {
        if (meal == null) {
            throw new IllegalArgumentException("Meal object cannot be null.");
        }

        mMeal = meal;
    }

    @Override
    protected Observable buildInteractorObservable() {
        return mMealRepository.deleteMeal(mMeal);
    }
}
