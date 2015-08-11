package com.zireck.projectk.presentation.interactor;

import com.zireck.projectk.presentation.listener.OnEditFoodInteractorFinishedListener;
import com.zireck.projectk.presentation.model.Food;
import com.zireck.projectk.presentation.model.FoodDao;
import com.zireck.projectk.data.util.GreenDaoUtils;

/**
 * Created by Zireck on 31/07/2015.
 */
public class EditFoodInteractorImpl implements EditFoodInteractor {

    @Override
    public void getFood(OnEditFoodInteractorFinishedListener listener, long foodId) {
        listener.onGetFoodFinished(retrieveFood(foodId));
    }

    @Override
    public void updateFood(OnEditFoodInteractorFinishedListener listener, Food food) {
        GreenDaoUtils.getFoodDao().update(food);
        listener.onFoodUpdated();
    }

    private Food retrieveFood(long foodId) {
        FoodDao foodDao = GreenDaoUtils.getFoodDao();
        Food food = foodDao.queryBuilder().where(FoodDao.Properties.Id.eq(foodId)).unique();

        return food;
    }
}
