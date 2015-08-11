package com.zireck.projectk.presentation.listener;

import com.zireck.projectk.presentation.model.FoodModel;

/**
 * Created by Zireck on 29/07/2015.
 */
public interface OnFoodDetailInteractorFinishedListener {
    public void onGetFoodFinished(FoodModel food);
    public void onDeleteFoodFinished();
}
