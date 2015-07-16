package com.zireck.projectk.interactor;

import android.util.Log;

import com.zireck.projectk.listener.OnFoodRepositoryFinishedListener;
import com.zireck.projectk.model.GreenDaoHelper;

/**
 * Created by Zireck on 16/07/2015.
 */
public class FoodRepositoryInteractorImpl implements FoodRepositoryInteractor {

    @Override
    public void retrieveFoodRepository(OnFoodRepositoryFinishedListener listener) {
        GreenDaoHelper daoHelper = new GreenDaoHelper();
        listener.onFinished(daoHelper.getFoodDao().loadAll());
    }
}
