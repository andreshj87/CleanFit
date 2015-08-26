package com.zireck.projectk.data.repository.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zireck.projectk.data.entity.DaoMaster;
import com.zireck.projectk.data.entity.DaoSession;
import com.zireck.projectk.data.entity.FoodEntity;
import com.zireck.projectk.data.entity.FoodEntityDao;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Zireck on 11/08/2015.
 */
public class FoodDataStore {

    @Inject Context mContext;

    @Inject
    public FoodDataStore() {

    }

    public Observable<FoodEntity> foodEntityDetails(final long foodId) {
        return Observable.create(new Observable.OnSubscribe<FoodEntity>() {
            @Override
            public void call(Subscriber<? super FoodEntity> subscriber) {
                FoodEntityDao foodEntityDao = getFoodEntityDao();
                FoodEntity foodEntity = foodEntityDao.queryBuilder().where(FoodEntityDao.Properties.Id.eq(foodId)).unique();

                if (foodEntity != null) {
                    subscriber.onNext(foodEntity);
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new Throwable());
                }
            }
        });
    }

    public Observable<List<FoodEntity>> foodEntityList() {
        return Observable.create(new Observable.OnSubscribe<List<FoodEntity>>() {
            @Override
            public void call(Subscriber<? super List<FoodEntity>> subscriber) {
                FoodEntityDao foodEntityDao = getFoodEntityDao();
                List<FoodEntity> foodEntities;
                foodEntities = foodEntityDao.loadAll();

                if (foodEntities != null) {
                    subscriber.onNext(foodEntities);
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new Throwable());
                }
            }
        });
    }

    public Observable<List<FoodEntity>> foodList() {
        return Observable.create(new Observable.OnSubscribe<List<FoodEntity>>() {
            @Override
            public void call(Subscriber<? super List<FoodEntity>> subscriber) {
                FoodEntityDao foodEntityDao = getFoodEntityDao();
                List<FoodEntity> foodEntities;
                foodEntities = foodEntityDao.queryBuilder().where(FoodEntityDao.Properties.IsDrink.eq(false)).list();

                if (foodEntities != null) {
                    subscriber.onNext(foodEntities);
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new Throwable());
                }
            }
        });
    }

    public Observable<List<FoodEntity>> drinkList() {
        return Observable.create(new Observable.OnSubscribe<List<FoodEntity>>() {
            @Override
            public void call(Subscriber<? super List<FoodEntity>> subscriber) {
                FoodEntityDao foodEntityDao = getFoodEntityDao();
                List<FoodEntity> foodEntities;
                foodEntities = foodEntityDao.queryBuilder().where(FoodEntityDao.Properties.IsDrink.eq(true)).list();

                if (foodEntities != null) {
                    subscriber.onNext(foodEntities);
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new Throwable());
                }
            }
        });
    }

    public Observable<Void> addFood(final FoodEntity food) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                FoodEntityDao foodEntityDao = getFoodEntityDao();
                food.setId(null);
                long result = foodEntityDao.insert(food);

                if (result == -1) {
                    subscriber.onError(new Throwable());
                } else {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<Void> editFood(final FoodEntity food) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                FoodEntityDao foodEntityDao = getFoodEntityDao();
                foodEntityDao.update(food);

                subscriber.onCompleted();
            }
        });
    }

    public Observable<Void> deleteFood(final FoodEntity food) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                FoodEntityDao foodEntityDao = getFoodEntityDao();
                foodEntityDao.deleteByKey(food.getId());

                subscriber.onCompleted();
            }
        });
    }

    private FoodEntityDao getFoodEntityDao() {
        return initGreenDao().getFoodEntityDao();
    }

    private DaoSession initGreenDao() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(mContext, "projectk", null);
        SQLiteDatabase database = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession daoSession = daoMaster.newSession();
        return daoSession;
    }
}
