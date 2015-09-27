package com.zireck.calories.domain.repository;

import java.util.Date;
import java.util.List;

import rx.Observable;

/**
 * Interface that represents a Repository for getting {@link com.zireck.calories.domain.Meal} related data.
 */
public interface MealRepository {

    /**
     * Get an {@link Observable} which will emit a {@link com.zireck.calories.domain.Meal}.
     *
     * @param mealId The meal id used to retrieve meal data.
     */
    Observable<com.zireck.calories.domain.Meal> meal(final long mealId);

    /**
     * Get an {@link Observable} which will emit a List of {@link com.zireck.calories.domain.Meal}.
     */
    Observable<List<com.zireck.calories.domain.Meal>> meals();

    /**
     * Get an {@link Observable} which will emit a List of {@link com.zireck.calories.domain.Meal}.
     *
     * @param firstDate The date used to retrieve meal data.
     * @param lastDate The date used to retrieve meal data.
     */
    Observable<List<com.zireck.calories.domain.Meal>> meals(final Date firstDate, final Date lastDate);

    /**
     * Get an {@link Observable} which will notify the addition of a {@link com.zireck.calories.domain.Meal} object.
     */
    Observable<Void> addMeal(final com.zireck.calories.domain.Meal meal);

    /**
     * Get an {@link Observable} which will notify the edition of a {@link com.zireck.calories.domain.Meal} object.
     */
    Observable<Void> editMeal(final com.zireck.calories.domain.Meal meal);

    /**
     * Get an {@link Observable} which will notify the deletion of a {@link com.zireck.calories.domain.Meal} object.
     * @param meal
     */
    Observable<Void> deleteMeal(final com.zireck.calories.domain.Meal meal);

    @Deprecated
    Observable<Void> deleteAllMeals();
}
