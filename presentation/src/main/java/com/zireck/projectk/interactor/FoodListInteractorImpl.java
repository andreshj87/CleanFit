package com.zireck.projectk.interactor;

import com.zireck.projectk.listener.OnFoodListFinishedListener;
import com.zireck.projectk.model.Food;
import com.zireck.projectk.model.FoodDao;
import com.zireck.projectk.util.GreenDaoUtils;

import java.util.List;

/**
 * Created by Zireck on 22/07/2015.
 */
public class FoodListInteractorImpl implements FoodListInteractor {
    @Override
    public void retrieveFoodRepository(OnFoodListFinishedListener listener) {
        listener.onFinished(retrieveFood(false));
    }

    @Override
    public void retrieveDrinkRepository(OnFoodListFinishedListener listener) {
        listener.onFinished(retrieveFood(true));
    }

    private List<Food> retrieveFood(boolean isDrink) {
        FoodDao foodDao = GreenDaoUtils.getFoodDao();
        List<Food> foodList = foodDao.queryBuilder().where(FoodDao.Properties.IsDrink.eq(isDrink)).list();

        return foodList;
    }
}
