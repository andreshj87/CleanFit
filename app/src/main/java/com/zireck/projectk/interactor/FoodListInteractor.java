package com.zireck.projectk.interactor;

import com.zireck.projectk.listener.OnFoodListFinishedListener;

/**
 * Created by Zireck on 22/07/2015.
 */
public interface FoodListInteractor {
    public void retrieveFoodRepository(OnFoodListFinishedListener listener);
    public void retrieveDrinkRepository(OnFoodListFinishedListener listener);
}
