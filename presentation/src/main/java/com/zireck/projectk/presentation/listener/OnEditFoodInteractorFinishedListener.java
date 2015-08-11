package com.zireck.projectk.presentation.listener;

import com.zireck.projectk.presentation.model.FoodModel;

/**
 * Created by Zireck on 31/07/2015.
 */
public interface OnEditFoodInteractorFinishedListener {
    public void onGetFoodFinished(FoodModel food);
    public void onFoodUpdated();
}
