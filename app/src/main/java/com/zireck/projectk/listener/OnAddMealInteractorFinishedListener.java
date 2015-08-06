package com.zireck.projectk.listener;

import com.zireck.projectk.model.Food;

import java.util.List;

/**
 * Created by Zireck on 06/08/2015.
 */
public interface OnAddMealInteractorFinishedListener {
    public void onGetFoodsFinished(List<Food> foods);
}
