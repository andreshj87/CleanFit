package com.zireck.projectk.data.repository.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zireck.projectk.data.entity.DaoMaster;
import com.zireck.projectk.data.entity.DaoSession;
import com.zireck.projectk.data.entity.MealEntity;
import com.zireck.projectk.data.entity.MealEntityDao;

import java.util.Calendar;
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

    @Inject
    public MealDataStore() {

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
                //System.out.println("k9d3 date received to compare: " + date.toString());

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                Calendar first = Calendar.getInstance();
                first.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

                Calendar last = Calendar.getInstance();
                last.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);

                /*
                Calendar now = Calendar.getInstance();
                now.setTimeInMillis(System.currentTimeMillis());
                now.add(Calendar.MONTH, -1);
                now.add(Calendar.DAY_OF_MONTH, -1);

                Calendar first = Calendar.getInstance();
                first.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

                Calendar last = Calendar.getInstance();
                last.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH), 23, 59, 59);*/

                Date firstDate = first.getTime();
                Date lastDate = last.getTime();

                System.out.println("k9d3 searching meals between dates: " + firstDate.toString() + " ___ AND ___ " + lastDate.toString());

                MealEntityDao mealEntityDao = getMealEntityDao();
                List<MealEntity> mealEntities;
                mealEntities = mealEntityDao.queryBuilder().where(MealEntityDao.Properties.Date.between(firstDate, lastDate)).list();

                if (mealEntities != null) {
                    //System.out.println("k9d3 amount of meals found: " + mealEntities.size());
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

    @Deprecated
    public Observable<Void> deleteAllMeals() {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                MealEntityDao mealEntityDao = getMealEntityDao();
                mealEntityDao.deleteAll();

                subscriber.onCompleted();
            }
        });
    }

    private MealEntityDao getMealEntityDao() {
        return initGreenDao().getMealEntityDao();
    }

    private DaoSession initGreenDao() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(mContext, "projectk", null);
        SQLiteDatabase database = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession daoSession = daoMaster.newSession();
        return daoSession;
    }
}
