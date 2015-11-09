package com.zireck.calories.presentation.dagger.module;

import com.zireck.calories.domain.Food;
import com.zireck.calories.domain.executor.PostExecutionThread;
import com.zireck.calories.domain.executor.ThreadExecutor;
import com.zireck.calories.domain.interactor.AddFood;
import com.zireck.calories.domain.interactor.CheckIfFoodCatalogIsEmpty;
import com.zireck.calories.domain.interactor.DeleteFood;
import com.zireck.calories.domain.interactor.EditFood;
import com.zireck.calories.domain.interactor.GetAllFoodList;
import com.zireck.calories.domain.interactor.GetDrinkList;
import com.zireck.calories.domain.interactor.GetFoodDetails;
import com.zireck.calories.domain.interactor.GetFoodList;
import com.zireck.calories.domain.interactor.Interactor;
import com.zireck.calories.domain.repository.FoodRepository;
import com.zireck.calories.presentation.dagger.PerActivity;

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

    public FoodModule(Food food) {
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
        return new GetFoodDetails(mFood, foodRepository, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity @Named("addFood")
    AddFood provideAddFoodInteractor(FoodRepository foodRepository,
                                        ThreadExecutor threadExecutor,
                                        PostExecutionThread postExecutionThread) {
        return new AddFood(foodRepository, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity @Named("editFood")
    EditFood provideEditFoodInteractor(FoodRepository foodRepository,
                                         ThreadExecutor threadExecutor,
                                         PostExecutionThread postExecutionThread) {
        return new EditFood(foodRepository, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity @Named("deleteFood")
    Interactor provideDeleteFoodInteractor(FoodRepository foodRepository, ThreadExecutor threadExecutor,
                                       PostExecutionThread postExecutionThread) {
        return new DeleteFood(mFood, foodRepository, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity @Named("checkIfFoodCatalogIsEmpty")
    Interactor provideCheckIfFoodCatalogIsEmptyInteractor(FoodRepository foodRepository,
                                                          ThreadExecutor threadExecutor,
                                                          PostExecutionThread postExecutionThread) {
        return new CheckIfFoodCatalogIsEmpty(foodRepository, threadExecutor, postExecutionThread);
    }
}
