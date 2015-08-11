package com.zireck.projectk.presentation.mapper;

import com.zireck.projectk.domain.Food;
import com.zireck.projectk.presentation.model.FoodModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Mapper class used to transform {@link Food}, in the domain layer, to {@link FoodModel} in the
 * presentation layer.
 */
public class FoodModelDataMapper {

    public FoodModelDataMapper() {

    }

    /**
     * Transforms a {@link Food} into a {@link FoodModel}.
     *
     * @param food {@link Food} object to be transformed.
     * @return {@link FoodModel}.
     */
    public FoodModel transform(Food food) {
        if (food == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }

        FoodModel foodModel = new FoodModel(food.getId());
        foodModel.setName(food.getName());
        foodModel.setBrand(food.getBrand());
        foodModel.setIsDrink(food.isDrink());
        foodModel.setCalories(food.getCalories());
        foodModel.setFats(food.getFats());
        foodModel.setCarbohydrates(food.getCarbohydrates());
        foodModel.setProteins(food.getProteins());
        foodModel.setPicture(food.getPicture());

        return foodModel;
    }

    /**
     * Transforms a Collection of {@link Food} into a Collection of {@link FoodModel}.
     *
     * @param foodsCollection {@link Food} objects to be transformed.
     * @return List of {@link FoodModel}.
     */
    public Collection<FoodModel> transform(Collection<Food> foodsCollection) {
        Collection<FoodModel> foodModelsCollection;

        if (foodsCollection != null && !foodsCollection.isEmpty()) {
            foodModelsCollection = new ArrayList<FoodModel>();
            for (Food food : foodsCollection) {
                foodModelsCollection.add(transform(food));
            }
        } else {
            foodModelsCollection = Collections.emptyList();
        }

        return foodModelsCollection;
    }
}
