package com.zireck.projectk.presentation.interactor;

import com.zireck.projectk.presentation.listener.OnFoodDetailInteractorFinishedListener;
import com.zireck.projectk.presentation.model.Food;

/**
 * Created by Zireck on 29/07/2015.
 */
public interface FoodDetailInteractor {
    public void getFood(OnFoodDetailInteractorFinishedListener listener, long foodId);
    public void deleteFood(OnFoodDetailInteractorFinishedListener listener, Food food);
}
