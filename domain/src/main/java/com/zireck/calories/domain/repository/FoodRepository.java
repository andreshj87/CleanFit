package com.zireck.calories.domain.repository;

import com.zireck.calories.domain.Food;

import java.util.List;

import rx.Observable;

/**
 * Interface that represents a Repository for getting {@link Food} related data.
 */
public interface FoodRepository {

    /**
     * Get an {@link Observable} which will emit a {@link Food}.
     *
     * @param foodId The food id used to retrieve food data.
     */
    Observable<Food> food(final long foodId);

    /**
     * Get an {@link Observable} which will emit a List of all {@link Food}.
     */
    Observable<List<Food>> allFood();

    /**
     * Get an {@link Observable} which will emit a List of {@link Food}.
     */
    Observable<List<Food>> foods();

    /**
     * Get an {@link Observable} which will emit a List of Drinks.
     */
    Observable<List<Food>> drinks();

    /**
     * Get an {@link Observable} which will notify the addition of a {@link Food} object.
     */
    Observable<Void> addFood(final Food food);

    /**
     * Get an {@link Observable} which will notify the edition of a {@link Food} object.
     */
    Observable<Void> editFood(final Food food);

    /**
     * Get an {@link Observable} which will notify the deletion of a {@link Food} object.
     * @param food
     */
    Observable<Void> deleteFood(final Food food);

    /**
     * Get an {@link Observable} which will check if the food catalog is empty.
     * @return
     */
    Observable<Boolean> isCatalogEmpty();
}
