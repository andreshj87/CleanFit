package com.zireck.projectk.interactor;

import com.zireck.projectk.listener.OnFoodRepositoryListFinishedListener;

/**
 * Created by Zireck on 16/07/2015.
 */
public interface FoodRepositoryListInteractor {
    public void retrieveFoodRepository(OnFoodRepositoryListFinishedListener listener);
}
