package com.zireck.projectk.data.repository;

import com.zireck.projectk.data.entity.FoodEntity;
import com.zireck.projectk.data.entity.mapper.FoodEntityDataMapper;
import com.zireck.projectk.data.repository.datasource.FoodDataStore;
import com.zireck.projectk.domain.Food;
import com.zireck.projectk.domain.repository.FoodRepository;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * {@link FoodRepository} for retrieving food data.
 */
public class FoodDataRepository implements FoodRepository {

    private FoodDataStore mFoodDataStore;
    private FoodEntityDataMapper mFoodEntityDataMapper;

    @Override
    public Observable<Food> food(int foodId) {
        return mFoodDataStore.foodEntityDetails(foodId).map(new Func1<FoodEntity, Food>() {
            @Override
            public Food call(FoodEntity foodEntity) {
                return mFoodEntityDataMapper.transform(foodEntity);
            }
        });
    }

    @Override
    public Observable<List<Food>> foods() {
        return mFoodDataStore.foodEntityList().map(new Func1<List<FoodEntity>, List<Food>>() {
            @Override
            public List<Food> call(List<FoodEntity> foodEntities) {
                return mFoodEntityDataMapper.transform(foodEntities);
            }
        });
    }
}
