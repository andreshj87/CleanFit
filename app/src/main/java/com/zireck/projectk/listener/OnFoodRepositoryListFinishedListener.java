package com.zireck.projectk.listener;

import com.zireck.projectk.model.Food;

import java.util.List;

/**
 * Created by Zireck on 16/07/2015.
 */
public interface OnFoodRepositoryListFinishedListener {
    public void onFinished(List<Food> foodItems);
}
