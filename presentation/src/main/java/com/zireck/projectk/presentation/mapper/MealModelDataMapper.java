package com.zireck.projectk.presentation.mapper;

import com.zireck.projectk.domain.Meal;
import com.zireck.projectk.presentation.model.MealModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link Meal}, in the domain layer, to {@link MealModel} in the
 * presentation layer.
 */
public class MealModelDataMapper {

    private FoodModelDataMapper mFoodModelDataMapper;

    @Inject
    public MealModelDataMapper(FoodModelDataMapper foodModelDataMapper) {
        mFoodModelDataMapper = foodModelDataMapper;
    }

    /**
     * Transforms a {@link Meal} into a {@link MealModel}.
     *
     * @param meal {@link Meal} object to be transformed.
     * @return {@link MealModel}.
     */
    public MealModel transform(Meal meal) {
        if (meal == null) {
            argumentException();
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
        mealModel.setFoodModel(mFoodModelDataMapper.transform(meal.getFood()));

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

    /**
     * Transforms a {@link MealModel} into a {@link Meal}.
     *
     * @param mealModel {@link MealModel} object to be transformed.
     * @return {@link Meal}.
     */
    public Meal transformInverse(MealModel mealModel) {
        if (mealModel == null) {
            argumentException();
        }

        Meal meal = new Meal(mealModel.getId());
        meal.setDate(mealModel.getDate());
        meal.setMealtime(mealModel.getMealtime());
        meal.setGrams(mealModel.getGrams());
        meal.setCalories(mealModel.getCalories());
        meal.setFats(mealModel.getFats());
        meal.setCarbohydrates(mealModel.getCarbohydrates());
        meal.setProteins(mealModel.getProteins());
        meal.setFoodId(mealModel.getFoodId());
        meal.setFood(mFoodModelDataMapper.transformInverse(mealModel.getFoodModel()));

        return meal;
    }

    /**
     * Transforms a Collection of {@link MealModel} into a Collection of {@link Meal}.
     *
     * @param mealModelCollection {@link MealModel} objects to be transformed.
     * @return List of {@link Meal}.
     */
    public Collection<Meal> transformInverse(Collection<MealModel> mealModelCollection) {
        Collection<Meal> mealCollection;

        if (mealModelCollection != null && !mealModelCollection.isEmpty()) {
            mealCollection = new ArrayList<Meal>();
            for (MealModel mealModel : mealModelCollection) {
                mealCollection.add(transformInverse(mealModel));
            }
        } else {
            mealCollection = Collections.emptyList();
        }

        return mealCollection;
    }

    private void argumentException() {
        throw new IllegalArgumentException("Cannot transform a null value");
    }
}