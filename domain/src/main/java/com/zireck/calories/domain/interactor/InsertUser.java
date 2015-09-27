package com.zireck.calories.domain.interactor;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Zireck on 11/09/2015.
 */
public class InsertUser extends Interactor {

    private com.zireck.calories.domain.User mUser;
    private com.zireck.calories.domain.repository.UserRepository mUserRepository;

    @Inject
    public InsertUser(com.zireck.calories.domain.repository.UserRepository userRepository, com.zireck.calories.domain.executor.ThreadExecutor threadExecutor,
                      com.zireck.calories.domain.executor.PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mUserRepository = userRepository;
    }

    public void setUser(com.zireck.calories.domain.User user) {
        if (user == null) {
            throw new IllegalArgumentException("User object cannot be null.");
        }

        mUser = user;
    }

    @Override
    protected Observable buildInteractorObservable() {
        return mUserRepository.insertUser(mUser);
    }
}
