package com.zireck.projectk.listener;

import com.zireck.projectk.model.Food;

import java.util.List;

/**
 * Created by Zireck on 22/07/2015.
 */
public interface OnFoodListFinishedListener {
    public void onFinished(List<Food> items);
}
