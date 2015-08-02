package com.zireck.projectk.interactor;

import com.zireck.projectk.listener.OnFoodDetailInteractorFinishedListener;
import com.zireck.projectk.model.Food;

/**
 * Created by Zireck on 29/07/2015.
 */
public interface FoodDetailInteractor {
    public void getFood(OnFoodDetailInteractorFinishedListener listener, long foodId);
    public void deleteFood(OnFoodDetailInteractorFinishedListener listener, Food food);
}
