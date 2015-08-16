package com.zireck.projectk.domain.interactor;

import com.zireck.projectk.domain.Food;
import com.zireck.projectk.domain.executor.PostExecutionThread;
import com.zireck.projectk.domain.executor.ThreadExecutor;
import com.zireck.projectk.domain.repository.FoodRepository;

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
                          PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mFoodRepository = foodRepository;
    }

    @Override
    protected Observable buildInteractorObservable() {
        return mFoodRepository.allFood();
    }
}
