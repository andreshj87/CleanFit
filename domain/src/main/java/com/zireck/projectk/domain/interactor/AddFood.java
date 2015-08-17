package com.zireck.projectk.domain.interactor;

import com.zireck.projectk.domain.Food;
import com.zireck.projectk.domain.executor.PostExecutionThread;
import com.zireck.projectk.domain.executor.ThreadExecutor;
import com.zireck.projectk.domain.repository.FoodRepository;

import rx.Observable;

/**
 * Created by Zireck on 17/08/2015.
 */
public class AddFood extends Interactor {

    private final Food mFood;
    private FoodRepository mFoodRepository;

    public AddFood(final Food food, FoodRepository foodRepository, ThreadExecutor threadExecutor,
                      PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mFood = food;
        mFoodRepository = foodRepository;
    }

    @Override
    protected Observable buildInteractorObservable() {
        return mFoodRepository.addFood(mFood);
    }
}
