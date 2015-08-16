package com.zireck.projectk.domain.interactor;

import com.zireck.projectk.domain.executor.PostExecutionThread;
import com.zireck.projectk.domain.executor.ThreadExecutor;
import com.zireck.projectk.domain.repository.FoodRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Zireck on 16/08/2015.
 */
public class DeleteFood extends Interactor {

    private final long mFoodId;
    private final FoodRepository mFoodRepository;

    @Inject
    public DeleteFood(long foodId, FoodRepository foodRepository, ThreadExecutor threadExecutor,
                          PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mFoodId = foodId;
        mFoodRepository = foodRepository;
    }

    @Override
    protected Observable buildInteractorObservable() {
        return mFoodRepository.deleteFood(mFoodId);
    }
}
