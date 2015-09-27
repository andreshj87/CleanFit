package com.zireck.calories.domain.interactor;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Zireck on 14/08/2015.
 */
public class GetFoodList extends Interactor {

    private final com.zireck.calories.domain.repository.FoodRepository mFoodRepository;

    @Inject
    public GetFoodList(com.zireck.calories.domain.repository.FoodRepository foodRepository, com.zireck.calories.domain.executor.ThreadExecutor threadExecutor,
                          com.zireck.calories.domain.executor.PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mFoodRepository = foodRepository;
    }

    @Override
    protected Observable buildInteractorObservable() {
        return mFoodRepository.foods();
    }
}
