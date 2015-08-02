package com.zireck.projectk.interactor;

import com.zireck.projectk.listener.OnAddFoodInteractorFinishedListener;
import com.zireck.projectk.model.Food;
import com.zireck.projectk.util.GreenDaoUtils;

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
