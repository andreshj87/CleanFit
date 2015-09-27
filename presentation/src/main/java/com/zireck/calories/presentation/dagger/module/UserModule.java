package com.zireck.calories.presentation.dagger.module;

import com.zireck.calories.presentation.dagger.PerActivity;
import com.zireck.calories.domain.executor.ThreadExecutor;
import com.zireck.calories.domain.interactor.InsertUser;
import com.zireck.calories.domain.repository.UserRepository;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zireck on 11/09/2015.
 */
@Module
public class UserModule {

    public UserModule() {

    }

    @Provides @PerActivity
    @Named("userDetails")
    com.zireck.calories.domain.interactor.Interactor provideGetUserDetailsInteractor(UserRepository userRepository,
                        ThreadExecutor threadExecutor, com.zireck.calories.domain.executor.PostExecutionThread postExecutionThread) {
        return new com.zireck.calories.domain.interactor.GetUserDetails(userRepository, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity @Named("insertUser")
    InsertUser provideInsertUserInteractor(UserRepository userRepository,
                                     ThreadExecutor threadExecutor,
                                     com.zireck.calories.domain.executor.PostExecutionThread postExecutionThread) {
        return new InsertUser(userRepository, threadExecutor, postExecutionThread);
    }
}
