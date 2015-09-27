package com.zireck.projectk.data.repository.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zireck.projectk.data.entity.DaoMaster;
import com.zireck.projectk.data.entity.DaoSession;
import com.zireck.projectk.data.entity.UserEntity;
import com.zireck.projectk.data.entity.UserEntityDao;
import com.zireck.projectk.data.util.GreenDaoUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Zireck on 11/09/2015.
 */
public class UserDataStore {

    @Inject Context mContext;

    private DaoMaster.DevOpenHelper mDevOpenHelper;
    private SQLiteDatabase mDatabase;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private UserEntityDao mUserEntityDao;

    @Inject
    public UserDataStore() {

    }

    private DaoSession initGreenDao() {
        mDevOpenHelper = new DaoMaster.DevOpenHelper(mContext, GreenDaoUtils.DATABASE_NAME, null);
        mDatabase = mDevOpenHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(mDatabase);
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }

    private void closeGreenDao() {
        //mDatabase.close();
    }

    private UserEntityDao getUserEntityDao() {
        mUserEntityDao = initGreenDao().getUserEntityDao();
        return mUserEntityDao;
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

                closeGreenDao();
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

                closeGreenDao();
            }
        });
    }
}
