package com.zireck.calories.domain.repository;

import java.util.List;
import rx.Observable;

/**
 * Interface that represents a Repository for getting {@link com.zireck.calories.domain.Food} related data.
 */
public interface FoodRepository {

    /**
     * Get an {@link Observable} which will emit a {@link com.zireck.calories.domain.Food}.
     *
     * @param foodId The food id used to retrieve food data.
     */
    Observable<com.zireck.calories.domain.Food> food(final long foodId);

    /**
     * Get an {@link Observable} which will emit a List of all {@link com.zireck.calories.domain.Food}.
     */
    Observable<List<com.zireck.calories.domain.Food>> allFood();

    /**
     * Get an {@link Observable} which will emit a List of {@link com.zireck.calories.domain.Food}.
     */
    Observable<List<com.zireck.calories.domain.Food>> foods();

    /**
     * Get an {@link Observable} which will emit a List of Drinks.
     */
    Observable<List<com.zireck.calories.domain.Food>> drinks();

    /**
     * Get an {@link Observable} which will notify the addition of a {@link com.zireck.calories.domain.Food} object.
     */
    Observable<Void> addFood(final com.zireck.calories.domain.Food food);

    /**
     * Get an {@link Observable} which will notify the edition of a {@link com.zireck.calories.domain.Food} object.
     */
    Observable<Void> editFood(final com.zireck.calories.domain.Food food);

    /**
     * Get an {@link Observable} which will notify the deletion of a {@link com.zireck.calories.domain.Food} object.
     * @param food
     */
    Observable<Void> deleteFood(final com.zireck.calories.domain.Food food);
}
