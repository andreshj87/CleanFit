package com.zireck.calories.domain.interactor;

import com.zireck.calories.domain.repository.FoodRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Zireck on 16/08/2015.
 */
public class DeleteFood extends Interactor {

    private final com.zireck.calories.domain.Food mFood;
    private final FoodRepository mFoodRepository;

    @Inject
    public DeleteFood(final com.zireck.calories.domain.Food food, FoodRepository foodRepository, com.zireck.calories.domain.executor.ThreadExecutor threadExecutor,
                          com.zireck.calories.domain.executor.PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mFood = food;
        mFoodRepository = foodRepository;
    }

    @Override
    protected Observable buildInteractorObservable() {
        return mFoodRepository.deleteFood(mFood);
    }
}
