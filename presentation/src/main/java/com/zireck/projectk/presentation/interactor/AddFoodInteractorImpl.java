package com.zireck.projectk.presentation.interactor;

import com.zireck.projectk.presentation.listener.OnAddFoodInteractorFinishedListener;
import com.zireck.projectk.presentation.model.Food;
import com.zireck.projectk.data.util.GreenDaoUtils;

/**
 * Created by Zireck on 03/08/2015.
 */
public class AddFoodInteractorImpl implements AddFoodInteractor {
    @Override
    public void addFood(OnAddFoodInteractorFinishedListener listener, Food food) {
        GreenDaoUtils.getFoodDao().insert(food);
        listener.onFoodAdded();
    }
}
