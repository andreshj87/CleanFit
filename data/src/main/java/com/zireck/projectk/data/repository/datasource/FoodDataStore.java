package com.zireck.projectk.data.repository.datasource;

import com.zireck.projectk.data.entity.FoodEntity;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Zireck on 11/08/2015.
 */
public class FoodDataStore {

    public Observable<FoodEntity> foodEntityDetails(final int foodId) {
        return Observable.create(new Observable.OnSubscribe<FoodEntity>() {
            @Override
            public void call(Subscriber<? super FoodEntity> subscriber) {
                //new DaoMaster.DevOpenHelper(this, "pk", null);
            }
        });
    }

    public Observable<List<FoodEntity>> foodEntityList() {
        return Observable.create(new Observable.OnSubscribe<List<FoodEntity>>() {
            @Override
            public void call(Subscriber<? super List<FoodEntity>> subscriber) {

            }
        });
    }
}
