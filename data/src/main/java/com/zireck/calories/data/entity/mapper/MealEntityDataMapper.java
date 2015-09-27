package com.zireck.calories.data.entity.mapper;

import com.zireck.calories.data.entity.MealEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link MealEntity}, in the data layer, to {@link com.zireck.calories.domain.Meal} in the
 * domain layer.
 */
public class MealEntityDataMapper {

    private FoodEntityDataMapper mFoodEntityDataMapper;

    @Inject
    public MealEntityDataMapper(FoodEntityDataMapper foodEntityDataMapper) {
        mFoodEntityDataMapper = foodEntityDataMapper;
    }

    /**
     * Transforms a {@link MealEntity} into a {@link com.zireck.calories.domain.Meal}.
     *
     * @param mealEntity {@link MealEntity} to be transformed.
     * @return {@link com.zireck.calories.domain.Meal} if valid {@link MealEntity}, otherwise null.
     */
    public com.zireck.calories.domain.Meal transform(MealEntity mealEntity) {
        com.zireck.calories.domain.Meal meal = null;
        if (mealEntity != null) {
            meal = new com.zireck.calories.domain.Meal(mealEntity.getId());
            meal.setDate(mealEntity.getDate());
            meal.setMealtime(mealEntity.getMealtime());
            meal.setGrams(mealEntity.getGrams());
            meal.setCalories(mealEntity.getCalories());
            meal.setFats(mealEntity.getFats());
            meal.setCarbohydrates(mealEntity.getCarbohydrates());
            meal.setProteins(mealEntity.getProteins());
            meal.setFoodId(mealEntity.getFoodId());
            meal.setFoodName(mealEntity.getFoodName());
            meal.setFood(mFoodEntityDataMapper.transform(mealEntity.getFoodEntity()));
        }

        return meal;
    }

    /**
     * Transforms a Collection of {@link MealEntity} into a List of {@link com.zireck.calories.domain.Meal}.
     *
     * @param mealEntityCollection {@link MealEntity} Collection to be transformed.
     * @return List of {@link com.zireck.calories.domain.Meal}.
     */
    public List<com.zireck.calories.domain.Meal> transform(Collection<MealEntity> mealEntityCollection) {
        List<com.zireck.calories.domain.Meal> mealsList;

        if (mealEntityCollection != null && !mealEntityCollection.isEmpty()) {
            mealsList = new ArrayList<com.zireck.calories.domain.Meal>();
            com.zireck.calories.domain.Meal meal;
            for (MealEntity mealEntity : mealEntityCollection) {
                meal = transform(mealEntity);
                if (meal != null) {
                    mealsList.add(meal);
                }
            }
        } else {
            mealsList = Collections.emptyList();
        }

        return mealsList;
    }

    /**
     * Transforms a {@link com.zireck.calories.domain.Meal} into a {@link MealEntity}.
     *
     * @param meal {@link com.zireck.calories.domain.Meal} to be transformed.
     * @return {@link MealEntity} if valid {@link com.zireck.calories.domain.Meal}, otherwise null.
     */
    public MealEntity transformInverse(com.zireck.calories.domain.Meal meal) {
        MealEntity mealEntity = null;
        if (meal != null) {
            mealEntity = new MealEntity(meal.getId());
            mealEntity.setDate(meal.getDate());
            mealEntity.setMealtime(meal.getMealtime());
            mealEntity.setGrams(meal.getGrams());
            mealEntity.setCalories(meal.getCalories());
            mealEntity.setFats(meal.getFats());
            mealEntity.setCarbohydrates(meal.getCarbohydrates());
            mealEntity.setProteins(meal.getProteins());
            mealEntity.setFoodId(meal.getFoodId());
            mealEntity.setFoodName(meal.getFoodName());
            mealEntity.setFoodEntity(mFoodEntityDataMapper.transformInverse(meal.getFood()));
        }

        return mealEntity;
    }

    /**
     * Transforms a Collection of {@link com.zireck.calories.domain.Meal} into a List of {@link MealEntity}.
     *
     * @param mealCollection {@link com.zireck.calories.domain.Meal} Collection to be transformed.
     * @return List of {@link MealEntity}.
     */
    public List<MealEntity> transformInverse(Collection<com.zireck.calories.domain.Meal> mealCollection) {
        List<MealEntity> mealEntityList;

        if (mealCollection != null && !mealCollection.isEmpty()) {
            mealEntityList = new ArrayList<MealEntity>();
            MealEntity mealEntity;
            for (com.zireck.calories.domain.Meal meal : mealCollection) {
                mealEntity = transformInverse(meal);
                if (mealEntity != null) {
                    mealEntityList.add(mealEntity);
                }
            }
        } else {
            mealEntityList = Collections.emptyList();
        }

        return mealEntityList;
    }
}