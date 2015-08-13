package com.zireck.projectk.domain.interactor;

import com.zireck.projectk.domain.Food;
import com.zireck.projectk.domain.executor.PostExecutionThread;
import com.zireck.projectk.domain.executor.ThreadExecutor;
import com.zireck.projectk.domain.repository.FoodRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * This class is an implementation of {@link Interactor} that represents an use case for retrieving
 * data related to a specific {@link Food}.
 */
public class GetFoodDetails extends Interactor {

    private final int mFoodId;
    private final FoodRepository mFoodRepository;

    @Inject
    public GetFoodDetails(int foodId, FoodRepository foodRepository, ThreadExecutor threadExecutor,
                          PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mFoodId = foodId;
        mFoodRepository = foodRepository;
    }

    @Override
    protected Observable buildInteractorObservable() {
        return mFoodRepository.food(mFoodId);
    }
}
