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
public class GetMealDetails extends Interactor {

    private final long mMealId;
    private MealRepository mMealRepository;

    @Inject
    public GetMealDetails(Meal meal, MealRepository mealRepository, ThreadExecutor threadExecutor,
                          PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mMealId = meal.getId();
        mMealRepository = mealRepository;
    }

    @Override
    protected Observable buildInteractorObservable() {
        return mMealRepository.meal(mMealId);
    }
}
