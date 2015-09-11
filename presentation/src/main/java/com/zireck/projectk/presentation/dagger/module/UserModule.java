package com.zireck.projectk.presentation.dagger.module;

import com.zireck.projectk.domain.executor.PostExecutionThread;
import com.zireck.projectk.domain.executor.ThreadExecutor;
import com.zireck.projectk.domain.interactor.InsertUser;
import com.zireck.projectk.domain.interactor.GetUserDetails;
import com.zireck.projectk.domain.interactor.Interactor;
import com.zireck.projectk.domain.repository.UserRepository;
import com.zireck.projectk.presentation.dagger.PerActivity;

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

    @Provides @PerActivity @Named("userDetails")
    Interactor provideGetUserDetailsInteractor(UserRepository userRepository,
                        ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetUserDetails(userRepository, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity @Named("insertUser")
    InsertUser provideInsertUserInteractor(UserRepository userRepository,
                                     ThreadExecutor threadExecutor,
                                     PostExecutionThread postExecutionThread) {
        return new InsertUser(userRepository, threadExecutor, postExecutionThread);
    }
}
