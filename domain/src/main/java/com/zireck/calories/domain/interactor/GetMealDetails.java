package com.zireck.calories.domain.interactor;

import com.zireck.calories.domain.Meal;
import com.zireck.calories.domain.executor.ThreadExecutor;
import com.zireck.calories.domain.repository.MealRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Zireck on 24/08/2015.
 */
public class GetMealDetails extends Interactor {

    private final long mMealId;
    private MealRepository mMealRepository;

    @Inject
    public GetMealDetails(Meal meal, MealRepository mealRepository, ThreadExecutor threadExecutor,
                          com.zireck.calories.domain.executor.PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mMealId = meal.getId();
        mMealRepository = mealRepository;
    }

    @Override
    protected Observable buildInteractorObservable() {
        return mMealRepository.meal(mMealId);
    }
}
