package com.zireck.projectk.interactor;

import com.zireck.projectk.listener.OnEditFoodInteractorFinishedListener;
import com.zireck.projectk.model.Food;
import com.zireck.projectk.model.FoodDao;
import com.zireck.projectk.model.GreenDaoHelper;

/**
 * Created by Zireck on 31/07/2015.
 */
public class EditFoodInteractorImpl implements EditFoodInteractor {

    @Override
    public void getFood(OnEditFoodInteractorFinishedListener listener, long foodId) {
        listener.onGetFoodFinished(retrieveFood(foodId));
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
