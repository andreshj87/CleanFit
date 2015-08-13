package com.zireck.projectk.data.entity.mapper;

import com.zireck.projectk.data.entity.MealEntity;
import com.zireck.projectk.domain.Meal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link MealEntity}, in the data layer, to {@link Meal} in the
 * domain layer.
 */
public class MealEntityDataMapper {

    @Inject
    public MealEntityDataMapper() {

    }

    /**
     * Transforms a {@link MealEntity} into a {@link Meal}.
     *
     * @param mealEntity {@link MealEntity} to be transformed.
     * @return {@link Meal} if valid {@link MealEntity}, otherwise null.
     */
    public Meal transform(MealEntity mealEntity) {
        Meal meal = null;
        if (mealEntity != null) {
            meal = new Meal(mealEntity.getId());
            meal.setDate(mealEntity.getDate());
            meal.setMealtime(mealEntity.getMealtime());
            meal.setGrams(mealEntity.getGrams());
            meal.setCalories(mealEntity.getCalories());
            meal.setFats(mealEntity.getFats());
            meal.setCarbohydrates(mealEntity.getCarbohydrates());
            meal.setProteins(mealEntity.getProteins());
            meal.setFoodId(mealEntity.getFoodId());

            // TODO:
            //meal.setFood(mealEntity.getFood());
        }

        return meal;
    }

    /**
     * Transforms a Collection of {@link MealEntity} into a List of {@link Meal}.
     *
     * @param mealEntityCollection {@link MealEntity} Collection to be transformed.
     * @return List of {@link Meal}.
     */
    public List<Meal> transform(Collection<MealEntity> mealEntityCollection) {
        List<Meal> mealsList;

        if (mealEntityCollection != null && !mealEntityCollection.isEmpty()) {
            mealsList = new ArrayList<Meal>();
            Meal meal;
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
}