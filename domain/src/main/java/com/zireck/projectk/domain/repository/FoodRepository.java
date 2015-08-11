package com.zireck.projectk.domain.repository;

import com.zireck.projectk.domain.Food;

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
    Observable<Food> food(final int foodId);

    /**
     * Get an {@link Observable} which will emit a List of {@link Food}.
     */
    Observable<List<Food>> foods();
}
