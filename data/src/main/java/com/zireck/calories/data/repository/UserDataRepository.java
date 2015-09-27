package com.zireck.calories.data.repository;

import com.zireck.calories.data.entity.UserEntity;
import com.zireck.calories.data.entity.mapper.UserEntityDataMapper;
import com.zireck.calories.data.repository.datasource.UserDataStore;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * {@link com.zireck.calories.domain.repository.UserRepository} for managing user data.
 */
public class UserDataRepository implements com.zireck.calories.domain.repository.UserRepository {

    private UserDataStore mUserDataStore;
    private UserEntityDataMapper mUserEntityDataMapper;

    @Inject
    public UserDataRepository(UserDataStore userDataStore,
                              UserEntityDataMapper userEntityDataMapper) {
        mUserDataStore = userDataStore;
        mUserEntityDataMapper = userEntityDataMapper;
    }

    @Override
    public Observable<com.zireck.calories.domain.User> user() {
        return mUserDataStore.userEntityDetails().map(new Func1<UserEntity, com.zireck.calories.domain.User>() {
            @Override
            public com.zireck.calories.domain.User call(UserEntity userEntity) {
                return mUserEntityDataMapper.transform(userEntity);
            }
        });
    }

    @Override
    public Observable<Void> insertUser(com.zireck.calories.domain.User user) {
        return mUserDataStore.insertUser(mUserEntityDataMapper.transformInverse(user));
    }
}
