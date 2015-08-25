package com.zireck.projectk.domain.repository;

import com.zireck.projectk.domain.Meal;

import java.util.Date;
import java.util.List;

import rx.Observable;

/**
 * Interface that represents a Repository for getting {@link Meal} related data.
 */
public interface MealRepository {

    /**
     * Get an {@link Observable} which will emit a {@link Meal}.
     *
     * @param mealId The meal id used to retrieve meal data.
     */
    Observable<Meal> meal(final long mealId);

    /**
     * Get an {@link Observable} which will emit a List of {@link Meal}.
     */
    Observable<List<Meal>> meals();

    /**
     * Get an {@link Observable} which will emit a List of {@link Meal}.
     *
     * @param date The date used to retrieve meal data.
     */
    Observable<List<Meal>> meals(final Date date);

    /**
     * Get an {@link Observable} which will notify the addition of a {@link Meal} object.
     */
    Observable<Void> addMeal(final Meal meal);

    /**
     * Get an {@link Observable} which will notify the edition of a {@link Meal} object.
     */
    Observable<Void> editMeal(final Meal meal);

    /**
     * Get an {@link Observable} which will notify the deletion of a {@link Meal} object.
     * @param meal
     */
    Observable<Void> deleteMeal(final Meal meal);
}
