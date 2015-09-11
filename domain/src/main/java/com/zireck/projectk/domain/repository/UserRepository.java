package com.zireck.projectk.domain.repository;

import com.zireck.projectk.domain.User;

import rx.Observable;

/**
 * Interface that represents a Repository for getting {@link User} related data.
 */
public interface UserRepository {

    /**
     * Get an {@link Observable} which will emit the {@link User}.
     */
    Observable<User> user();

    /**
     * Get an {@link Observable} which will notify the insertion of an {@link User} object.
     */
    Observable<Void> insertUser(final User user);
}
