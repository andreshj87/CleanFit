package com.zireck.projectk.presentation.listener;

import com.zireck.projectk.presentation.model.Food;

import java.util.List;

/**
 * Created by Zireck on 06/08/2015.
 */
public interface OnAddMealInteractorFinishedListener {
    public void onGetFoodsFinished(List<Food> foods);
}
