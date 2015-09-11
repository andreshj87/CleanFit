package com.zireck.projectk.domain.interactor;

import com.zireck.projectk.domain.User;
import com.zireck.projectk.domain.executor.PostExecutionThread;
import com.zireck.projectk.domain.executor.ThreadExecutor;
import com.zireck.projectk.domain.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Zireck on 11/09/2015.
 */
public class InsertUser extends Interactor {

    private User mUser;
    private UserRepository mUserRepository;

    @Inject
    public InsertUser(UserRepository userRepository, ThreadExecutor threadExecutor,
                      PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mUserRepository = userRepository;
    }

    public void setUser(User user) {
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
