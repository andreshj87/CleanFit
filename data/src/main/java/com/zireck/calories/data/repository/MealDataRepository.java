package com.zireck.calories.data.repository;

import com.zireck.calories.data.entity.MealEntity;
import com.zireck.calories.data.entity.mapper.MealEntityDataMapper;
import com.zireck.calories.data.repository.datasource.MealDataStore;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * {@link com.zireck.calories.domain.repository.MealRepository} for retrieving meal data.
 */
public class MealDataRepository implements com.zireck.calories.domain.repository.MealRepository {

    private MealDataStore mMealDataStore;
    private MealEntityDataMapper mMealEntityDataMapper;

    @Inject
    public MealDataRepository(MealDataStore mealDataStore,
                              MealEntityDataMapper mealEntityDataMapper) {
        mMealDataStore = mealDataStore;
        mMealEntityDataMapper = mealEntityDataMapper;
    }

    @Override
    public Observable<com.zireck.calories.domain.Meal> meal(long mealId) {
        return mMealDataStore.mealEntityDetails(mealId).map(new Func1<MealEntity, com.zireck.calories.domain.Meal>() {
            @Override
            public com.zireck.calories.domain.Meal call(MealEntity mealEntity) {
                return mMealEntityDataMapper.transform(mealEntity);
            }
        });
    }

    @Override
    public Observable<List<com.zireck.calories.domain.Meal>> meals() {
        return mMealDataStore.mealEntityList().map(new Func1<List<MealEntity>, List<com.zireck.calories.domain.Meal>>() {
            @Override
            public List<com.zireck.calories.domain.Meal> call(List<MealEntity> mealEntities) {
                return mMealEntityDataMapper.transform(mealEntities);
            }
        });
    }

    @Override
    public Observable<List<com.zireck.calories.domain.Meal>> meals(Date firstDate, Date lastDate) {
        return mMealDataStore.mealEntityList(firstDate, lastDate).map(new Func1<List<MealEntity>, List<com.zireck.calories.domain.Meal>>() {
            @Override
            public List<com.zireck.calories.domain.Meal> call(List<MealEntity> mealEntities) {
                return mMealEntityDataMapper.transform(mealEntities);
            }
        });
    }

    @Override
    public Observable<Void> addMeal(com.zireck.calories.domain.Meal meal) {
        return mMealDataStore.addMeal(mMealEntityDataMapper.transformInverse(meal));
    }

    @Override
    public Observable<Void> editMeal(com.zireck.calories.domain.Meal meal) {
        return mMealDataStore.editMeal(mMealEntityDataMapper.transformInverse(meal));
    }

    @Override
    public Observable<Void> deleteMeal(com.zireck.calories.domain.Meal meal) {
        return mMealDataStore.deleteMeal(mMealEntityDataMapper.transformInverse(meal));
    }

    @Deprecated
    @Override
    public Observable<Void> deleteAllMeals() {
        return mMealDataStore.deleteAllMeals();
    }
}
