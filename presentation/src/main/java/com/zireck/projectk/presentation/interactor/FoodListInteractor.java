package com.zireck.projectk.presentation.interactor;

import com.zireck.projectk.presentation.listener.OnFoodListFinishedListener;

/**
 * Created by Zireck on 22/07/2015.
 */
public interface FoodListInteractor {
    public void retrieveFoodRepository(OnFoodListFinishedListener listener);
    public void retrieveDrinkRepository(OnFoodListFinishedListener listener);
}
