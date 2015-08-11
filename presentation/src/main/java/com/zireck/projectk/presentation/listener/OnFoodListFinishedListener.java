package com.zireck.projectk.presentation.listener;

import com.zireck.projectk.presentation.model.FoodModel;

import java.util.List;

/**
 * Created by Zireck on 22/07/2015.
 */
public interface OnFoodListFinishedListener {
    public void onFinished(List<FoodModel> items);
}
