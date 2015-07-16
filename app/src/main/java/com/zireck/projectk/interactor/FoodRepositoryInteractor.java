package com.zireck.projectk.interactor;

import com.zireck.projectk.listener.OnFoodRepositoryFinishedListener;

/**
 * Created by Zireck on 16/07/2015.
 */
public interface FoodRepositoryInteractor {
    public void retrieveFoodRepository(OnFoodRepositoryFinishedListener listener);
}
