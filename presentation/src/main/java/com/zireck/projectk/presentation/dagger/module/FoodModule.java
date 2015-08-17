package com.zireck.projectk.presentation.dagger.module;

import com.zireck.projectk.domain.Food;
import com.zireck.projectk.domain.executor.PostExecutionThread;
import com.zireck.projectk.domain.executor.ThreadExecutor;
import com.zireck.projectk.domain.interactor.AddFood;
import com.zireck.projectk.domain.interactor.DeleteFood;
import com.zireck.projectk.domain.interactor.GetAllFoodList;
import com.zireck.projectk.domain.interactor.GetDrinkList;
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

    private Food mFood;
    private long mFoodId = -1;

    public FoodModule() {

    }

    public FoodModule(long foodId) {
        mFoodId = foodId;
    }

    public FoodModule(final Food food) {
        mFood = food;
    }

    @Provides @PerActivity @Named("allFoodList")
    Interactor provideGetAllFoodListInteractor(GetAllFoodList getAllFoodList) {
        return getAllFoodList;
    }

    @Provides @PerActivity @Named("foodList")
    Interactor provideGetFoodListInteractor(GetFoodList getFoodList) {
        return getFoodList;
    }

    @Provides @PerActivity @Named("drinkList")
    Interactor provideGetDrinkListInteractor(GetDrinkList getDrinkList) {
        return getDrinkList;
    }

    @Provides @PerActivity @Named("foodDetails")
    Interactor provideGetFoodDetailsInteractor(FoodRepository foodRepository,
                        ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetFoodDetails(mFoodId, foodRepository, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity @Named("addFood")
    Interactor provideAddFoodInteractor(FoodRepository foodRepository,
                                        ThreadExecutor threadExecutor,
                                        PostExecutionThread postExecutionThread) {
        return new AddFood(mFood, foodRepository, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity @Named("deleteFood")
    Interactor provideDeleteInteractor(FoodRepository foodRepository, ThreadExecutor threadExecutor,
                                       PostExecutionThread postExecutionThread) {
        return new DeleteFood(mFood, foodRepository, threadExecutor, postExecutionThread);
    }
}
