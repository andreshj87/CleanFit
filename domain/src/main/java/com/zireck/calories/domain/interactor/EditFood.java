package com.zireck.calories.domain.interactor;

import com.zireck.calories.domain.Food;
import com.zireck.calories.domain.executor.ThreadExecutor;
import com.zireck.calories.domain.repository.FoodRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Zireck on 17/08/2015.
 */
public class EditFood extends Interactor {

    private Food mFood;
    private final FoodRepository mFoodRepository;

    @Inject
    public EditFood(FoodRepository foodRepository, ThreadExecutor threadExecutor,
                      com.zireck.calories.domain.executor.PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mFoodRepository = foodRepository;
    }

    public void setFood(Food food) {
        if (food == null) {
            throw new IllegalArgumentException("Food object cannot be null.");
        }

        mFood = food;
    }

    @Override
    protected Observable buildInteractorObservable() {
        return mFoodRepository.editFood(mFood);
    }
}
