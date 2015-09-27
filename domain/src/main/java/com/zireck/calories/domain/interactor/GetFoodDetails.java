package com.zireck.calories.domain.interactor;

import com.zireck.calories.domain.repository.FoodRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * This class is an implementation of {@link Interactor} that represents an use case for retrieving
 * data related to a specific {@link com.zireck.calories.domain.Food}.
 */
public class GetFoodDetails extends Interactor {

    private final long mFoodId;
    private final FoodRepository mFoodRepository;

    @Inject
    public GetFoodDetails(com.zireck.calories.domain.Food food, FoodRepository foodRepository, com.zireck.calories.domain.executor.ThreadExecutor threadExecutor,
                          com.zireck.calories.domain.executor.PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mFoodId = food.getId();
        mFoodRepository = foodRepository;
    }

    @Override
    protected Observable buildInteractorObservable() {
        return mFoodRepository.food(mFoodId);
    }
}
