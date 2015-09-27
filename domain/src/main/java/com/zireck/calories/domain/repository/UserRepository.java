package com.zireck.calories.domain.repository;

import rx.Observable;

/**
 * Interface that represents a Repository for getting {@link com.zireck.calories.domain.User} related data.
 */
public interface UserRepository {

    /**
     * Get an {@link Observable} which will emit the {@link com.zireck.calories.domain.User}.
     */
    Observable<com.zireck.calories.domain.User> user();

    /**
     * Get an {@link Observable} which will notify the insertion of an {@link com.zireck.calories.domain.User} object.
     */
    Observable<Void> insertUser(final com.zireck.calories.domain.User user);
}
