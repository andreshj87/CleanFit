package com.zireck.projectk.data.repository.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zireck.projectk.data.entity.DaoMaster;
import com.zireck.projectk.data.entity.DaoSession;
import com.zireck.projectk.data.entity.UserEntity;
import com.zireck.projectk.data.entity.UserEntityDao;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Zireck on 11/09/2015.
 */
public class UserDataStore {

    @Inject
    Context mContext;

    @Inject
    public UserDataStore() {

    }

    public Observable<UserEntity> userEntityDetails() {
        return Observable.create(new Observable.OnSubscribe<UserEntity>() {
            @Override
            public void call(Subscriber<? super UserEntity> subscriber) {
                UserEntityDao userEntityDao = getUserEntityDao();
                UserEntity userEntity = userEntityDao.queryBuilder().unique();

                if (userEntity != null) {
                    subscriber.onNext(userEntity);
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new Throwable());
                }
            }
        });
    }

    public Observable<Void> insertUser(final UserEntity userEntity) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                UserEntityDao userEntityDao = getUserEntityDao();

                userEntity.setId(0L);

                userEntityDao.update(userEntity);
                userEntityDao.insert(userEntity);
                subscriber.onCompleted();
            }
        });
    }

    private UserEntityDao getUserEntityDao() {
        return initGreenDao().getUserEntityDao();
    }

    private DaoSession initGreenDao() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(mContext, "projectk", null);
        SQLiteDatabase database = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession daoSession = daoMaster.newSession();
        return daoSession;
    }
}
