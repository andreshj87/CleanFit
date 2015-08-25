package com.zireck.projectk.data.repository.datasource;

import com.zireck.projectk.data.entity.MealEntity;
import com.zireck.projectk.data.entity.MealEntityDao;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Zireck on 24/08/2015.
 */
public class MealDataStore extends DataStore {

    @Inject
    public MealDataStore() {
        super();
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
            }
        });
    }

    public Observable<List<MealEntity>> mealEntityList() {
        return Observable.create(new Observable.OnSubscribe<List<MealEntity>>() {
            @Override
            public void call(Subscriber<? super List<MealEntity>> subscriber) {
                MealEntityDao mealEntityDao = getMealEntityDao();
                List<MealEntity> mealEntities;
                mealEntities = mealEntityDao.loadAll();

                if (mealEntities != null) {
                    subscriber.onNext(mealEntities);
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new Throwable());
                }
            }
        });
    }

    public Observable<List<MealEntity>> mealEntityList(final Date date) {
        return Observable.create(new Observable.OnSubscribe<List<MealEntity>>() {
            @Override
            public void call(Subscriber<? super List<MealEntity>> subscriber) {
                MealEntityDao mealEntityDao = getMealEntityDao();
                List<MealEntity> mealEntities;
                mealEntities = mealEntityDao.queryBuilder().where(MealEntityDao.Properties.Date.eq(date)).list();

                if (mealEntities != null) {
                    subscriber.onNext(mealEntities);
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new Throwable());
                }
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
            }
        });
    }

    private MealEntityDao getMealEntityDao() {
        return mDaoSession.getMealEntityDao();
    }
}
