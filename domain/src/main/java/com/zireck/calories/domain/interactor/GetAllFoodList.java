package com.zireck.calories.domain.interactor;

import com.zireck.calories.domain.Food;
import com.zireck.calories.domain.executor.ThreadExecutor;
import com.zireck.calories.domain.repository.FoodRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * This class is an implementation of {@link Interactor} that represents an use case for retrieving
 * a collection of {@link Food}.
 */
public class GetAllFoodList extends Interactor {

    private final FoodRepository mFoodRepository;

    @Inject
    public GetAllFoodList(FoodRepository foodRepository, ThreadExecutor threadExecutor,
                          com.zireck.calories.domain.executor.PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mFoodRepository = foodRepository;
    }

    @Override
    protected Observable buildInteractorObservable() {
        return mFoodRepository.allFood();
    }
}
