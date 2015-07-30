package com.zireck.projectk.interactor;

import com.zireck.projectk.listener.OnFoodDetailFinishedListener;
import com.zireck.projectk.model.Food;
import com.zireck.projectk.model.FoodDao;
import com.zireck.projectk.model.GreenDaoHelper;

/**
 * Created by Zireck on 29/07/2015.
 */
public class FoodDetailInteractorImpl implements FoodDetailInteractor {
    @Override
    public void getFood(OnFoodDetailFinishedListener listener, long foodId) {
        listener.onGetFoodFinished(retrieveFood(foodId));
    }

    @Override
    public void deleteFood(OnFoodDetailFinishedListener listener, Food food) {
        FoodDao foodDao = retrieveFoodDao();
        foodDao.delete(food);
        listener.onDeleteFoodFinished();
    }

    private Food retrieveFood(long foodId) {
        GreenDaoHelper daoHelper = new GreenDaoHelper();
        FoodDao foodDao = retrieveFoodDao();
        Food food = foodDao.queryBuilder().where(FoodDao.Properties.Id.eq(foodId)).unique();

        return food;
    }

    private FoodDao retrieveFoodDao() {
        GreenDaoHelper daoHelper = new GreenDaoHelper();
        return daoHelper.getFoodDao();
    }
}
