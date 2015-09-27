package com.zireck.calories.data.repository;

import com.zireck.calories.data.entity.FoodEntity;
import com.zireck.calories.data.entity.mapper.FoodEntityDataMapper;
import com.zireck.calories.data.repository.datasource.FoodDataStore;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * {@link com.zireck.calories.domain.repository.FoodRepository} for retrieving food data.
 */
public class FoodDataRepository implements com.zireck.calories.domain.repository.FoodRepository {

    private FoodDataStore mFoodDataStore;
    private FoodEntityDataMapper mFoodEntityDataMapper;

    @Inject
    public FoodDataRepository(FoodDataStore foodDataStore,
                              FoodEntityDataMapper foodEntityDataMapper) {
        mFoodDataStore = foodDataStore;
        mFoodEntityDataMapper = foodEntityDataMapper;
    }

    @Override
    public Observable<com.zireck.calories.domain.Food> food(long foodId) {
        return mFoodDataStore.foodEntityDetails(foodId).map(new Func1<FoodEntity, com.zireck.calories.domain.Food>() {
            @Override
            public com.zireck.calories.domain.Food call(FoodEntity foodEntity) {
                return mFoodEntityDataMapper.transform(foodEntity);
            }
        });
    }

    @Override
    public Observable<List<com.zireck.calories.domain.Food>> allFood() {
        return mFoodDataStore.foodEntityList().map(new Func1<List<FoodEntity>, List<com.zireck.calories.domain.Food>>() {
            @Override
            public List<com.zireck.calories.domain.Food> call(List<FoodEntity> foodEntities) {
                return mFoodEntityDataMapper.transform(foodEntities);
            }
        });
    }

    @Override
    public Observable<List<com.zireck.calories.domain.Food>> foods() {
        return mFoodDataStore.foodList().map(new Func1<List<FoodEntity>, List<com.zireck.calories.domain.Food>>() {
            @Override
            public List<com.zireck.calories.domain.Food> call(List<FoodEntity> foodEntities) {
                return mFoodEntityDataMapper.transform(foodEntities);
            }
        });
    }

    @Override
    public Observable<List<com.zireck.calories.domain.Food>> drinks() {
        return mFoodDataStore.drinkList().map(new Func1<List<FoodEntity>, List<com.zireck.calories.domain.Food>>() {
            @Override
            public List<com.zireck.calories.domain.Food> call(List<FoodEntity> foodEntities) {
                return mFoodEntityDataMapper.transform(foodEntities);
            }
        });
    }

    @Override
    public Observable<Void> addFood(com.zireck.calories.domain.Food food) {
        return mFoodDataStore.addFood(mFoodEntityDataMapper.transformInverse(food));
    }

    @Override
    public Observable<Void> editFood(com.zireck.calories.domain.Food food) {
        return mFoodDataStore.editFood(mFoodEntityDataMapper.transformInverse(food));
    }

    @Override
    public Observable<Void> deleteFood(final com.zireck.calories.domain.Food food) {
        return mFoodDataStore.deleteFood(mFoodEntityDataMapper.transformInverse(food));
    }
}
