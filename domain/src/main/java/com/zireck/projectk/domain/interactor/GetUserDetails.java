package com.zireck.projectk.domain.interactor;

import com.zireck.projectk.domain.User;
import com.zireck.projectk.domain.executor.PostExecutionThread;
import com.zireck.projectk.domain.executor.ThreadExecutor;
import com.zireck.projectk.domain.repository.UserRepository;

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
