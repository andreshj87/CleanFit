package com.zireck.calories.presentation.listener;

import com.zireck.calories.presentation.model.FoodModel;

import java.util.List;

/**
 * Created by Zireck on 06/08/2015.
 */
public interface OnAddMealInteractorFinishedListener {
    public void onGetFoodsFinished(List<FoodModel> foods);
}
