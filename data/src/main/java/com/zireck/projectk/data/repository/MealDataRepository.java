package com.zireck.projectk.data.repository;

import com.zireck.projectk.data.entity.MealEntity;
import com.zireck.projectk.data.entity.mapper.MealEntityDataMapper;
import com.zireck.projectk.data.repository.datasource.MealDataStore;
import com.zireck.projectk.domain.Meal;
import com.zireck.projectk.domain.repository.MealRepository;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * {@link MealRepository} for retrieving meal data.
 */
public class MealDataRepository implements MealRepository {

    private MealDataStore mMealDataStore;
    private MealEntityDataMapper mMealEntityDataMapper;

    @Inject
    public MealDataRepository(MealDataStore mealDataStore,
                              MealEntityDataMapper mealEntityDataMapper) {
        mMealDataStore = mealDataStore;
        mMealEntityDataMapper = mealEntityDataMapper;
    }

    @Override
    public Observable<Meal> meal(long mealId) {
        return mMealDataStore.mealEntityDetails(mealId).map(new Func1<MealEntity, Meal>() {
            @Override
            public Meal call(MealEntity mealEntity) {
                return mMealEntityDataMapper.transform(mealEntity);
            }
        });
    }

    @Override
    public Observable<List<Meal>> meals() {
        return mMealDataStore.mealEntityList().map(new Func1<List<MealEntity>, List<Meal>>() {
            @Override
            public List<Meal> call(List<MealEntity> mealEntities) {
                return mMealEntityDataMapper.transform(mealEntities);
            }
        });
    }

    @Override
    public Observable<List<Meal>> meals(Date date) {
        return mMealDataStore.mealEntityList(date).map(new Func1<List<MealEntity>, List<Meal>>() {
            @Override
            public List<Meal> call(List<MealEntity> mealEntities) {
                return mMealEntityDataMapper.transform(mealEntities);
            }
        });
    }

    @Override
    public Observable<Void> addMeal(Meal meal) {
        return mMealDataStore.addMeal(mMealEntityDataMapper.transformInverse(meal));
    }

    @Override
    public Observable<Void> editMeal(Meal meal) {
        return mMealDataStore.editMeal(mMealEntityDataMapper.transformInverse(meal));
    }

    @Override
    public Observable<Void> deleteMeal(Meal meal) {
        return mMealDataStore.deleteMeal(mMealEntityDataMapper.transformInverse(meal));
    }
}
