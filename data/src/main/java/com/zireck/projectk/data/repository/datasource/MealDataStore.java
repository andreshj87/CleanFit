package com.zireck.projectk.data.repository.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zireck.projectk.data.entity.DaoMaster;
import com.zireck.projectk.data.entity.DaoSession;
import com.zireck.projectk.data.entity.MealEntity;
import com.zireck.projectk.data.entity.MealEntityDao;
import com.zireck.projectk.data.util.GreenDaoUtils;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Zireck on 24/08/2015.
 */
public class MealDataStore {

    @Inject Context mContext;

    private DaoMaster.DevOpenHelper mDevOpenHelper;
    private SQLiteDatabase mDatabase;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private MealEntityDao mMealEntityDao;

    @Inject
    public MealDataStore() {

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

    private MealEntityDao getMealEntityDao() {
        mMealEntityDao = initGreenDao().getMealEntityDao();
        return mMealEntityDao;
    }

    public Observable<MealEntity> mealEntityDetails(final long mealId) {
        return Observable.create(new Observable.OnSubscribe<MealEntity>() {
            @Override
            public void call(Subscriber<? super MealEntity> subscriber) {
                MealEntityDao mealEntityDao = getMealEntityDao();
                MealEntity mealEntity = mealEntityDao.queryBuilder().where(MealEntityDao.Properties.Id.eq(mealId)).unique();

                if (mealEntity != null) {
                    subscriber.onNext(mealEntity);
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new Throwable());
                }

                closeGreenDao();
            }
        });
    }

    public Observable<List<MealEntity>> mealEntityList() {
        return Observable.create(new Observable.OnSubscribe<List<MealEntity>>() {
            @Override
            public void call(Subscriber<? super List<MealEntity>> subscriber) {
                MealEntityDao mealEntityDao = getMealEntityDao();
                List<MealEntity> mealEntities;
                mealEntities = mealEntityDao.queryBuilder().orderAsc(MealEntityDao.Properties.Date).list();

                if (mealEntities != null) {
                    subscriber.onNext(mealEntities);
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new Throwable());
                }

                closeGreenDao();
            }
        });
    }

    public Observable<List<MealEntity>> mealEntityList(final Date firstDate, final Date lastDate) {
        return Observable.create(new Observable.OnSubscribe<List<MealEntity>>() {
            @Override
            public void call(Subscriber<? super List<MealEntity>> subscriber) {
                MealEntityDao mealEntityDao = getMealEntityDao();
                List<MealEntity> mealEntities;
                mealEntities = mealEntityDao.queryBuilder().where(MealEntityDao.Properties.Date.between(firstDate, lastDate)).list();

                if (mealEntities != null) {
                    subscriber.onNext(mealEntities);
                } else {
                    subscriber.onError(new Throwable());
                }

                subscriber.onCompleted();

                closeGreenDao();
            }
        });
    }

    public Observable<Void> addMeal(final MealEntity meal) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                MealEntityDao mealEntityDao = getMealEntityDao();
                meal.setId(null);
                long result = mealEntityDao.insert(meal);

                if (result == -1) {
                    subscriber.onError(new Throwable());
                } else {
                    subscriber.onCompleted();
                }

                closeGreenDao();
            }
        });
    }

    public Observable<Void> editMeal(final MealEntity meal) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                MealEntityDao mealEntityDao = getMealEntityDao();
                mealEntityDao.update(meal);

                subscriber.onCompleted();

                closeGreenDao();
            }
        });
    }

    public Observable<Void> deleteMeal(final MealEntity meal) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                MealEntityDao mealEntityDao = getMealEntityDao();
                mealEntityDao.deleteByKey(meal.getId());

                subscriber.onCompleted();

                closeGreenDao();
            }
        });
    }

    @Deprecated
    public Observable<Void> deleteAllMeals() {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                MealEntityDao mealEntityDao = getMealEntityDao();
                mealEntityDao.deleteAll();

                subscriber.onCompleted();

                closeGreenDao();
            }
        });
    }
}
