package com.zireck.projectk.domain.repository;

import com.zireck.projectk.domain.Meal;

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
    Observable<Meal> meal(final int mealId);

    /**
     * Get an {@link Observable} which will emit a List of {@link Meal}.
     */
    Observable<List<Meal>> meals();
}
