package com.zireck.calories.data.entity.mapper;

import com.zireck.calories.data.entity.FoodEntity;
import com.zireck.calories.domain.Food;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link FoodEntity}, in the data layer, to {@link Food} in the
 * domain layer.
 */
public class FoodEntityDataMapper {

    @Inject
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

    /**
     * Transforms a {@link Food} into a {@link FoodEntity}.
     *
     * @param food {@link Food} to be transformed.
     * @return {@link FoodEntity} if valid {@link Food}, otherwise null.
     */
    public FoodEntity transformInverse(Food food) {
        FoodEntity foodEntity = null;
        if (food != null) {
            foodEntity = new FoodEntity(food.getId());
            foodEntity.setName(food.getName());
            foodEntity.setBrand(food.getBrand());
            foodEntity.setIsDrink(food.isDrink());
            foodEntity.setCalories(food.getCalories());
            foodEntity.setFats(food.getFats());
            foodEntity.setCarbohydrates(food.getCarbohydrates());
            foodEntity.setProteins(food.getProteins());
            foodEntity.setPicture(food.getPicture());
        }

        return foodEntity;
    }

    /**
     * Transforms a Collection of {@link Food} into a List of {@link FoodEntity}.
     *
     * @param foodCollection {@link Food} Collection to be transformed.
     * @return List of {@link FoodEntity}.
     */
    public List<FoodEntity> transformInverse(Collection<Food> foodCollection) {
        List<FoodEntity> foodEntities;

        if (foodCollection != null && !foodCollection.isEmpty()) {
            foodEntities = new ArrayList<FoodEntity>();
            FoodEntity foodEntity;
            for (Food food : foodCollection) {
                foodEntity = transformInverse(food);
                if (foodEntity != null) {
                    foodEntities.add(foodEntity);
                }
            }
        } else {
            foodEntities = Collections.emptyList();
        }

        return foodEntities;
    }
}
