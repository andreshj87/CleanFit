package com.zireck.projectk.interactor;

import com.zireck.projectk.listener.OnFoodDetailFinishedListener;
import com.zireck.projectk.model.Food;

/**
 * Created by Zireck on 29/07/2015.
 */
public interface FoodDetailInteractor {
    public void getFood(OnFoodDetailFinishedListener listener, long foodId);
    public void deleteFood(OnFoodDetailFinishedListener listener, Food food);
}
