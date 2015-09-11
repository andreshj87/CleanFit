package com.zireck.projectk.data.repository;

import com.zireck.projectk.data.entity.UserEntity;
import com.zireck.projectk.data.entity.mapper.UserEntityDataMapper;
import com.zireck.projectk.data.repository.datasource.UserDataStore;
import com.zireck.projectk.domain.User;
import com.zireck.projectk.domain.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * {@link UserRepository} for managing user data.
 */
public class UserDataRepository implements UserRepository {

    private UserDataStore mUserDataStore;
    private UserEntityDataMapper mUserEntityDataMapper;

    @Inject
    public UserDataRepository(UserDataStore userDataStore,
                              UserEntityDataMapper userEntityDataMapper) {
        mUserDataStore = userDataStore;
        mUserEntityDataMapper = userEntityDataMapper;
    }

    @Override
    public Observable<User> user() {
        return mUserDataStore.userEntityDetails().map(new Func1<UserEntity, User>() {
            @Override
            public User call(UserEntity userEntity) {
                return mUserEntityDataMapper.transform(userEntity);
            }
        });
    }

    @Override
    public Observable<Void> insertUser(User user) {
        return mUserDataStore.insertUser(mUserEntityDataMapper.transformInverse(user));
    }
}
