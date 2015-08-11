package com.zireck.projectk.presentation.mapper;

import com.zireck.projectk.domain.Meal;
import com.zireck.projectk.presentation.model.MealModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Mapper class used to transform {@link Meal}, in the domain layer, to {@link MealModel} in the
 * presentation layer.
 */
public class MealModelDataMapper {

    public MealModelDataMapper() {

    }

    /**
     * Transforms a {@link Meal} into a {@link MealModel}.
     *
     * @param meal {@link Meal} object to be transformed.
     * @return {@link MealModel}.
     */
    public MealModel transform(Meal meal) {
        if (meal == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }

        MealModel mealModel = new MealModel(meal.getId());
        mealModel.setDate(meal.getDate());
        mealModel.setMealtime(meal.getMealtime());
        mealModel.setGrams(meal.getGrams());
        mealModel.setCalories(meal.getCalories());
        mealModel.setFats(meal.getFats());
        mealModel.setCarbohydrates(meal.getCarbohydrates());
        mealModel.setProteins(meal.getProteins());
        mealModel.setFoodId(meal.getFoodId());

        // TODO:
        //mealModel.setFood(meal.getFood());

        return mealModel;
    }

    /**
     * Transforms a Collection of {@link Meal} into a Collection of {@link MealModel}.
     *
     * @param mealsCollection {@link Meal} objects to be transformed.
     * @return List of {@link MealModel}.
     */
    public Collection<MealModel> transform(Collection<Meal> mealsCollection) {
        Collection<MealModel> mealModelsCollection;

        if (mealsCollection != null && !mealsCollection.isEmpty()) {
            mealModelsCollection = new ArrayList<MealModel>();
            for (Meal meal : mealsCollection) {
                mealModelsCollection.add(transform(meal));
            }
        } else {
            mealModelsCollection = Collections.emptyList();
        }

        return mealModelsCollection;
    }
}