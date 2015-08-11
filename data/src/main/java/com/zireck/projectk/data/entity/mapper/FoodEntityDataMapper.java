package com.zireck.projectk.data.entity.mapper;

import com.zireck.projectk.data.entity.FoodEntity;
import com.zireck.projectk.domain.Food;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Mapper class used to transform {@link FoodEntity}, in the data layer, to {@link Food} in the
 * domain layer.
 */
public class FoodEntityDataMapper {

    public FoodEntityDataMapper() {

    }

    /**
     * Transforms a {@link FoodEntity} into a {@link Food}.
     *
     * @param foodEntity {@link FoodEntity} to be transformed.
     * @return {@link Food} if valid {@link FoodEntity}, otherwise null.
     */
    public Food transform(FoodEntity foodEntity) {
        Food food = null;
        if (foodEntity != null) {
            food = new Food(foodEntity.getId());
            food.setName(foodEntity.getName());
            food.setBrand(foodEntity.getBrand());
            food.setIsDrink(foodEntity.getIsDrink());
            food.setCalories(foodEntity.getCalories());
            food.setFats(foodEntity.getFats());
            food.setCarbohydrates(foodEntity.getCarbohydrates());
            food.setProteins(foodEntity.getProteins());
            food.setPicture(foodEntity.getPicture());
        }

        return food;
    }

    /**
     * Transforms a Collection of {@link FoodEntity} into a List of {@link Food}.
     *
     * @param foodEntityCollection {@link FoodEntity} Collection to be transformed.
     * @return List of {@link Food}.
     */
    public List<Food> transform(Collection<FoodEntity> foodEntityCollection) {
        List<Food> foodsList;

        if (foodEntityCollection != null && !foodEntityCollection.isEmpty()) {
            foodsList = new ArrayList<Food>();
            Food food;
            for (FoodEntity foodEntity : foodEntityCollection) {
                food = transform(foodEntity);
                if (food != null) {
                    foodsList.add(food);
                }
            }
        } else {
            foodsList = Collections.emptyList();
        }

        return foodsList;
    }
}
