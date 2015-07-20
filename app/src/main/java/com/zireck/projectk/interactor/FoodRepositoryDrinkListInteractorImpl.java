package com.zireck.projectk.interactor;

import com.zireck.projectk.listener.OnFoodRepositoryListFinishedListener;
import com.zireck.projectk.model.Food;
import com.zireck.projectk.model.FoodDao;
import com.zireck.projectk.model.GreenDaoHelper;

import java.util.List;

/**
 * Created by Zireck on 16/07/2015.
 */
public class FoodRepositoryDrinkListInteractorImpl implements FoodRepositoryListInteractor {
    @Override
    public void retrieveFoodRepository(OnFoodRepositoryListFinishedListener listener) {
        GreenDaoHelper daoHelper = new GreenDaoHelper();
        FoodDao foodDao = daoHelper.getFoodDao();
        List<Food> foodList = foodDao.queryBuilder().where(FoodDao.Properties.IsDrink.eq(true)).list();
        listener.onFinished(foodList);
    }
}
