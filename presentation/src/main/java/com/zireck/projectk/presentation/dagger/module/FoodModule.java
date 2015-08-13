package com.zireck.projectk.presentation.dagger.module;

import com.zireck.projectk.domain.executor.PostExecutionThread;
import com.zireck.projectk.domain.executor.ThreadExecutor;
import com.zireck.projectk.domain.interactor.GetFoodDetails;
import com.zireck.projectk.domain.interactor.GetFoodList;
import com.zireck.projectk.domain.interactor.Interactor;
import com.zireck.projectk.domain.repository.FoodRepository;
import com.zireck.projectk.presentation.dagger.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides food related collaborators.
 */
@Module
public class FoodModule {

    private int mFoodId = -1;

    public FoodModule() {

    }

    public FoodModule(int foodId) {
        mFoodId = foodId;
    }

    @Provides @PerActivity @Named("foodList")
    Interactor provideGetFoodListInteractor(GetFoodList getFoodList) {
        return getFoodList;
    }

    @Provides @PerActivity @Named("foodDetails")
    Interactor provideGetFoodDetailsInteractor(FoodRepository foodRepository,
                        ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetFoodDetails(mFoodId, foodRepository, threadExecutor, postExecutionThread);
    }
}
