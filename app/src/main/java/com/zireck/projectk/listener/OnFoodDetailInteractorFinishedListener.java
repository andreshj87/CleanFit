package com.zireck.projectk.listener;

import com.zireck.projectk.model.Food;

/**
 * Created by Zireck on 29/07/2015.
 */
public interface OnFoodDetailInteractorFinishedListener {
    public void onGetFoodFinished(Food food);
    public void onDeleteFoodFinished();
}
