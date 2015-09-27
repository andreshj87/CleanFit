package com.zireck.calories.domain.interactor;

import com.zireck.calories.domain.User;
import com.zireck.calories.domain.executor.PostExecutionThread;
import com.zireck.calories.domain.executor.ThreadExecutor;
import com.zireck.calories.domain.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * This class is an implementation of {@link Interactor} that represents an use case for retrieving
 * {@link User} related data.
 */
public class GetUserDetails extends Interactor {

    private UserRepository mUserRepository;

    @Inject
    public GetUserDetails(UserRepository userRepository, ThreadExecutor threadExecutor,
                          PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mUserRepository = userRepository;
    }

    @Override
    protected Observable buildInteractorObservable() {
        return mUserRepository.user();
    }
}
