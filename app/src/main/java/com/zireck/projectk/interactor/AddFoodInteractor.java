package com.zireck.projectk.interactor;

import com.zireck.projectk.listener.OnAddFoodInteractorFinishedListener;
import com.zireck.projectk.model.Food;

/**
 * Created by Zireck on 03/08/2015.
 */
public interface AddFoodInteractor {
    public void addFood(OnAddFoodInteractorFinishedListener listener, Food food);
}
